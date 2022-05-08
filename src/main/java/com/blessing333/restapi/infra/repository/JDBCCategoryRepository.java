package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.category.Category;
import com.blessing333.restapi.domain.model.category.CategoryNotFoundException;
import com.blessing333.restapi.domain.model.category.CategoryRepository;
import com.blessing333.restapi.infra.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JDBCCategoryRepository implements CategoryRepository {
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String INSERT_SQL = "INSERT INTO categories(id, name) VALUES (:id, :name)";
    private static final String UPDATE_SQL = "UPDATE categories SET name =:name WHERE id =:id";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM categories WHERE id = :id";
    private static final String DELETE_SQL = "DELETE FROM categories WHERE id = :id";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CategoryRowMapper rowMapper = new CategoryRowMapper();

    @Override
    public UUID save(Category category) {
        jdbcTemplate.update(INSERT_SQL,toParamMap(category));
        return  category.getId();
    }

    @Override
    public Category findById(UUID id) {
        Map<String, byte[]> param = Collections.singletonMap(ID_COLUMN, UUIDUtils.toBinary(id));
        try{
            return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, param, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new CategoryNotFoundException(e);
        }
    }

    @Override
    public void update(Category category) {
        jdbcTemplate.update(UPDATE_SQL, toParamMap(category));
    }

    @Override
    public void delete(UUID id) {
        Map<String, byte[]> param = Collections.singletonMap(ID_COLUMN, UUIDUtils.toBinary(id));
        jdbcTemplate.update(DELETE_SQL, param);
    }

    @Override
    public void deleteAll(){
        jdbcTemplate.update("DELETE FROM categories",Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Category category) {
        Map<String, Object> map = new HashMap<>();
        map.put(ID_COLUMN, UUIDUtils.toBinary(category.getId()));
        map.put(NAME_COLUMN, category.getName());
        return map;
    }

    private static class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            var id = UUIDUtils.toUUID(resultSet.getBytes(ID_COLUMN));
            String name = resultSet.getString(NAME_COLUMN);
            return new Category(id, name);
        }
    }
}
