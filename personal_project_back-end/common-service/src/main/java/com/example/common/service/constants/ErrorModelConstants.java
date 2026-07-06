package com.example.common.service.constants;

import com.example.common.service.models.ErrorModel;

public class ErrorModelConstants {

    // list error model of user model
    public static final ErrorModel USER_NOT_FOUND = ErrorModel.of("001", "User not found");
    public static final ErrorModel USER_EXISTS = ErrorModel.of("002", "User already exists");
    public static final ErrorModel USER_INVALID = ErrorModel.of("003", "User invalid");
    public static final ErrorModel USER_NOT_ACTIVE = ErrorModel.of("004","User not active");

    // list error model of room model
    public static final ErrorModel ROOM_NOT_FOUND = ErrorModel.of("101", "Room not found");
    public static final ErrorModel ROOM_EXISTS = ErrorModel.of("102", "Room already exists");
    public static final ErrorModel ROOM_INVALID = ErrorModel.of("103", "Room invalid");
    public static final ErrorModel ROOM_NOT_AVAILABLE = ErrorModel.of("104", "Room not available");
    public static final ErrorModel ROOM_NOT_ACTIVE = ErrorModel.of("105", "Room not active");


    // list error model of contract model
    public static final ErrorModel CONTRACT_NOT_FOUND = ErrorModel.of("201", "Contract not found");
    public static final ErrorModel CONTRACT_EXISTS = ErrorModel.of("202", "Contract already exists");
    public static final ErrorModel CONTRACT_INVALID = ErrorModel.of("203", "Contract invalid");

    // list error model of utility index
    public static final ErrorModel UTILITY_INDEX_WATER_INVALID = ErrorModel.of("301", "Water index is invalid");
    public static final ErrorModel UTILITY_INDEX_ELECTRIC_INVALID = ErrorModel.of("302", "Electric index is invalid");

}
