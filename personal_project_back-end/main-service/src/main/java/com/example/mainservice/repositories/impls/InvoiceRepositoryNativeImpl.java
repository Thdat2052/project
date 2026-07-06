package com.example.mainservice.repositories.impls;

import com.example.common.service.components.BaseNativeQuery;
import com.example.common.service.enums.ContractStatusEnum;
import com.example.common.service.enums.MaintenanceRequestStatusEnum;
import com.example.mainservice.models.InvoiceInformationModel;
import com.example.common.service.models.InvoiceModel;
import com.example.mainservice.models.InvoiceServiceInfoModel;
import com.example.mainservice.models.ServiceInRoomModel;
import com.example.mainservice.models.responses.RevenueStatResponse;
import com.example.mainservice.repositories.InvoiceRepositoryNative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InvoiceRepositoryNativeImpl implements InvoiceRepositoryNative {

    @Autowired
    private BaseNativeQuery nativeQuery;

    @Override
    public List<RevenueStatResponse> inventory() {
        String sql = """
                SELECT
                    MONTH(i.PAYMENT_DATE) AS month,
                    YEAR(i.PAYMENT_DATE) AS year,
                    SUM(i.TOTAL_PRICE) AS totalRevenue
                FROM INVOICE i
                WHERE i.STATUS = 'PAID'
                GROUP BY YEAR(i.PAYMENT_DATE), MONTH(i.PAYMENT_DATE)
                """;
        Map<String, Object> params = new HashMap<>();
        List<RevenueStatResponse> result = nativeQuery.findList(sql, RevenueStatResponse.class, params);
        return result;
    }

    @Override
    public RevenueStatResponse inventoryByMonth(int month, int year) {
        String sql = """
                SELECT
                    MONTH(i.PAYMENT_DATE) AS month,
                    YEAR(i.PAYMENT_DATE) AS year,
                    SUM(i.TOTAL_PRICE) AS totalRevenue
                FROM INVOICE i
                WHERE i.STATUS = 'PAID'
                  AND MONTH(i.PAYMENT_DATE) = :month
                  AND YEAR(i.PAYMENT_DATE) = :year
                GROUP BY YEAR(i.PAYMENT_DATE), MONTH(i.PAYMENT_DATE)
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("month", month);
        params.put("year", year);
        RevenueStatResponse result = nativeQuery.findOne(sql, RevenueStatResponse.class, params);
        return result;
    }

    @Override
    public RevenueStatResponse inventoryByQuarter(int quarter, int year) {
        String sql = """
                SELECT
                    QUARTER(i.PAYMENT_DATE) AS quarter,
                    YEAR(i.PAYMENT_DATE) AS year,
                    SUM(i.TOTAL_PRICE) AS totalRevenue
                FROM INVOICE i
                WHERE i.STATUS = 'PAID'
                  AND QUARTER(i.PAYMENT_DATE) = ?
                  AND YEAR(i.PAYMENT_DATE) = ?
                GROUP BY YEAR(i.PAYMENT_DATE), QUARTER(i.PAYMENT_DATE)""";
        RevenueStatResponse result = nativeQuery.findOne(sql, RevenueStatResponse.class, quarter, year);
        return result;
    }

    @Override
    public RevenueStatResponse inventoryByYear(int year) {
        String sql = """
                SELECT
                     YEAR(i.PAYMENT_DATE) AS year,
                     SUM(i.TOTAL_PRICE) AS totalRevenue
                 FROM INVOICE i
                 WHERE i.STATUS = 'PAID'
                   AND YEAR(i.PAYMENT_DATE) = ?
                 GROUP BY YEAR(i.PAYMENT_DATE)""";
        RevenueStatResponse result = nativeQuery.findOne(sql, RevenueStatResponse.class, year);
        return result;
    }

    @Override
    public List<InvoiceInformationModel> getRoomInvoiceServiceData(Long roomId, ContractStatusEnum contractStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("contractStatus", contractStatus.name());

        String sql = """
                select room.id as roomId, room.name as roomName, contract.id as contractId,
                contract.monthly_rent as monthlyRent, user.id userId, user.username,
                sr.id as serviceRoomId, sr.quantity, service.id serviceId, service.name serviceName,  sr.price, service.service_type
                from room join contract on room.id = contract.room_id and contract.status = :contractStatus
                join user on contract.user_id = user.id
                join service_room sr on contract.id = sr.contract_id
                left join service on sr.service_id = service.id and service.is_active = 1""";
        if(roomId !=null){
            sql+= " where room.id = :roomId";
            params.put("roomId", roomId);
        }
        List<InvoiceInformationModel> result = nativeQuery.findList(sql, InvoiceInformationModel.class, params);
        return result;
    }

    @Override
    public List<ServiceInRoomModel> getMaintenanceOfRoom(Long contractId, int month, int year, MaintenanceRequestStatusEnum maintenanceRequestStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("contractId", contractId);
        params.put("month", month);
        params.put("year", year);
        params.put("maintenanceRequestStatus", maintenanceRequestStatus.name());
        String sql = """
                select sv.id as serviceId, sv.name as serviceName, mf.price, ct.room_id as roomId, mr.id as maintenanceRequestId, mr.TOTAL_FEE totalMoney
                from maintenance_request mr
                join contract ct on ct.id = mr.contract_id
                join maintenance_fee mf on mr.id = mf.MAINTENANCE_REQUEST_ID
                join service_room svr on mf.service_room_id = svr.id
                join service sv on svr.SERVICE_ID = sv.id
                where year(mr.REQUEST_DONE_DATE) = :year and month(mr.REQUEST_DONE_DATE) = :month and ct.id = :contractId
                and mr.status = :maintenanceRequestStatus""";
        List<ServiceInRoomModel> result = nativeQuery.findList(sql, ServiceInRoomModel.class, params);
        return result;
    }

    @Override
    public List<InvoiceModel> getInvoiceByTime(ContractStatusEnum contractStatus, int month, int year) {
        Map<String, Object> params = new HashMap<>();
        params.put("contractStatus", contractStatus.name());
        params.put("month", month);
        params.put("year", year);
        String sql = """
               select room.id roomId, contract.id as contractId, contract.user_id userId,
                invoice.id invoiceId, invoice.CREATED_AT as invoiceCreated, invoice.status as invoiceStatus
             from room join contract on room.id = contract.room_id and contract.status = :contractStatus
             join invoice on contract.id = invoice.contract_id and  invoice.month_calculate= :month 
             and invoice.year_calculate =:year """;
        List<InvoiceModel> result = nativeQuery.findList(sql, InvoiceModel.class, params);
        return result;
    }

    @Override
    public List<ServiceInRoomModel> getMaintenanceOfContract(Long contractId) {
        Map<String, Object> params = new HashMap<>();
        params.put("contractId", contractId);
        String sql = """
                select sv.id as serviceId, sv.name as serviceName, mf.price, mr.room_id as roomId, mr.id as maintenanceRequestId
                from maintenance_request mr
                join maintenance_fee mf on mr.id = mf.MAINTENANCE_REQUEST_ID
                join service sv on mf.SERVICE_ID = sv.id
                where mr.contract_id = :contractId""";
        return nativeQuery.findList(sql, ServiceInRoomModel.class, params);
    }

    @Override
    public List<InvoiceServiceInfoModel> getInvoiceInfoByTime(int month, int year) {
        Map<String, Object> params = new HashMap<>();
        params.put("month", month);
        params.put("year", year);
        String sql = """
                select invoice.id as invoiceId, invoice.CREATED_AT as invoiceCreated, invoice.STATUS AS status,
               invoice.MONTH_CALCULATE as monthCal, invoice.YEAR_CALCULATE as yearCal,
               invoice.TOTAL_PRICE as totalInvoice, user.FULLNAME as fullName, room.id as roomId,
               service.id as serviceId, service.NAME as nameService, service.SERVICE_TYPE as serviceType,
               service_room.PRICE as priceService, service_room.QUANTITY as quantityService, room.price as roomPrice
                 from invoice
               inner join contract on invoice.CONTRACT_ID = contract.id
               inner join user on contract.USER_ID = user.id
               inner join room on contract.ROOM_ID = room.id
               inner join service_room on  contract.ID = service_room.CONTRACT_ID
               inner join service on service_room.SERVICE_ID = service.id
               where invoice.MONTH_CALCULATE = :month and invoice.YEAR_CALCULATE = :year""";
        return nativeQuery.findList(sql, InvoiceServiceInfoModel.class, params);
    }
}
