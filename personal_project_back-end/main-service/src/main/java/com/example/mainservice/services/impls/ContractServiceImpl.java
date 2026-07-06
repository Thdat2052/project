package com.example.mainservice.services.impls;

import com.example.common.service.entities.ContractEntity;
import com.example.common.service.entities.RoomMemberEntity;
import com.example.common.service.entities.ServiceRoomEntity;
import com.example.common.service.enums.ContractStatusEnum;
import com.example.common.service.enums.RoomStatusEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.internals.apis.AuthApis;
import com.example.common.service.mappers.ContractMapper;
import com.example.common.service.mappers.ServiceMapper;
import com.example.common.service.mappers.UserMapper;
import com.example.common.service.models.ContractModel;
import com.example.common.service.models.ServiceModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.models.UserModel;
import com.example.common.service.models.requests.ContractInformationRequest;
import com.example.common.service.models.responses.ContractResponse;
import com.example.common.service.services.impls.CommonServiceImpl;
import com.example.common.service.utils.AuthUtils;
import com.example.mainservice.constants.ErrorModelConstants;
import com.example.mainservice.repositories.ContractRepository;
import com.example.mainservice.repositories.ServiceRepository;
import com.example.mainservice.repositories.UserRepository;
import com.example.mainservice.services.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl extends CommonServiceImpl<ContractEntity, Long, ContractRepository>
        implements ContractService {
    private final UserService userService;
    private AuthApis authApis;
    private RoomService roomService;
    private ServiceRoomService serviceRoomService;
    private RoomMemberService roomMemberService;

    public ContractServiceImpl(ContractRepository repo, UserService userService, AuthApis authApis, RoomService roomService, ServiceRoomService serviceRoomService,
                               RoomMemberService roomMemberService) {
        super(repo);
        this.userService = userService;
        this.authApis = authApis;
        this.roomService = roomService;
        this.serviceRoomService = serviceRoomService;
        this.roomMemberService = roomMemberService;
    }

    @Override
    @Transactional
    public ContractModel createOrUpdate(ContractInformationRequest request, Map<String, String> headers) throws AppException, InterruptedException, IOException {
        String transactionId = UUID.randomUUID().toString();
        headers.put("TransactionId", transactionId);
        var memberRoom = request.getMemberOfRooms();
        var roomId = request.getRoomId();
        if(memberRoom.isEmpty()) throw new AppException(ErrorModelConstants.ROOM_MEMBER_IS_NOT_EMPTY);
        var room = roomService.getById(roomId);
       try {
           if(Objects.equals(RoomStatusEnum.RENTED , room.getStatus()))  throw new AppException(ErrorModelConstants.ROOM_IS_RENTED);
           List<UserModel> users = createUserInRoom(request,headers,transactionId);
           Long memberKey = getMemberKey(memberRoom, users);
           room.setStatus(RoomStatusEnum.RENTED);
           roomService.save(room);
           var contract = createContract(request.getContract(), transactionId, memberKey, roomId);
           roomMemberService.create(contract.getId(), users);
           var a = serviceRoomService.createServiceOfRoom(contract.getId(),request.getServiceOfRooms());
           return contract;
       }catch (Exception e){
           authApis.rollBackWhenCreatedContractFail(headers);
           throw new AppException(ErrorModelConstants.CREATE_CONTRACT_FAILED);
       }
    }

    private ContractModel createContract(ContractModel contractModel, String transactionId,Long memberKey, Long roomId){
        ContractEntity contractEntity = ContractMapper.INSTANCE.toEntity(contractModel);
        contractEntity.setUserId(memberKey);
        contractEntity.setStatus(ContractStatusEnum.ACTIVE);
        contractEntity.setTransactionId(transactionId);
        contractEntity.setRoomId(roomId);
        return ContractMapper.INSTANCE.toModel(repo.save(contractEntity));
    }
    private Long getMemberKey(List<UserModel> memberRoom, List<UserModel> userModels ){
        String memberKey = memberRoom.get(0).getUsername();
        Optional<UserModel> representative = memberRoom.stream()
                .filter(UserModel::getIsRoomRepresentative)
                .findFirst();
        if(representative.isPresent()) memberKey = representative.get().getUsername();
        String finalMemberKey = memberKey;
        return userModels.stream()
                .filter(user -> Objects.equals(user.getUsername(), finalMemberKey) )
                .findFirst().get().getId();
    }

    private List<UserModel> createUserInRoom(ContractInformationRequest request,
                                             Map<String, String> headers, String transactionId)
            throws AppException, IOException, InterruptedException {
        var userRespone = authApis.createUsers(request.getMemberOfRooms(), headers);
        if(Objects.equals(HttpStatus.INTERNAL_SERVER_ERROR.value(), userRespone.getStatusCode())){
            throw new AppException(ErrorModelConstants.CREATE_USER_MEMBER_FAILED);
        }
        return userRespone.getData().stream()
                .map(user -> {
                    user.setTransactionId(transactionId);
                    return user;
                })
                .toList();
    }

    @Override
    public Page<ContractModel> findAllWithPaging(PagingQueryConditionRequest pagingQueryConditionRequest) {
        return repo.findAllWithPaging(pagingQueryConditionRequest);
    }

    @Override
    public List<ContractResponse> findMyContract() throws AppException {
        var currentUser= userService.findMe();
        var memberRooms = roomMemberService.findByUserId(currentUser.getId());
        var memberRoomIds = memberRooms.stream()
                .map(RoomMemberEntity::getUserId).collect(Collectors.toList());
        memberRoomIds.add(currentUser.getId());
        return repo.findByUserIdIn(memberRoomIds);
    }
    @Override
    public ContractEntity findByRoomIdAndStatus(Long roomId){
        return repo.findByRoomIdAndStatus(roomId, ContractStatusEnum.ACTIVE);
    }
    @Override
    public ContractResponse findContractById(Long id) throws AppException {
        var contract = repo.findContractById(id);
        if (contract == null) {
            throw new AppException(ErrorModelConstants.CONTRACT_NOT_FOUND);
        }
        contract.setMembers(roomMemberService.findByContractId(contract.getId()));
        contract.setServices(serviceRoomService.findByContractId(contract.getId()));
        return contract;
    }

    @Transactional
    @Override
    public ContractResponse addUserToContract(Long id, List<Long> userIds) throws AppException {
        //todo: check current user is owner of contract or admin

        var contract = repo.findContractById(id);
        // check contract is active
        if (!contract.getStatus().equals(ContractStatusEnum.ACTIVE)) {
            throw new AppException(ErrorModelConstants.CONTRACT_NOT_FOUND);
        }
        roomMemberService.deleteByContractId(id);
        var users = userService.findByIdIn(userIds);
        if (users.isEmpty()) {
            throw new AppException(ErrorModelConstants.USER_NOT_FOUND);
        }
        List<RoomMemberEntity> roomMemberEntities = users.stream().map(user -> {
            RoomMemberEntity roomMemberEntity = new RoomMemberEntity();
            roomMemberEntity.setUserId(user.getId());
            roomMemberEntity.setContractId(id);
            return roomMemberEntity;
        }).collect(Collectors.toList());
        roomMemberService.save(roomMemberEntities);
        return contract;
    }

    @Override
    public ContractModel findByRoomId(Long roomId) throws AppException {
        return ContractMapper.INSTANCE.toModel(repo.findByRoomIdAndStatus(roomId, ContractStatusEnum.ACTIVE));
    }
}
