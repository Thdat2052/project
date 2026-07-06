package com.example.notifyservice.repositories.impl;

import com.example.common.service.entities.EmailTemplateEntity;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.notifyservice.repositories.EmailTemplateRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailTemplateRepositoryCustomImpl implements EmailTemplateRepositoryCustom {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<EmailTemplateEntity> findAllByPaging(PagingQueryConditionRequest pageRequest) {
        PageRequest pageable = PageRequest.of(pageRequest.getPageCurrent(),
                pageRequest.getPageSize());

        String countSql = "SELECT COUNT(*) FROM EMAIL_TEMPLATE";
        Long total = jdbcTemplate.queryForObject(countSql, Long.class);

        if (total == null || total == 0) {
            return Page.empty(pageable);
        }

        String querySql = "SELECT ID, NAME, CONTENT, SUBJECT FROM EMAIL_TEMPLATE ORDER BY id LIMIT ? OFFSET ?";
        List<EmailTemplateEntity> results = jdbcTemplate.query(
                querySql,
                new Object[]{pageRequest.getPageSize(), pageable.getOffset()},
                new BeanPropertyRowMapper<>(EmailTemplateEntity.class)
        );

        return new PageImpl<>(results, pageable, total);
    }
}