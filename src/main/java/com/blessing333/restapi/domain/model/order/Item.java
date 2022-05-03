package com.blessing333.restapi.domain.model.order;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Item {
    private final UUID id;
    private final UUID categoryId;
    private final LocalDateTime createdAt;
    private String name;
    private String description;
    private long price;
    private int quantity;
    private LocalDateTime updateAt;

    public static Item createNewItem(UUID id, UUID categoryId, String name, String description, long price, int quantity, LocalDateTime createdAt) {
        Item item = new Item(id,categoryId,createdAt);
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setQuantity(quantity);
        return item;
    }
}
