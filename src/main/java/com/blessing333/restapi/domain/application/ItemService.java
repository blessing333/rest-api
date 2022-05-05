package com.blessing333.restapi.domain.application;

import com.blessing333.restapi.domain.model.order.Item;

import java.util.List;

public interface ItemService {
    List<Item> loadAllItems();
}
