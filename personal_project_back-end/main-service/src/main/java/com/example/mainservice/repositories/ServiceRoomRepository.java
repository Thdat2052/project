package com.example.mainservice.repositories;

import com.example.common.service.entities.RoomEntity;
import com.example.common.service.entities.ServiceRoomEntity;
import com.example.common.service.repositories.jpas.JpaCommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRoomRepository extends JpaCommonRepository<ServiceRoomEntity, Long>, ServiceRoomRepositoryNative {
}
