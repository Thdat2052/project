package com.example.mainservice.repositories;

import com.example.common.service.entities.InvoiceEntity;
import com.example.common.service.entities.UtilityIndexEntity;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import com.example.mainservice.models.MonthYearModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public interface UtilityIndexRepository extends JpaCommonRepository<UtilityIndexEntity, Long>, UtilityIndexRepositoryNative {
    UtilityIndexEntity findByRoomIdAndMonthMeasureAndYearMeasure(Long roomId, Integer month, Integer year);
    List<UtilityIndexEntity> findByRoomId(Long roomId);
}
