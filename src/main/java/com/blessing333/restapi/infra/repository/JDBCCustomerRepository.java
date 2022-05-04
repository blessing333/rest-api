package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.common.UUIDUtils;
import com.blessing333.restapi.domain.model.customer.Customer;
import com.blessing333.restapi.domain.model.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JDBCCustomerRepository implements CustomerRepository {
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String EMAIL_COLUMN = "email";
    private static final String ADDRESS_COLUMN = "address";
    private static final String CREATE_DATE_COLUMN = "create_at";
    private static final String INSERT_SQL = "INSERT INTO customers(id, name, email, address,create_at) VALUES (:id, :name, :email, :address, :create_at)";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CustomerRowMapper rowMapper = new CustomerRowMapper();

    @Override
    public UUID save(Customer customer) {
        jdbcTemplate.update(INSERT_SQL,toParamMap(customer));
        return customer.getId();
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> map = new HashMap<>();
        map.put(ID_COLUMN, UUIDUtils.toBinary(customer.getId()));
        map.put(NAME_COLUMN, customer.getName());
        map.put(EMAIL_COLUMN, customer.getEmail());
        map.put(ADDRESS_COLUMN, customer.getAddress());
        map.put(CREATE_DATE_COLUMN, customer.getCreateAt());
        return map;
    }

    private static class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            var id = UUIDUtils.toUUID(resultSet.getBytes(ID_COLUMN));
            String name = resultSet.getString(NAME_COLUMN);
            String email = resultSet.getString(EMAIL_COLUMN);
            String address = resultSet.getString(ADDRESS_COLUMN);
            LocalDateTime createAt = resultSet.getTimestamp(CREATE_DATE_COLUMN).toLocalDateTime();
            return new Customer(id, name, email, address, createAt);
        }
    }
}
