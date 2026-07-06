package com.example.mainservice.services.impls;

import com.example.common.service.constants.ErrorModelConstants;
import com.example.common.service.entities.ServiceEntity;
import com.example.common.service.entities.UtilityIndexEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.mappers.UtilityIndexMapper;
import com.example.common.service.models.UtilityIndexModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.services.impls.CommonServiceImpl;
import com.example.common.service.utils.EntityUtils;
import com.example.mainservice.models.MonthYearModel;
import com.example.mainservice.repositories.ServiceRepository;
import com.example.mainservice.repositories.UtilityIndexRepository;
import com.example.mainservice.services.AmenityService;
import com.example.mainservice.services.ContractService;
import com.example.mainservice.services.UtilityIndexService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UtilityIndexServiceImpl extends CommonServiceImpl<UtilityIndexEntity, Long, UtilityIndexRepository>
        implements UtilityIndexService {
    private final ContractService contractService;
    protected UtilityIndexServiceImpl(UtilityIndexRepository repo, ContractService contractService) {
        super(repo);
        this.contractService = contractService;
    }

    @Override
    @Transactional
    public UtilityIndexModel createOrUpdate(UtilityIndexModel utilityIndexModel) throws AppException {
        Long id = utilityIndexModel.getId();
        Long waterUsed = utilityIndexModel.getWaterNewIndex() - utilityIndexModel.getWaterOldIndex();
        Long electricUsed = utilityIndexModel.getElectricityNewIndex() - utilityIndexModel.getElectricityOldIndex();

        if(waterUsed < 0 ) throw new AppException(ErrorModelConstants.UTILITY_INDEX_WATER_INVALID);
        if(electricUsed < 0 ) throw new AppException(ErrorModelConstants.UTILITY_INDEX_ELECTRIC_INVALID);
        LocalDate now = LocalDate.now();
        if(utilityIndexModel.getMonthMeasure() == null) utilityIndexModel.setMonthMeasure(now.getMonthValue());
        if(utilityIndexModel.getYearMeasure() == null) utilityIndexModel.setYearMeasure(now.getYear());
        var entity = UtilityIndexMapper.INSTANCE.toEntity(utilityIndexModel);
        if(!EntityUtils.isCreate(id)){ // is update
            entity = getById(id);
        }
        entity.setElectricUsage(electricUsed);
        entity.setWaterUsage(waterUsed);
        return UtilityIndexMapper.INSTANCE.toModel(repo.save(entity));
    }

    @Override
    public UtilityIndexEntity findByRoomIdAndMonthMeasureAndYearMeasure(Long roomId, Integer month, Integer year) {
        return repo.findByRoomIdAndMonthMeasureAndYearMeasure(roomId,month,year);
    }
    @Override
    public List<UtilityIndexModel> findByContractId(Long contractId) throws AppException {
        var contract = contractService.findContractById(contractId);
        if(contract == null) throw new AppException(ErrorModelConstants.CONTRACT_NOT_FOUND);
        YearMonth start = YearMonth.from(contract.getStartDate());
        YearMonth end   = YearMonth.now();
        long monthsBetween = start.until(end, ChronoUnit.MONTHS);
        Set<YearMonth> validMonths = IntStream
                .rangeClosed(0, (int) monthsBetween)
                .mapToObj(start::plusMonths)
                .collect(Collectors.toSet());
        List<UtilityIndexEntity> validIndexes = repo.findByRoomId(contract.getRoomId())
                .stream()
                .filter(u -> validMonths.contains(
                        YearMonth.of(u.getYearMeasure(), u.getMonthMeasure())
                ))
                .collect(Collectors.toList());

        return UtilityIndexMapper.INSTANCE.toModelList(validIndexes);
    }

    @Override
    public List<UtilityIndexModel> findAllWithRoomIsRented() {
        return repo.findAllWithRoomIsRented();
    }
    @Override
    public Page<UtilityIndexModel> findAllWithPaging(PagingQueryConditionRequest request) throws AppException {
        return repo.findAllWithPaging(request);
    }
}
