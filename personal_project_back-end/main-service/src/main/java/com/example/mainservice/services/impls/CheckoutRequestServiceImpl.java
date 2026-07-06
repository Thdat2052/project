package com.example.mainservice.services.impls;

import com.example.common.service.entities.CheckoutRequestEntity;
import com.example.common.service.enums.CheckoutRequestStatusEnum;
import com.example.common.service.enums.ContractStatusEnum;
import com.example.common.service.enums.RoleEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.internals.apis.AuthApis;
import com.example.common.service.mappers.CheckoutRequestMapper;
import com.example.common.service.mappers.ContractMapper;
import com.example.common.service.models.CheckoutRequestModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.services.impls.CommonServiceImpl;
import com.example.common.service.utils.AuthUtils;
import com.example.mainservice.constants.ErrorModelConstants;
import com.example.mainservice.repositories.CheckoutRequestRepository;
import com.example.mainservice.services.CheckoutRequestService;
import com.example.mainservice.services.ContractService;
import com.example.mainservice.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CheckoutRequestServiceImpl extends CommonServiceImpl<CheckoutRequestEntity, Long, CheckoutRequestRepository>
        implements CheckoutRequestService {
    private final CheckoutRequestRepository repository;
    private final ContractService contractService;
    private final UserService userService;

    public CheckoutRequestServiceImpl(CheckoutRequestRepository repository, ContractService contractService, UserService userService) {
        super(repository);
        this.repository = repository;
        this.contractService = contractService;
        this.userService = userService;
    }

    @Override
    public CheckoutRequestModel create(CheckoutRequestModel request) throws AppException {
        var currentUser= userService.findMe();
        var contract = contractService.findByRoomId(request.getRoomId());
        if (contract == null) {
            throw new AppException(ErrorModelConstants.CONTRACT_NOT_FOUND);
        }
        if (!contract.getUserId().equals(currentUser.getId()) || contract.getStatus() != ContractStatusEnum.ACTIVE) {
            throw new AppException(ErrorModelConstants.CONTRACT_USER_PERMISSION);
        }
        if(repository.existsByRoomIdAndStatus(request.getRoomId(), CheckoutRequestStatusEnum.IN_PROGRESS)) {
            throw new AppException(ErrorModelConstants.CHECKOUT_REQUEST_EXISTS);
        }
        var entity = CheckoutRequestMapper.INSTANCE.toEntity(request);
        entity.setUserId(currentUser.getId());
        entity.setStatus(CheckoutRequestStatusEnum.IN_PROGRESS);
        entity.setRequestDate(LocalDateTime.now());
        return CheckoutRequestMapper.INSTANCE.toModel(repository.save(entity));
    }

    @Override
    public CheckoutRequestModel approve(Long id, CheckoutRequestStatusEnum status) throws AppException {
        var currentUser = userService.findMe();
        if(currentUser.getRoles().stream()
                .noneMatch(role -> role.equals(RoleEnum.ADMIN))) {
            throw new AppException(ErrorModelConstants.CHECKOUT_REQUEST_APPROVE_PERMISSION);
        }
        var entity = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorModelConstants.CHECKOUT_REQUEST_NOT_FOUND));
        var contract = contractService.findByRoomId(entity.getRoomId());
        if (contract == null) {
            throw new AppException(ErrorModelConstants.CHECKOUT_REQUEST_CONTRACT_NOT_FOUND);
        }
        if(CheckoutRequestStatusEnum.IN_PROGRESS.equals(entity.getStatus()) ||
                CheckoutRequestStatusEnum.PENDING.equals(entity.getStatus())) {
            entity.setStatus(status);
            contract.setStatus(ContractStatusEnum.COMPLETED);
            contractService.save(ContractMapper.INSTANCE.toEntity(contract));
            return CheckoutRequestMapper.INSTANCE.toModel(repository.save(entity));
        }
        throw new AppException(ErrorModelConstants.CHECKOUT_REQUEST_STATUS_INVALID);
    }

    @Override
    public Page<CheckoutRequestModel> findAllWithPaging(PagingQueryConditionRequest request) throws AppException {
        return repo.findAllWithPaging(request);
    }
}
