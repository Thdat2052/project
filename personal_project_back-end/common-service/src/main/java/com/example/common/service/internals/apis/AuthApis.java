package com.example.common.service.internals.apis;

import com.example.common.service.components.ApiHttpClient;
import com.example.common.service.enums.ServiceNameEnum;
import com.example.common.service.models.UserModel;
import com.example.common.service.models.requests.TokenValidationRequest;
import com.example.common.service.models.responses.ApiResponse;
import com.example.common.service.utils.UrlUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class AuthApis {
    @Autowired
    private Map<ServiceNameEnum, String> serviceUrlMap;
    @Autowired
    private ApiHttpClient apiHttpClient;

    public ApiResponse<Boolean> validateToken(TokenValidationRequest tokenValidationRequest) throws IOException, InterruptedException {
        String url = UrlUtils.getUrl(serviceUrlMap.get(ServiceNameEnum.AUTH_SERVICE), UrlUtils.AUTH_URL, "/validate");
        return apiHttpClient.post(url,null,null, tokenValidationRequest, new TypeReference<Boolean>() {});
    }


    public ApiResponse<List<UserModel>> createUsers(List<UserModel> userModelList, Map<String, String> headers) throws IOException, InterruptedException {
        String url = UrlUtils.getUrl(serviceUrlMap.get(ServiceNameEnum.AUTH_SERVICE), UrlUtils.AUTH_URL,"");
        return apiHttpClient.post(url, headers, null, userModelList, new TypeReference<List<UserModel>>() {});
    }

    public ApiResponse<Boolean> rollBackWhenCreatedContractFail(Map<String, String> headers) throws IOException, InterruptedException {
        String url = UrlUtils.getUrl(serviceUrlMap.get(ServiceNameEnum.AUTH_SERVICE), UrlUtils.AUTH_URL,"/rollback");
        return apiHttpClient.get(url, headers, null, new TypeReference<Boolean>() {});
    }

    public ApiResponse<UserModel> getInfoFromToken(Map<String, String> headers) throws IOException, InterruptedException {
        String url = UrlUtils.getUrl(serviceUrlMap.get(ServiceNameEnum.AUTH_SERVICE), UrlUtils.AUTH_URL,"/token");
        return apiHttpClient.get(url, headers, null, new TypeReference<UserModel>() {});
    }

}
