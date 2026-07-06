package com.example.mainservice.repositories.impls;


import com.example.common.service.components.BaseNativeQuery;
import com.example.common.service.models.UserModel;
import com.example.mainservice.repositories.RoomMemberRepositoryNative;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomMemberRepositoryNativeImpl implements RoomMemberRepositoryNative {
    BaseNativeQuery baseNativeQuery;
    @Override
    public List<UserModel> findByContractId(Long contractId) {
        String sql = """
                SELECT u.ID AS id, u.FULLNAME AS fullName, u.EMAIL AS email, u.PHONE_NUMBER AS phoneNumber,
                       u.CCCD AS cccd, u.username , u.PERMANENT_ADDRESS AS permanentAddress, u.LICENSE_PLATE_NUMBER AS licensePlateNumber
                FROM ROOM_MEMBER rm
                JOIN USER u ON rm.USER_ID = u.ID
                WHERE rm.CONTRACT_ID = :contractId
                """;
        return baseNativeQuery.findList(sql, UserModel.class, Map.of("contractId",contractId));
    }
}
