package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.customer.Customer;
import com.blessing333.restapi.domain.model.customer.CustomerRepository;
import com.blessing333.restapi.infra.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
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
    private static final String FIND_BY_ID_SQL = "SELECT * from customers where id = :id";
    private static final String DELETE_ALL_SQL = "DELETE FROM customers";
    private static final CustomerRowMapper rowMapper = new CustomerRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public UUID save(Customer customer) {
        jdbcTemplate.update(INSERT_SQL,toParamMap(customer));
        return customer.getId();
    }

    @Override
    public Customer findById(UUID id) {
        Map<String, byte[]> param = Collections.singletonMap(ID_COLUMN, UUIDUtils.toBinary(id));
        return jdbcTemplate.queryForObject(FIND_BY_ID_SQL,param,rowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
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
            LocalDateTime createdDate = resultSet.getTimestamp(CREATE_DATE_COLUMN).toLocalDateTime();
            return new Customer(id, name,email,address,createdDate);
        }
    }


}
