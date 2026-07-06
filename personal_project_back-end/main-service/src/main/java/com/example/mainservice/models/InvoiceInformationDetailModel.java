package com.example.mainservice.models;

import com.example.common.service.models.InvoiceModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceInformationDetailModel{
    private InvoiceModel detail;
    private InvoiceInformationModel invoiceInformation;
    private List<ServiceInRoomModel> serviceOfRooms;
    private List<ServiceInRoomModel> serviceMaintenanceOfRooms;
    private long totalMoney;
}
