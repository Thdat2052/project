package com.example.mainservice.services.impls;

import com.example.common.service.entities.MaintenanceFeeEntity;
import com.example.common.service.entities.MaintenanceRequestEntity;
import com.example.common.service.enums.ContractStatusEnum;
import com.example.common.service.enums.MaintenanceRequestStatusEnum;
import com.example.common.service.enums.ServiceRoomStatusEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.mappers.MaintenanceRequestMapper;
import com.example.common.service.models.MaintenanceRequestModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.services.impls.CommonServiceImpl;
import com.example.mainservice.constants.ErrorModelConstants;
import com.example.mainservice.models.requests.MaintenanceRequestRequest;
import com.example.mainservice.models.responses.MaintenanceRequestResponse;
import com.example.mainservice.repositories.MaintenanceRequestRepository;
import com.example.mainservice.repositories.ServiceRoomRepository;
import com.example.mainservice.services.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class MaintenanceRequestServiceImpl extends CommonServiceImpl<MaintenanceRequestEntity, Long, MaintenanceRequestRepository>
        implements MaintenanceRequestService {
    private final ContractService contractService;
    private final RoomMemberService roomMemberService;
    private final UserService userService;
    private final ServiceRoomRepository serviceRoomRepository;
    private final MaintenanceFeeService maintenanceFeeService;

    public MaintenanceRequestServiceImpl(MaintenanceRequestRepository repository, ContractService contractService, RoomMemberService roomMemberService, UserService userService, ServiceRoomRepository serviceRoomRepository, MaintenanceFeeService maintenanceFeeService) {
        super(repository);
        this.contractService = contractService;
        this.roomMemberService = roomMemberService;
        this.userService = userService;
        this.serviceRoomRepository = serviceRoomRepository;
        this.maintenanceFeeService = maintenanceFeeService;
    }

    @Transactional
    @Override
    public MaintenanceRequestModel create(MaintenanceRequestModel model) throws AppException {
        var currentUser = userService.findMe();
        var userId = currentUser.getId();
        var contract = contractService.findContractById(model.getContractId());
        if (contract == null) {
            throw new AppException(ErrorModelConstants.CONTRACT_NOT_FOUND);
        }
        if (contract.getStatus() != ContractStatusEnum.ACTIVE) {
            throw new AppException(ErrorModelConstants.CONTRACT_INVALID);
        }
        var roomMember = roomMemberService.findByContractId(model.getContractId());

        if (!contract.getUserId().equals(userId) &&
                roomMember.stream().noneMatch(member -> member.getId().equals(userId))) {
            throw new AppException(ErrorModelConstants.USER_NOT_IN_ROOM);
        }
        var serviceRoom = serviceRoomRepository.findById(model.getServiceRoomId())
                .orElseThrow(() -> new AppException(ErrorModelConstants.SERVICE_ROOM_NOT_FOUND));
        if (!serviceRoom.getContractId().equals(model.getContractId())) {
            throw new AppException(ErrorModelConstants.SERVICE_ROOM_INVALID);
        }
        if (!ServiceRoomStatusEnum.ACTIVE.equals(serviceRoom.getStatus())) {
            throw new AppException(ErrorModelConstants.MAINTENANCE_REQUEST_IN_PROGRESS);
        }
        serviceRoom.setStatus(ServiceRoomStatusEnum.ERROR);
        serviceRoomRepository.save(serviceRoom);
        var entity = MaintenanceRequestMapper.INSTANCE.toEntity(model);
        entity.setStatus(MaintenanceRequestStatusEnum.IN_PROGRESS);
        entity.setRequestDate(LocalDateTime.now());
        return MaintenanceRequestMapper.INSTANCE.toModel(save(entity));
    }

    @Override
    public Page<MaintenanceRequestResponse> findAllWithPaging(PagingQueryConditionRequest request) {
        return repo.findAllWithPaging(request);
    }

    @Override
    public MaintenanceRequestModel findById(Long id) throws AppException {
        return null;
    }

    @Override
    public MaintenanceRequestModel update(Long id, MaintenanceRequestRequest request) throws AppException {
        var maintenanceRequest = repo.findById(id);

        //check if maintenanceRequest is null
        if (maintenanceRequest.isEmpty()) {
            throw new AppException(ErrorModelConstants.MAINTENANCE_REQUEST_NOT_FOUND);
        }
        var entity = maintenanceRequest.get();
        if (MaintenanceRequestStatusEnum.COMPLETED.equals(entity.getStatus()) ||
                MaintenanceRequestStatusEnum.CANCELLED.equals(entity.getStatus())) {
            throw new AppException(ErrorModelConstants.MAINTENANCE_REQUEST_INVALID);
        }
        if (MaintenanceRequestStatusEnum.COMPLETED.equals(request.getStatus())) {
            entity.setRequestDoneDate(LocalDateTime.now());
        }
        entity.setStatus(request.getStatus());
        entity.setTotalFee(Objects.nonNull(entity.getTotalFee()) ? entity.getTotalFee() : 0 + request.getPrice());
        repo.save(entity);
        var serviceRoom = serviceRoomRepository.findById(entity.getServiceRoomId())
                .orElseThrow(() -> new AppException(ErrorModelConstants.SERVICE_ROOM_NOT_FOUND));
        if (MaintenanceRequestStatusEnum.COMPLETED.equals(request.getStatus()) ||
                MaintenanceRequestStatusEnum.CANCELLED.equals(request.getStatus())) {
            serviceRoom.setStatus(ServiceRoomStatusEnum.ACTIVE);
            serviceRoomRepository.save(serviceRoom);
        } else if (MaintenanceRequestStatusEnum.PENDING.equals(request.getStatus())) {
            serviceRoom.setStatus(ServiceRoomStatusEnum.UNDER_MAINTENANCE);
            serviceRoomRepository.save(serviceRoom);
        }
        var maintenanceFee = new MaintenanceFeeEntity();
        maintenanceFee.setMaintenanceRequestId(entity.getId());
        maintenanceFee.setPrice(request.getPrice());
        maintenanceFee.setServiceRoomId(entity.getServiceRoomId());
        return MaintenanceRequestMapper.INSTANCE.toModel(entity);
    }
}
