package com.example.mainservice.repositories.impls;

import com.example.common.service.components.BaseNativeQuery;
import com.example.common.service.models.CheckoutRequestModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.mainservice.repositories.CheckoutRequestRepositoryNative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CheckoutRequestRepositoryNativeImpl implements CheckoutRequestRepositoryNative {

    @Autowired
    private BaseNativeQuery baseNativeQuery;

    @Override
    public Page<CheckoutRequestModel> findAllWithPaging(PagingQueryConditionRequest pageRequest) {
        String baseQuery = "FROM CHECKOUT_REQUEST cr JOIN ROOM r ON cr.ROOM_ID = r.ID JOIN USER u ON cr.USER_ID = u.ID";
        String whereClause = "";
        Map<String, Object> params = new HashMap<>();

        if (!ObjectUtils.isEmpty(pageRequest.getKeyword())) {
            String keyword = "%" + pageRequest.getKeyword().trim() + "%";
            whereClause = " WHERE cr.ID LIKE :keyword OR cr.REASON LIKE :keyword OR r.NUMBER LIKE :keyword OR u.FULLNAME LIKE :keyword";
            params.put("keyword", keyword);
        }

        String countSql = "SELECT COUNT(*) " + baseQuery + whereClause;
        PageRequest pageable = PageRequest.of(pageRequest.getPageCurrent(), pageRequest.getPageSize());
        String querySql = "SELECT cr.ID, cr.ROOM_ID, cr.USER_ID, cr.STATUS, cr.REQUEST_DATE, cr.REASON, r.NUMBER AS roomName, u.FULLNAME AS fullName " + baseQuery + whereClause;

        return baseNativeQuery.findPage(querySql, countSql, pageable, CheckoutRequestModel.class, params);
    }

}
