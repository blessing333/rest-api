package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.order.OrderItem;
import com.blessing333.restapi.domain.model.order.OrderItemRepository;
import com.blessing333.restapi.domain.model.order.OrderedItem;
import com.blessing333.restapi.infra.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class JDBCOrderItemRepository implements OrderItemRepository {
    private static final String ID_COLUMN = "id";
    private static final String ORDER_ID_COLUMN = "order_id";
    private static final String ITEM_ID_COLUMN = "item_id";
    private static final String ORDER_PRICE_COLUMN = "order_price";
    private static final String ITEM_COUNT_COLUMN = "item_count";

    private static final String INSERT_SQL = "INSERT INTO order_items(id, order_id, item_id, order_price, item_count) " +
                                             "VALUES (:id, :order_id, :item_id, :order_price, :item_count)";
    private static final String FIND_BY_ORDER_ID_SQL = "SELECT * FROM order_items WHERE order_id = :order_id";
    private static final String JOIN_WITH_ITEM_SQL = "select * from order_items left join items on order_items.item_id = items.id where order_items.order_id = :order_id";
    private static final String DELETE_ALL_SQL = "delete from order_items";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderItemRowMapper rowMapper = new OrderItemRowMapper();
    private final OrderWithItemMapper orderWithItemMapper = new OrderWithItemMapper();

    @Override
    public UUID save(OrderItem orderItem) {
        jdbcTemplate.update(INSERT_SQL, toParamMap(orderItem));
        return orderItem.getId();
    }

    @Override
    public List<OrderItem> findByOrder(UUID orderId) {
        Map<String, byte[]> param = Collections.singletonMap(ORDER_ID_COLUMN, UUIDUtils.toBinary(orderId));
        return jdbcTemplate.query(FIND_BY_ORDER_ID_SQL, param, rowMapper);
    }

    @Override
    public List<OrderedItem> findWithItemByOrderId(UUID orderId) {
        return jdbcTemplate.query(JOIN_WITH_ITEM_SQL,
                Collections.singletonMap(ORDER_ID_COLUMN,UUIDUtils.toBinary(orderId)),
                orderWithItemMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL,Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(OrderItem orderItem) {
        Map<String, Object> map = new HashMap<>();
        map.put(ID_COLUMN, UUIDUtils.toBinary(orderItem.getId()));
        map.put(ORDER_ID_COLUMN, UUIDUtils.toBinary(orderItem.getOrderId()));
        map.put(ITEM_ID_COLUMN, UUIDUtils.toBinary(orderItem.getItemId()));
        map.put(ORDER_PRICE_COLUMN, orderItem.getOrderPrice());
        map.put(ITEM_COUNT_COLUMN, orderItem.getItemCount());
        return map;
    }

    private static class OrderItemRowMapper implements RowMapper<OrderItem> {
        @Override
        public OrderItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UUID id = UUIDUtils.toUUID(resultSet.getBytes(ID_COLUMN));
            UUID orderId = UUIDUtils.toUUID(resultSet.getBytes(ORDER_ID_COLUMN));
            UUID itemId = UUIDUtils.toUUID(resultSet.getBytes(ITEM_ID_COLUMN));
            long orderPrice = resultSet.getLong(ORDER_PRICE_COLUMN);
            int orderCount = resultSet.getInt(ITEM_COUNT_COLUMN);
            return new OrderItem(id,orderId,itemId,orderPrice,orderCount);
        }
    }

    private static class OrderWithItemMapper implements ResultSetExtractor<List<OrderedItem>>{
        @Override
        public List<OrderedItem> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<OrderedItem> list = new ArrayList<>();
            while(rs.next()){
                String itemName = rs.getString("items.name");
                String itemDescription = rs.getString("items.description");
                long itemPrice = rs.getLong("items.price");
                int itemCount = rs.getInt("order_items.item_count");
                OrderedItem row = new OrderedItem(itemName,itemDescription,itemPrice,itemCount);
                list.add(row);
            }
            return list;
        }
    }
}
