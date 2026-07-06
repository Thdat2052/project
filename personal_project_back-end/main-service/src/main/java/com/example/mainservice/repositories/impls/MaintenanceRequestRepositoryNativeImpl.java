package com.example.mainservice.repositories.impls;

import com.example.common.service.components.BaseNativeQuery;
import com.example.common.service.enums.ContractStatusEnum;
import com.example.common.service.enums.MaintenanceRequestStatusEnum;
import com.example.common.service.models.ContractModel;
import com.example.common.service.models.InvoiceModel;
import com.example.common.service.models.MaintenanceRequestModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.mainservice.models.InvoiceInformationModel;
import com.example.mainservice.models.InvoiceServiceInfoModel;
import com.example.mainservice.models.ServiceInRoomModel;
import com.example.mainservice.models.requests.MaintenanceRequestRequest;
import com.example.mainservice.models.responses.MaintenanceRequestResponse;
import com.example.mainservice.models.responses.RevenueStatResponse;
import com.example.mainservice.repositories.InvoiceRepositoryNative;
import com.example.mainservice.repositories.MaintenanceRequestRepositoryNative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MaintenanceRequestRepositoryNativeImpl implements MaintenanceRequestRepositoryNative {

    @Autowired
    private BaseNativeQuery baseNativeQuery;


    @Override
    public Page<MaintenanceRequestResponse> findAllWithPaging(PagingQueryConditionRequest pageRequest) {
        String tableSql = """
                FROM MAINTENANCE_REQUEST mr
                LEFT JOIN SERVICE_ROOM sr ON mr.SERVICE_ROOM_ID = sr.ID
                LEFT JOIN SERVICE s ON sr.SERVICE_ID = s.ID
                LEFT JOIN CONTRACT c ON c.ID = mr.CONTRACT_ID
                LEFT JOIN USER u ON c.USER_ID = u.ID
                LEFT JOIN ROOM r ON r.ID = c.ROOM_ID
                WHERE 1=1                    
                """;
        Map<String, Object> params = new HashMap<>();
        String sqlWhere = "";
        if (pageRequest.getKeyword() != null && !pageRequest.getKeyword().isEmpty()) {
            sqlWhere += " AND (r.NAME LIKE :keyword OR s.NAME LIKE :keyword OR r.NUMBER LIKE :keyword OR mr.DECISION LIKE :keyword)";
            params.put("keyword", "%" + pageRequest.getKeyword() + "%");
        }
        String countSql= "SELECT COUNT(*) " + tableSql + sqlWhere;
        String querySql= """
                SELECT
                    mr.ID AS id,
                    mr.REQUEST_DATE AS requestDate,
                    mr.REQUEST_DONE_DATE AS requestDoneDate,
                    mr.TOTAL_FEE AS totalFee,
                    mr.STATUS AS status,
                    mr.DECISION AS decision,
                    r.NAME AS roomName,
                    r.NUMBER AS roomNumber,
                    s.NAME AS serviceName
                """+ tableSql + sqlWhere + " ORDER BY mr.CREATED_AT DESC";
        PageRequest pageable = PageRequest.of(pageRequest.getPageCurrent(), pageRequest.getPageSize());
        return baseNativeQuery.findPage(querySql, countSql, pageable, MaintenanceRequestResponse.class, params);
    }
}
