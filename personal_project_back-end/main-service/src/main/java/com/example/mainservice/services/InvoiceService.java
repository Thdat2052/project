package com.example.mainservice.services;

import com.example.common.service.entities.InvoiceEntity;
import com.example.common.service.enums.ContractStatusEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.services.CommonService;
import com.example.mainservice.models.*;
import com.example.common.service.models.InvoiceModel;
import com.example.mainservice.models.responses.RevenueStatResponse;

import java.util.List;

public interface InvoiceService extends CommonService<InvoiceEntity, Long> {
    List<RevenueStatResponse> inventory();
    RevenueStatResponse inventoryByMonth(int month, int year);
    RevenueStatResponse inventoryByQuarter(int quarter, int year);
    RevenueStatResponse inventoryByYear(int year);
    List<InvoiceInformationModel> getRoomInvoiceServiceData(Long roomId, ContractStatusEnum contractStatus);
    List<ServiceInRoomModel> getMaintenanceOfRoom(Long roomId, Integer month, Integer year);
    List<InvoiceModel> getInvoiceByTime(ContractStatusEnum statusEnum, int month, int year);
    List<InvoiceInformationDetailModel> calculateInvoice(Long roomId, Integer month, Integer year);
    List<InvoiceInformationDetailModel> getMyInvoices();
    List<InvoiceServiceInfoDetailModel> getInvoiceInfoByTime(int month, int year);
    void deleteById(Long id);
    void confirmPayment(Long id) throws AppException;
}
