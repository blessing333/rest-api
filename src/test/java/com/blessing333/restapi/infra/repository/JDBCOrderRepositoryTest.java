package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.customer.Customer;
import com.blessing333.restapi.domain.model.customer.CustomerRepository;
import com.blessing333.restapi.domain.model.order.Order;
import com.blessing333.restapi.domain.model.order.OrderDeleteFailException;
import com.blessing333.restapi.domain.model.order.OrderStatus;
import com.blessing333.restapi.domain.model.order.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RequiredArgsConstructor
class JDBCOrderRepositoryTest {
    private static final UUID customerId = UUID.randomUUID();
    @Autowired
    private JDBCOrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void initCustomer() {
        String name = "user";
        String email = "test@naver.com";
        String address = "경기도 성남시 분당구";
        Customer customer = new Customer(customerId, name, email, address, LocalDateTime.now());
        customerRepository.save(customer);
    }

    @AfterEach
    void deleteAllData(){
        orderRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @DisplayName("주문정보를 저장할 수 있어야한다")
    @Test
    void saveTest() {
        UUID orderId = UUID.randomUUID();
        OrderStatus status = OrderStatus.ACCEPTED;
        Order order = new Order(orderId, customerId, status, LocalDateTime.now());

        orderRepository.save(order);

        Order found = orderRepository.findById(orderId);
        assertThat(found.getId()).isEqualTo(orderId);
    }

    @DisplayName("주분정보를 조회할 수 있어야한다")
    @Test
    void findOrderTest() {
        UUID orderId = UUID.randomUUID();
        OrderStatus status = OrderStatus.ACCEPTED;
        Order order = new Order(orderId, customerId, status, LocalDateTime.now());

        orderRepository.save(order);

        Order found = orderRepository.findById(orderId);
        assertThat(found.getId()).isEqualTo(orderId);
        assertThat(found.getBuyerId()).isEqualTo(customerId);
        assertThat(found.getOrderStatus()).isEqualTo(found.getOrderStatus());
    }

    @DisplayName("주분정보를 찾을 수 없는 경우, OrderNotFoundException 발생")
    @Test
    void findOrderTestShouldFail() {
        UUID invalidId = UUID.randomUUID();

        assertThrows(OrderNotFoundException.class,()->orderRepository.findById(invalidId));
    }

    @DisplayName("주문 정보를 삭제할 수 있어야 한다")
    @Test
    void deleteTest(){
        UUID orderId = UUID.randomUUID();
        OrderStatus status = OrderStatus.ACCEPTED;
        Order order = new Order(orderId, customerId, status, LocalDateTime.now());

        orderRepository.save(order);
        orderRepository.delete(orderId);

        assertThrows(OrderNotFoundException.class,()->orderRepository.findById(orderId));
    }

    @DisplayName("주문 정보삭제에 실패할 경우, OrderDeleteFailException 발생시킨다")
    @Test
    void deleteTestShouldFail(){
        UUID invalidId = UUID.randomUUID();

        assertThrows(OrderDeleteFailException.class,()->orderRepository.delete(invalidId));
    }
}