package com.example.mainservice.repositories;

import com.example.common.service.entities.MaintenanceFeeEntity;
import com.example.common.service.entities.MaintenanceRequestEntity;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MaintenanceFeeRepository extends JpaCommonRepository<MaintenanceFeeEntity, Long>{
}
