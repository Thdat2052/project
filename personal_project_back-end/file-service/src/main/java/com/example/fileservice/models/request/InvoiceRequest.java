package com.example.fileservice.models.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceRequest {

    private Detail detail;
    private InvoiceInformation invoiceInformation;
    private List<ServiceItem> serviceOfRooms;
    private List<MaintenanceServiceItem> serviceMaintenanceOfRooms;
    private Integer totalMoney;

    // Getters & Setters
    @Data
    public static class Detail {
        private Integer roomId;
        private Integer contractId;
        private Integer invoiceId;
        private Integer userId;
        private LocalDateTime invoiceCreated;

        // Getters & Setters
    }
    @Data
    public static class InvoiceInformation {
        private Integer maintenanceRequestId;
        private Integer serviceId;
        private String serviceName;
        private Integer price;
        private Integer quantity;
        private Integer totalMoney;
        private Integer roomId;
        private String roomName;
        private Integer contractId;
        private Integer monthlyRent;
        private Integer userId;
        private String username;

        // Getters & Setters
    }
    @Data
    public static class ServiceItem {
        private Integer maintenanceRequestId;
        private Integer serviceId;
        private String serviceName;
        private Integer price;
        private Integer quantity;
        private Integer totalMoney;

        // Getters & Setters
    }
    @Data
    public static class MaintenanceServiceItem {
        private Integer maintenanceRequestId;
        private Integer serviceId;
        private String serviceName;
        private Integer price;
        private Integer quantity;
        private Integer totalMoney;

        // Getters & Setters
    }

}
