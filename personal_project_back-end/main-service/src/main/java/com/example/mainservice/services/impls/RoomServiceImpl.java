package com.example.mainservice.services.impls;

import com.example.common.service.constants.ErrorModelConstants;
import com.example.common.service.enums.InvoiceStatusEnum;
import com.example.common.service.enums.RoomStatusEnum;
import com.example.common.service.mappers.RoomMapper;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.models.RoomModel;
import com.example.common.service.entities.RoomEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.services.impls.CommonServiceImpl;
import com.example.mainservice.models.requests.RoomConditionRequest;
import com.example.mainservice.models.responses.RoomContractResponse;
import com.example.mainservice.models.responses.UnpaidCustomerResponse;
import com.example.mainservice.repositories.RoomRepository;
import com.example.mainservice.services.RoomService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomServiceImpl extends CommonServiceImpl<RoomEntity, Long, RoomRepository>
        implements RoomService {
    protected RoomServiceImpl(RoomRepository repo) {
        super(repo);
    }


    @Override
    @Transactional
    public RoomModel createOrUpdate(RoomModel model) throws Exception {
        RoomEntity roomEntity = RoomMapper.INSTANCE.toEntity(model);
        if(!ObjectUtils.isEmpty(roomEntity.getId()) && roomEntity.getId() > 0) {
            var oldRoom = repo.findById(roomEntity.getId()).orElseThrow(() ->
                    new AppException(ErrorModelConstants.ROOM_NOT_FOUND));
            if(repo.existsByIdNotAndNumber(roomEntity.getId(), roomEntity.getNumber()))
                throw new AppException(ErrorModelConstants.ROOM_EXISTS);
        }
//        else if (repo.existsByNumber(roomEntity.getNumber())) {
//            roomEntity.setStatus(RoomStatusEnum.AVAILABLE);
//            throw new AppException(ErrorModelConstants.ROOM_EXISTS);
//        }
        RoomEntity savedRoom = repo.save(roomEntity);

        return RoomMapper.INSTANCE.toModel(savedRoom);
    }

    @Override
    public Page<RoomModel> findAllWithPaging(RoomConditionRequest roomConditionRequest){
        return repo.findAllByPaging(roomConditionRequest);
    }

    @Override
    @Transactional
    @Modifying
    public void deleteByIdIn(List<Long> ids) {
        var entities = repo.findByIdInAndStatus(ids, RoomStatusEnum.RENTED);
        if(!entities.isEmpty()) throw new AppException("Has room is rented");
        repo.deleteByIdIn(ids);
    }

    @Override
    public boolean existsByIdAndStatus(Long id, RoomStatusEnum status) {
        return false;
    }
    @Override
    public List<UnpaidCustomerResponse> getUnpaidCustomers(InvoiceStatusEnum status) {
        return repo.getUnpaidCustomers(status);
    }

    @Override
    public List<RoomContractResponse> findActiveContractsInNextWeek(int numberDay) {
        return repo.findActiveContractsInNextWeek(numberDay);
    }

    @Override
    public List<RoomEntity> findAllByStatus(RoomStatusEnum statusEnum){
        return repo.findAllByStatus(statusEnum);
    }
}
