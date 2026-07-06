package com.example.mainservice.constants;

import com.example.common.service.models.ErrorModel;
import org.springframework.http.HttpStatus;

public class ErrorModelConstants {
    // list error model of user model
    public static final ErrorModel USER_NOT_FOUND = ErrorModel.of("001", "User not found");
    public static final ErrorModel USER_EXISTS = ErrorModel.of("002", "User already exists");
    public static final ErrorModel USER_INVALID = ErrorModel.of("003", "User invalid");
    public static final ErrorModel USER_NOT_ACTIVE = ErrorModel.of("004", "User not active");
    // list error model of room model
    public static final ErrorModel ROOM_NOT_FOUND = ErrorModel.of("101", "Room not found");
    public static final ErrorModel ROOM_EXISTS = ErrorModel.of("102", "Room already exists");
    public static final ErrorModel ROOM_INVALID = ErrorModel.of("103", "Room invalid");
    public static final ErrorModel ROOM_NOT_AVAILABLE = ErrorModel.of("104", "Room not available");
    public static final ErrorModel ROOM_NOT_ACTIVE = ErrorModel.of("105", "Room not active");
    public static final ErrorModel ROOM_MEMBER_NOT_FOUND = ErrorModel.of("106", "Room member not found");
    public static final ErrorModel USER_NOT_IN_ROOM = ErrorModel.of("107", "User not in room");
    // list error model of contract model
    public static final ErrorModel CONTRACT_NOT_FOUND = ErrorModel.of("201", "Contract not found");
    public static final ErrorModel CONTRACT_EXISTS = ErrorModel.of("202", "Contract already exists");
    public static final ErrorModel CONTRACT_INVALID = ErrorModel.of("203", "Contract invalid");
    public static final ErrorModel CONTRACT_USER_PERMISSION = ErrorModel.of("204", "Contract user permission invalid");
    // list error model of utility index
    public static final ErrorModel UTILITY_INDEX_WATER_INVALID = ErrorModel.of("301", "Water index is invalid");
    public static final ErrorModel UTILITY_INDEX_ELECTRIC_INVALID = ErrorModel.of("302", "Electric index is invalid");
    // list error model of service room
    public static final ErrorModel SERVICE_ROOM_NOT_FOUND = ErrorModel.of("401", "Service room not found");
    public static final ErrorModel SERVICE_ROOM_EXISTS = ErrorModel.of("402", "Service room already exists");
    public static final ErrorModel SERVICE_ROOM_INVALID = ErrorModel.of("403", "Service room invalid");
    //list error model maintenance request
    public static final ErrorModel MAINTENANCE_REQUEST_NOT_FOUND = ErrorModel.of("501", "Maintenance request not found");
    public static final ErrorModel MAINTENANCE_REQUEST_EXISTS = ErrorModel.of("502", "Maintenance request already exists");
    public static final ErrorModel MAINTENANCE_REQUEST_INVALID = ErrorModel.of("503", "Maintenance request is completed or invalid");
    public static final ErrorModel MAINTENANCE_REQUEST_IN_PROGRESS = ErrorModel.of("504", "Maintenance request in progress or can't send request!");
    //list error model checkout request
    public static final ErrorModel CHECKOUT_REQUEST_NOT_FOUND = ErrorModel.of("601", "Checkout request not found");
    public static final ErrorModel CHECKOUT_REQUEST_EXISTS = ErrorModel.of("602", "Checkout request already exists");
    public static final ErrorModel CHECKOUT_REQUEST_APPROVE_PERMISSION = ErrorModel.of("603", "Checkout request approve permission invalid");
    public static final ErrorModel CHECKOUT_REQUEST_CONTRACT_NOT_FOUND = ErrorModel.of("604", "Checkout request contract not found");
    public static final ErrorModel CHECKOUT_REQUEST_STATUS_INVALID = ErrorModel.of("605", "Checkout request status invalid");
    // list error model of role
    public static final ErrorModel ROLE_NOT_FOUND = ErrorModel.of("700", "Role not found");
    //list error model of invoice
    public static final ErrorModel INVOICE_NOT_FOUND = ErrorModel.of("800", "Invoice not found");
    public static final ErrorModel INVOICE_IS_PAID = ErrorModel.of("801", "Invoice is paid");
    public static ErrorModel ROOM_NOTFOUND = ErrorModel.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            "Room not found!");
    public static ErrorModel ROOM_ID_REQUIRED = ErrorModel.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            "Room Id is required!");
    public static ErrorModel ROOM_IS_RENTED = ErrorModel.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            "Room is rented");
    public static ErrorModel ROOM_MEMBER_IS_NOT_EMPTY = ErrorModel.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            "Room member is not empty!");
    public static ErrorModel SERVICE_NOTFOUND = ErrorModel.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            "Service notfound!");
    public static ErrorModel CREATE_USER_MEMBER_FAILED = ErrorModel.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Create user in room fail!");
    public static ErrorModel CREATE_CONTRACT_FAILED = ErrorModel.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Create contract fail!");
    private ErrorModelConstants() {
    }
}
