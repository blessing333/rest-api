package com.blessing333.restapi.domain.model.order;

import lombok.Getter;

@Getter
public class OrderedItem {
    private final String itemName;
    private final String itemDescription;
    private final long itemPrice;
    private final int itemCount;
    private final long totalItemPrice;

    public OrderedItem(String itemName, String itemDescription, long itemPrice, int itemCount) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.totalItemPrice = itemPrice * itemCount;
    }
}
