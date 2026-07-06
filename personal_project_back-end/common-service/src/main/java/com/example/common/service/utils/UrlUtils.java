package com.example.common.service.utils;

import java.util.Arrays;
import java.util.List;

public abstract class UrlUtils {

    public static final String API_VERSION = "/v1";
    public static final String API_PREFIX = "/api";
    public static final String BASE_URL = API_PREFIX + API_VERSION;


    // nameService_nameEntity or nameFunction_(prefix Url) example. MAIN_SERVICE_URL
    public static final String INVENTORY_URL = BASE_URL + "/inventories";
    public static final String USER_URL = BASE_URL + "/users";
    public static final String AUTH_URL = BASE_URL + "/auth";
    public static final String SERVICE_URL = BASE_URL + "/services";
    public static final String CONTRACT_URL = BASE_URL + "/contracts";
    public static final String CHECKOUT_REQUEST_URL = BASE_URL + "/checkout-requests";
    public static final String MAINTENANCE_REQUEST_URL = BASE_URL + "/maintenance-requests";
    public static final String ROOM_URL = BASE_URL + "/rooms";
    public static final String UTILITY_INDEX_URL = BASE_URL + "/utility-indexes";
    public static final String INVOICE_URL = BASE_URL + "/invoices";
    public static final String REPORT_URL = BASE_URL + "/reports";
    public static final String FILE_URL = BASE_URL + "/files";
    public static final String SENSOR_URL = BASE_URL + "/sensors";

    public static final List<String> PUBLIC_URLS = Arrays.asList(
            AUTH_URL,
            AUTH_URL+ "/register",
            AUTH_URL+ "/login",
            AUTH_URL+ "/validate",
            AUTH_URL+ "/users/reset-password",
            AUTH_URL+ "/token",
            AUTH_URL+ "/rollback",
            "/ws/**",
            SENSOR_URL
    );

    public static  final String getUrl(String domainUrl, String apiPath, String resourcePath){
        return (StringUtils.isBlank(resourcePath)) ? domainUrl  + apiPath  : domainUrl  + apiPath + resourcePath;
    }
}
