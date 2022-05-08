package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.common.UUIDUtils;
import com.blessing333.restapi.domain.model.order.Order;
import com.blessing333.restapi.domain.model.order.OrderRepository;
import com.blessing333.restapi.domain.model.order.OrderStatus;
import com.blessing333.restapi.domain.model.order.exception.OrderDeleteFailException;
import com.blessing333.restapi.domain.model.order.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class JDBCOrderRepository implements OrderRepository {
    private static final String ID_COLUMN = "id";
    private static final String BUYER_COLUMN = "buyer_id";
    private static final String ORDER_STATUS_COLUMN = "order_status";
    private static final String ORDER_DATE_COLUMN = "order_date";

    private static final String INSERT_SQL = "INSERT INTO orders(id, buyer_id, order_status, order_date)VALUES (:id, :buyer_id, :order_status, :order_date)";
    private static final String DELETE_SQL = "DELETE FROM orders WHERE id = :id";
    private static final String DELETE_ALL_SQL = "DELETE from orders";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM orders WHERE id = :id";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderRowMapper rowMapper = new OrderRowMapper();

    @Override
    public UUID save(Order order) {
        jdbcTemplate.update(INSERT_SQL, toParamMap(order));
        return order.getId();
    }

    @Override
    public Order findById(UUID id) {
        Map<String, byte[]> param = Collections.singletonMap(ID_COLUMN, UUIDUtils.toBinary(id));
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, param, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new OrderNotFoundException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        int affectedRowCount = jdbcTemplate.update(DELETE_SQL,Collections.singletonMap(ID_COLUMN,id));
        if(affectedRowCount != 1)
            throw new OrderDeleteFailException();
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL,Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Order order) {
        Map<String, Object> map = new HashMap<>();
        map.put(ID_COLUMN, UUIDUtils.toBinary(order.getId()));
        map.put(BUYER_COLUMN, UUIDUtils.toBinary(order.getBuyerId()));
        map.put(ORDER_STATUS_COLUMN, order.getOrderStatus().toString());
        map.put(ORDER_DATE_COLUMN, order.getOrderDate());
        return map;
    }

    private static class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UUID id = UUIDUtils.toUUID(resultSet.getBytes(ID_COLUMN));
            UUID buyerId = UUIDUtils.toUUID(resultSet.getBytes(BUYER_COLUMN));
            String orderStatusStr = resultSet.getString(ORDER_STATUS_COLUMN);
            OrderStatus orderStatus = OrderStatus.fromString(orderStatusStr);
            LocalDateTime orderDate = resultSet.getTimestamp(ORDER_DATE_COLUMN).toLocalDateTime();
            return new Order(id,buyerId,orderStatus,orderDate);
        }
    }
}
