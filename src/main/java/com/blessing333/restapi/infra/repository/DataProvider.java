package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.category.Category;
import com.blessing333.restapi.domain.model.customer.Customer;
import com.blessing333.restapi.domain.model.order.Item;
import com.blessing333.restapi.domain.model.order.Order;
import com.blessing333.restapi.domain.model.order.OrderItem;
import com.blessing333.restapi.domain.model.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataProvider {
    private final JDBCCustomerRepository customerRepository;
    private final JDBCOrderItemRepository orderItemRepository;
    private final JDBCOrderRepository orderRepository;
    private final JDBCItemRepository itemRepository;
    private final JDBCCategoryRepository categoryRepository;

    @PostConstruct
    public void createDefaultData(){
        UUID categoryId  = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        UUID orderItemId = UUID.randomUUID();

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
}
