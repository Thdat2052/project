package com.example.mainservice.repositories;


import com.example.common.service.enums.ContractStatusEnum;
import com.example.common.service.enums.MaintenanceRequestStatusEnum;
import com.example.mainservice.models.InvoiceInformationModel;
import com.example.common.service.models.InvoiceModel;
import com.example.mainservice.models.InvoiceServiceInfoModel;
import com.example.mainservice.models.ServiceInRoomModel;
import com.example.mainservice.models.responses.RevenueStatResponse;

import java.util.List;


public interface InvoiceRepositoryNative {
    List<RevenueStatResponse> inventory();
    RevenueStatResponse inventoryByMonth(int month, int year);
    RevenueStatResponse inventoryByQuarter(int quarter, int year);
    RevenueStatResponse inventoryByYear(int year);
    List<InvoiceInformationModel> getRoomInvoiceServiceData(Long roomId, ContractStatusEnum contractStatus);
    List<ServiceInRoomModel> getMaintenanceOfRoom(Long contractId, int month, int year, MaintenanceRequestStatusEnum maintenanceRequestStatus);
    List<InvoiceModel> getInvoiceByTime(ContractStatusEnum contractStatus, int month, int year);
    List<ServiceInRoomModel>  getMaintenanceOfContract( Long contractId);
    List<InvoiceServiceInfoModel> getInvoiceInfoByTime(int month, int year);
}