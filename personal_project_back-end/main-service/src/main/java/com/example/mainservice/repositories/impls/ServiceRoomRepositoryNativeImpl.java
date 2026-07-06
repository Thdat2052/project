package com.example.mainservice.repositories.impls;

import com.example.common.service.components.BaseNativeQuery;
import com.example.common.service.models.ServiceModel;
import com.example.mainservice.repositories.ServiceRoomRepositoryNative;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServiceRoomRepositoryNativeImpl implements ServiceRoomRepositoryNative {
    BaseNativeQuery baseNativeQuery;

    @Override
    public List<ServiceModel> findAllByRoomId(Long roomId) {
        String sql = """
                SELECT 
                    sr.ID AS id,
                    sr.ROOM_ID AS roomId,
                    sr.SERVICE_ID AS serviceId,
                    sr.CREATED_AT AS createdAt,
                    sr.PRICE AS price,
                    sr.QUANTITY AS quantity,
                    s.NAME AS name,
                FROM SERVICE_ROOM sr
                LEFT JOIN SERVICE s ON s.id = sr.service_id
                WHERE sr.room_id = :roomId
                """;
        Map<String,Object> params = new HashMap<>();
        params.put("roomId", roomId);
        return baseNativeQuery.findList(sql,  ServiceModel.class, params);
    }

    @Override
    public List<ServiceModel> findByContractId(Long contractId) {
        String sql = """
                SELECT 
                    sr.ID AS id,
                    sr.CREATED_AT AS createdAt,
                    sr.PRICE AS price,
                    sr.QUANTITY AS quantity,
                    sr.STATUS AS status,
                    s.NAME AS name
                FROM SERVICE_ROOM sr
                LEFT JOIN SERVICE s ON s.id = sr.service_id
                WHERE sr.contract_id = :contractId
                """;
        return baseNativeQuery.findList(sql, ServiceModel.class, Map.of("contractId", contractId));
    }
}
