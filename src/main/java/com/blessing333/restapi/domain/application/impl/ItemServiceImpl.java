package com.blessing333.restapi.domain.application.impl;

import com.blessing333.restapi.domain.application.ItemService;
import com.blessing333.restapi.domain.model.item.Item;
import com.blessing333.restapi.domain.model.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public List<Item> loadAllItems() {
        return itemRepository.findAll();
    }
}
