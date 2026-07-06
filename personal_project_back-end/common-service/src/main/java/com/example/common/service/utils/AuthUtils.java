package com.example.common.service.utils;

import com.example.common.service.securities.CustomUserDetail;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;


public class AuthUtils {

    private AuthUtils() {
    }

    public static String getTokenFromHeader(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (!hasAuthorizationBearer(request)) {
            throw new SecurityException("Missing or invalid Authorization header");
        }
        return authorizationHeader.substring(7);
    }

    public static boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }

        return true;
    }

    public static Map<String, String> getHeaderDefaultWithToken(String token){
        Map<String, String> headers =  new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        return headers;
    }

    public static CustomUserDetail getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetail) {
            return (CustomUserDetail) authentication.getPrincipal();
        }
        return null;
    }

    public static String getCurrentUsername() {
        CustomUserDetail user = getCurrentUser();
        return user != null ? user.getUsername() : null;
    }

}