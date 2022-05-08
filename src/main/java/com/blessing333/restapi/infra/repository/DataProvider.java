package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.category.Category;
import com.blessing333.restapi.domain.model.customer.Customer;
import com.blessing333.restapi.domain.model.item.Item;
import com.blessing333.restapi.domain.model.order.Order;
import com.blessing333.restapi.domain.model.order.OrderItem;
import com.blessing333.restapi.domain.model.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataProvider {
    private final JDBCCustomerRepository customerRepository;
    private final JDBCOrderItemRepository orderItemRepository;
    private final JDBCOrderRepository orderRepository;
    private final JDBCItemRepository itemRepository;
    private final JDBCCategoryRepository categoryRepository;

    @PostConstruct
    public void createDefaultData(){
        deleteAllData();
        UUID categoryId  = UUID.randomUUID();
        UUID customerId = UUID.fromString("5493b4af-b865-4ce0-a866-c08bc7acc48b");
        UUID orderId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        UUID orderItemId = UUID.randomUUID();

        Category category = new Category(categoryId, "커피 원두");
        categoryRepository.save(category);

        Item item = Item.createNewItem(itemId, categoryId, "블루마운틴 원두", "자메이카산 블루마운틴 원두", 30000, 3000, LocalDateTime.now());
        itemRepository.save(item);
        itemRepository.save(Item.createNewItem(UUID.randomUUID(), categoryId, "예가체프 원두", "에티오피아산 원두", 18000, 3000, LocalDateTime.now()));
        itemRepository.save(Item.createNewItem(UUID.randomUUID(), categoryId, "케냐AA 원두", "케냐산 원두", 10000, 3000, LocalDateTime.now()));

        Customer customer = new Customer(customerId, "이민재", "lee@naver.com", "경기도 성남시 분당구", LocalDateTime.now());
        customerRepository.save(customer);

        Order order = new Order(orderId, customerId, OrderStatus.ACCEPTED, LocalDateTime.now());
        orderRepository.save(order);

        OrderItem orderItem = new OrderItem(orderItemId, orderId, itemId, 3000L * 100, 100);
        orderItemRepository.save(orderItem);
    }

    void deleteAllData(){
        orderRepository.deleteAll();
        orderItemRepository.deleteAll();
        itemRepository.deleteAll();
        customerRepository.deleteAll();
        categoryRepository.deleteAll();
    }
}
