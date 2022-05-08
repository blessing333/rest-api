package com.blessing333.restapi.domain.model.item;

import java.util.List;
import java.util.UUID;

public interface ItemRepository {
    UUID save(Item item);

    Item findById(UUID id);

    List<Item> findAll();

    void deleteAll();
}
