package com.example.notifyservice.repositories.impl;

import com.example.common.service.entities.EmailConfigEntity;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.notifyservice.repositories.EmailConfigRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailConfigRepositoryCustomImpl implements EmailConfigRepositoryCustom {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<EmailConfigEntity> findAllByPaging(PagingQueryConditionRequest pageRequest) {
        int pageNumber = Math.max(pageRequest.getPageCurrent(), 0);
        int pageSize = Math.max(pageRequest.getPageSize(), 10);
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        String querySql = "SELECT ID, HOST, PORT, EMAIL FROM EMAIL_CONFIG ORDER BY ID LIMIT ? OFFSET ?";

        List<EmailConfigEntity> results = jdbcTemplate.query(
                querySql,
                new Object[]{pageSize, pageable.getOffset()},
                new BeanPropertyRowMapper<>(EmailConfigEntity.class)
        );

        if (results.isEmpty()) {
            return Page.empty(pageable);
        }

        long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM EMAIL_CONFIG", Long.class);

        return new PageImpl<>(results, pageable, total);
    }
}
