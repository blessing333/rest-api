package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.category.Category;
import com.blessing333.restapi.domain.model.customer.Customer;
import com.blessing333.restapi.domain.model.order.Item;
import com.blessing333.restapi.domain.model.order.Order;
import com.blessing333.restapi.domain.model.order.OrderItem;
import com.blessing333.restapi.domain.model.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TestDataManager {
    @Autowired
    JDBCCustomerRepository customerRepository;
    @Autowired
    JDBCOrderItemRepository orderItemRepository;
    @Autowired
    JDBCOrderRepository orderRepository;
    @Autowired
    JDBCItemRepository itemRepository;
    @Autowired
    JDBCCategoryRepository categoryRepository;

    public void createDefaultData(UUID categoryId, UUID customerId, UUID orderId, UUID itemId,UUID orderItemId){
        Category category = new Category(categoryId, "categoryName");
        categoryRepository.save(category);

        Item item = Item.createNewItem(itemId, categoryId, "item", "desc", 100000, 3000, LocalDateTime.now());
        itemRepository.save(item);

        Customer customer = new Customer(customerId, "username", "useremai", "useraddress", LocalDateTime.now());
        customerRepository.save(customer);

        Order order = new Order(orderId, customerId, OrderStatus.ACCEPTED, LocalDateTime.now());
        orderRepository.save(order);

        OrderItem orderItem = new OrderItem(orderItemId, orderId, itemId, 50000, 100);
        orderItemRepository.save(orderItem);
    }

    public void insertNewItemToDB(UUID itemId,UUID categoryId){
        Item item = Item.createNewItem(itemId,categoryId,"test","test",10000,500,LocalDateTime.now());
        itemRepository.save(item);
    }

    public void deleteAllData(){
        orderRepository.deleteAll();
        orderItemRepository.deleteAll();
        itemRepository.deleteAll();
        customerRepository.deleteAll();
        categoryRepository.deleteAll();
    }

}
