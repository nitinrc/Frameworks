package com.publisher_service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class EmployeeDB {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeDB(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
