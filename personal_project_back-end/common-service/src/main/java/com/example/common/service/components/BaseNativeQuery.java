package com.example.common.service.components;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BaseNativeQuery {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // ---------  Using JdbcTemplate (positional parameters) ---------

    public <T> Page<T> findPage(String sql, String countSql, Pageable pageable, Class<T> type, Object... params) {
        String paginatedSql = sql + " LIMIT ? OFFSET ?";
        Object[] paginatedParams = appendPaginationParams(params, pageable.getPageSize(), pageable.getOffset());

        List<T> content = jdbcTemplate.query(paginatedSql, new BeanPropertyRowMapper<>(type), paginatedParams);
        Long total = jdbcTemplate.queryForObject(countSql, Long.class, params);

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    public <T> List<T> findList(String sql, Class<T> type, Object... params) {
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(type), params);
    }


    public <T> T findOne(String sql, Class<T> type, Object... params) {
        List<T> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(type), params);
        return results.isEmpty() ? null : results.get(0);
    }

    private Object[] appendPaginationParams(Object[] originalParams, int pageSize, long offset) {
        Object[] newParams = new Object[originalParams.length + 2];
        System.arraycopy(originalParams, 0, newParams, 0, originalParams.length);
        newParams[originalParams.length] = pageSize;
        newParams[originalParams.length + 1] = offset;
        return newParams;
    }

    // --------- NEW: Using NamedParameterJdbcTemplate (named parameters) ---------

    public <T> Page<T> findPage(String sql, String countSql, Pageable pageable, Class<T> type, Map<String, Object> params) {
        String paginatedSql = sql + " LIMIT :limit OFFSET :offset";

        Map<String, Object> paginatedParams = new HashMap<>(params);
        paginatedParams.put("limit", pageable.getPageSize());
        paginatedParams.put("offset", pageable.getOffset());

        List<T> content = namedParameterJdbcTemplate.query(paginatedSql, paginatedParams, new BeanPropertyRowMapper<>(type));
        Long total = namedParameterJdbcTemplate.queryForObject(countSql, paginatedParams, Long.class);

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    public <T> List<T> findList(String sql, Class<T> type, Map<String, Object> params) {
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(type));
    }

    public <T> T findOne(String sql, Class<T> type, Map<String, Object> params) {
        List<T> results = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(type));
        return results.isEmpty() ? null : results.get(0);
    }
}