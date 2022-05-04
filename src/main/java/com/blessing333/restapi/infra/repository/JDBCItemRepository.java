package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.common.UUIDUtils;
import com.blessing333.restapi.domain.model.order.Item;
import com.blessing333.restapi.domain.model.order.ItemNotFoundException;
import com.blessing333.restapi.domain.model.order.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class JDBCItemRepository implements ItemRepository {
    private static final String ID_COLUMN = "id";
    private static final String CATEGORY_ID_COLUMN = "category_id";
    private static final String NAME_COLUMN = "name";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String PRICE_COLUMN = "price";
    private static final String QUANTITY_COLUMN = "quantity";
    private static final String CREATE_DATE_COLUMN = "created_at";
    private static final String INSERT_SQL = "INSERT INTO items(id, category_id, name, description, price, quantity, created_at) VALUES (:id, :category_id, :name, :description, :price, :quantity, :created_at)";
    private static final String FIND_ALL_SQL = "SELECT * FROM items";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM items WHERE id = :id";
    private static final String DELETE_ALL_SQL = "DELETE FROM items";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ItemRowMapper rowMapper = new ItemRowMapper();

    @Override
    public UUID save(Item item) {
        jdbcTemplate.update(INSERT_SQL, toParamMap(item));
        return item.getId();
    }

    @Override
    public List<Item> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, rowMapper);
    }

    @Override
    public Item findById(UUID id) {
        Map<String, byte[]> param = Collections.singletonMap(ID_COLUMN, UUIDUtils.toBinary(id));
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, param, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException(e);
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL,Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Item item) {
        Map<String, Object> map = new HashMap<>();
        map.put(ID_COLUMN, UUIDUtils.toBinary(item.getId()));
        map.put(CATEGORY_ID_COLUMN, UUIDUtils.toBinary(item.getCategoryId()));
        map.put(NAME_COLUMN, item.getName());
        map.put(DESCRIPTION_COLUMN, item.getDescription());
        map.put(PRICE_COLUMN, item.getPrice());
        map.put(QUANTITY_COLUMN, item.getQuantity());
        map.put(CREATE_DATE_COLUMN, item.getCreatedAt());
        return map;
    }

    private static class ItemRowMapper implements RowMapper<Item> {
        @Override
        public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            var id = UUIDUtils.toUUID(resultSet.getBytes(ID_COLUMN));
            var categoryId = UUIDUtils.toUUID(resultSet.getBytes(CATEGORY_ID_COLUMN));
            String name = resultSet.getString(NAME_COLUMN);
            String description = resultSet.getString(DESCRIPTION_COLUMN);
            long price = resultSet.getLong(PRICE_COLUMN);
            int quantity = resultSet.getInt(QUANTITY_COLUMN);
            return Item.createNewItem(id, categoryId, name, description, price, quantity, LocalDateTime.now());
        }
    }
}
