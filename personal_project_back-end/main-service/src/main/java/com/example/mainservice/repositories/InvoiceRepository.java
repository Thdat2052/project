package com.example.mainservice.repositories;

import com.example.common.service.entities.InvoiceEntity;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaCommonRepository<InvoiceEntity, Long>, InvoiceRepositoryNative {
    List<InvoiceEntity> findAllByContractIdAndYearCalculateAndMonthCalculate(Long contractId,
                        Integer yearCalculate, Integer monthCalculate);
}
