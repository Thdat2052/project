package com.example.common.service.utils;

public class EntityUtils {
    private EntityUtils() {
    }
    public static boolean isCreate(Long id){
        return id==null || id ==0;
    }

}
