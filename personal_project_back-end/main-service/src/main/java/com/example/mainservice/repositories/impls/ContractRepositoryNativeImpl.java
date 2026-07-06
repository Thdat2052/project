package com.example.mainservice.repositories.impls;

import com.example.common.service.components.BaseNativeQuery;
import com.example.common.service.entities.ContractEntity;
import com.example.common.service.models.ContractModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.models.responses.ContractResponse;
import com.example.mainservice.repositories.ContractRepositoryNative;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContractRepositoryNativeImpl implements ContractRepositoryNative {
    BaseNativeQuery baseNativeQuery;

    @Override
    public Page<ContractModel> findAllWithPaging(PagingQueryConditionRequest pageRequest) {
        String baseQuery = "FROM CONTRACT JOIN USER ON CONTRACT.USER_ID = USER.ID " +
                "JOIN ROOM ON CONTRACT.ROOM_ID = ROOM.ID";
        String whereClause = "";
        Map<String,Object> params = new HashMap<>();
        if (!ObjectUtils.isEmpty(pageRequest.getKeyword())) {
            String keyword = "%" + pageRequest.getKeyword().trim() + "%";
            whereClause = " WHERE ID LIKE :keyword";
            params.put("keyword", keyword);
        }
        String countSql = "SELECT COUNT(*) " + baseQuery + whereClause;
        PageRequest pageable = PageRequest.of(pageRequest.getPageCurrent(), pageRequest.getPageSize());
        String querySql = "SELECT CONTRACT.ID, CONTRACT.DEPOSIT, CONTRACT.MONTHLY_RENT,CONTRACT.USER_ID,CONTRACT.ROOM_ID,CONTRACT.STATUS, CONTRACT.START_DATE, CONTRACT.END_DATE " +
                "ROOM.NUMBER AS roomNumber, USER.FULL_NAME AS userName" +
                baseQuery + whereClause +
                " ORDER BY CONTRACT.CREATED_AT DESC LIMIT :limit OFFSET :offset";
        return baseNativeQuery.findPage(querySql, countSql, pageable, ContractModel.class, params);
    }
    @Override
    public ContractResponse findContractById(Long id) {
        String sql = """
                SELECT
                    c.ID AS id,
                    c.DEPOSIT AS deposit,
                    c.MONTHLY_RENT AS monthlyRent,
                    c.USER_ID AS userId,
                    c.ROOM_ID AS roomId,
                    c.STATUS AS status,
                    c.START_DATE AS startDate,
                    c.END_DATE AS endDate,
                    r.NUMBER AS roomNumber,
                    r.NAME AS roomName,
                    u.FULLNAME AS fullName
                FROM CONTRACT c
                JOIN USER u ON c.USER_ID = u.ID
                JOIN ROOM r ON c.ROOM_ID = r.ID
                WHERE c.ID = :id
                """;
        return baseNativeQuery.findOne(sql, ContractResponse.class, Map.of("id", id));
    }

    @Override
    public List<ContractResponse> findByUserIdIn(List<Long> ids) {
        String sql = """
                SELECT c.ID AS id,
                    c.DEPOSIT AS deposit,
                    c.MONTHLY_RENT AS monthlyRent,
                    c.USER_ID AS userId,
                    c.ROOM_ID AS roomId,
                    c.STATUS AS status,
                    c.START_DATE AS startDate,
                    c.END_DATE AS endDate,
                    r.NUMBER AS roomNumber,
                    r.NAME AS roomName,
                    u.FULLNAME AS fullName
                FROM CONTRACT c
                JOIN USER u ON c.USER_ID = u.ID
                JOIN ROOM r ON c.ROOM_ID = r.ID
                WHERE c.USER_ID IN (:ids)
                """;
        return baseNativeQuery.findList(sql, ContractResponse.class, Map.of("ids", ids));
    }
}
