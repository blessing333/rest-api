package com.blessing333.restapi.domain.model.category;

import java.util.UUID;

public interface CategoryRepository {
    UUID save(Category category);

    Category findById(UUID id);

    void update(Category category);

    void delete(UUID id);

    void deleteAll();
}
