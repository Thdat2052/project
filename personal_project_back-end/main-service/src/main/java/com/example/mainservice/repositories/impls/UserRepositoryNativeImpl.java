package com.example.mainservice.repositories.impls;

import com.example.common.service.components.BaseNativeQuery;
import com.example.common.service.entities.UserEntity;
import com.example.common.service.models.UserRoleModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.mainservice.repositories.UserRepositoryNative;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRepositoryNativeImpl implements UserRepositoryNative {
    BaseNativeQuery baseNativeQuery;

    @Override
    public Page<UserEntity> findAllByPaging(PagingQueryConditionRequest pageRequest) {
        String baseQuery = "FROM USER u ";
        String whereClause = "";
        Map<String, Object> params = new HashMap<>();

        if (!ObjectUtils.isEmpty(pageRequest.getKeyword())) {
            String keyword = "%" + pageRequest.getKeyword().trim() + "%";
            whereClause = " WHERE u.USERNAME LIKE :keyword OR u.CCCD LIKE :keyword OR u.PHONE_NUMBER LIKE :keyword " +
                    "OR u.FULLNAME LIKE :keyword OR u.PERMANENT_ADDRESS LIKE :keyword OR u.EMAIL LIKE :keyword ";
            params.put("keyword", keyword);
        }

        String countSql = "SELECT COUNT(*) " + baseQuery + whereClause;


        PageRequest pageable = PageRequest.of(pageRequest.getPageCurrent(), pageRequest.getPageSize());
        String querySql = "SELECT u.ID, u.USERNAME, u.CCCD ,u.LICENSE_PLATE_NUMBER ,u.PHONE_NUMBER, u.FULLNAME, u.PERMANENT_ADDRESS, u.EMAIL, u.ACTIVE " +
                baseQuery + whereClause;
        return baseNativeQuery.findPage(querySql, countSql, pageable, UserEntity.class, params);
    }

    @Override
    public List<UserRoleModel> findAllUserRoleByUserIdIn(List<Long> userIds) {
        if (ObjectUtils.isEmpty(userIds)) {
            return List.of();
        }
        String sql = "SELECT u.ID AS userId, r.NAME AS name " +
                "FROM USER u JOIN USER_ROLE ur ON u.ID = ur.USER_ID " +
                "JOIN ROLE r ON ur.ROLE_ID = r.ID WHERE u.ID IN (:userIds)";
        return baseNativeQuery.findList(sql, UserRoleModel.class,
                Map.of("userIds", userIds));
    }
}
