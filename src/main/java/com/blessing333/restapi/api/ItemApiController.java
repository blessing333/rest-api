package com.blessing333.restapi.api;

import com.blessing333.restapi.domain.model.order.Item;
import com.blessing333.restapi.domain.model.order.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemApiController {
    private final ItemRepository itemRepository;

    @GetMapping("/api/v1/items")
    List<Item> loadAllItems(){
        log.info("isCalled");
        return itemRepository.findAll();
    }

}
