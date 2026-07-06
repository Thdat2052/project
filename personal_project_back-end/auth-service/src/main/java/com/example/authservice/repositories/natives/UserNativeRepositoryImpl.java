package com.example.authservice.repositories.natives;

import com.example.authservice.dtos.requests.UserDto;
import com.example.common.service.components.BaseNativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

public class UserNativeRepositoryImpl implements UserNativeRepository{

    @Autowired
    private BaseNativeQuery nativeQuery;

    @Override
    public List<UserDto> findUserInformation(String username) {
        String sql = """
                SELECT U.* , R.ID AS roleId, R.NAME AS roleName FROM USER U
                INNER JOIN USER_ROLE UR ON U.ID = UR.USER_ID
                INNER JOIN ROLE R ON R.ID = UR.ROLE_ID
                WHERE lower(U.USERNAME) = ?
                """;
        return nativeQuery.findList(sql,UserDto.class,username.trim().toLowerCase());
    }
}
