package com.example.mainservice.repositories.impls;

import com.example.common.service.components.BaseNativeQuery;
import com.example.common.service.enums.InvoiceStatusEnum;
import com.example.common.service.enums.RoomStatusEnum;
import com.example.common.service.models.QueryInfoModel;
import com.example.common.service.models.RoomModel;
import com.example.common.service.utils.StringUtils;
import com.example.mainservice.models.requests.RoomConditionRequest;
import com.example.mainservice.models.responses.RoomContractResponse;
import com.example.mainservice.models.responses.UnpaidCustomerResponse;
import com.example.mainservice.repositories.RoomRepositoryNative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoomRepositoryNativeImpl implements RoomRepositoryNative {
    @Autowired
    private BaseNativeQuery nativeQuery;

    @Override
    public List<UnpaidCustomerResponse> getUnpaidCustomers(InvoiceStatusEnum status) {
        String sql = """
                SELECT
                i.ID AS invoiceId,
                ct.ROOM_ID AS roomId,
                r.NAME AS roomName,
                r.NUMBER AS roomNumber,
                ct.USER_ID AS userId,
                u.USERNAME AS username,
                i.STATUS AS status,
                i.TOTAL_PRICE AS totalPrice,
                i.PAYMENT_DATE AS paymentDate
                FROM INVOICE i
                JOIN CONTRACT ct ON  ct.id = i.contract_id
                JOIN ROOM r ON ct.ROOM_ID = r.ID
                JOIN USER u ON ct.USER_ID = u.ID
                WHERE i.STATUS = ?
                """;
        List<UnpaidCustomerResponse> result = nativeQuery.findList(sql, UnpaidCustomerResponse.class, status.name());
        return result;
    }

    @Override
    public List<RoomContractResponse> findActiveContractsInNextWeek(int numberDay) {
        String sql = """
                SELECT
                    r.id AS roomId,
                    r.NAME AS roomName,
                    r.NUMBER AS roomNumber,
                    u.id AS userId,
                    u.USERNAME AS username,
                    c.END_DATE AS endDate
                FROM CONTRACT c
                JOIN ROOM r ON c.ROOM_ID = r.ID
                JOIN USER u ON c.USER_ID = u.ID 
                WHERE c.STATUS = 'ACTIVE'
                  AND c.END_DATE BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? DAY)
                  """;
        List<RoomContractResponse> result = nativeQuery.findList(sql, RoomContractResponse.class, numberDay);
        return result;
    }

    @Override
    public Page<RoomModel> findAllByPaging(RoomConditionRequest pageRequest) {
        QueryInfoModel queryInfoModel = getQueryAndParamsWithPaging(pageRequest);
        PageRequest paging = PageRequest.of(pageRequest.getPageCurrent(), pageRequest.getPageSize());
        return nativeQuery.findPage(queryInfoModel.getSql(), queryInfoModel.getCountSql(), paging, RoomModel.class,
                queryInfoModel.getParams());
    }

    private QueryInfoModel getQueryAndParamsWithPaging(RoomConditionRequest pageRequest) {
        StringBuilder sqlBuilder = new StringBuilder("""
        SELECT
            r.ID AS id,
            r.NAME AS name,
            r.NUMBER AS number,
            r.STATUS AS status,
            r.CREATED_AT AS createdAt,
            r.WIDTH AS width,
            r.LENGTH AS length,
            r.NOTE AS note,
            r.PRICE AS price
        FROM ROOM r WHERE 1=1
        """);

        StringBuilder countSqlBuilder = new StringBuilder("""
        SELECT COUNT(r.ID)
        FROM ROOM r
        WHERE 1=1
         """);

        Map<String, Object> params = new HashMap<>();

        if (pageRequest.getStatus() != null) {
            sqlBuilder.append(" AND r.STATUS = :status");
            countSqlBuilder.append(" AND r.STATUS = :status");
            params.put("status", pageRequest.getStatus().name());
        }

        if (!StringUtils.isBlank(pageRequest.getKeyword())) {
            String condition = """
                    AND (
                        r.NAME LIKE :search
                        OR r.NUMBER LIKE :search
                        OR r.STATUS LIKE :search
                        OR r.WIDTH LIKE :search
                        OR r.LENGTH LIKE :search
                    )
            """;
            sqlBuilder.append(condition);

            countSqlBuilder.append(condition);

            params.put("search", "%" + pageRequest.getKeyword() + "%");
        }

        return QueryInfoModel.builder()
                .sql(sqlBuilder.toString())
                .countSql(countSqlBuilder.toString())
                .params(params)
                .build();
    }

}