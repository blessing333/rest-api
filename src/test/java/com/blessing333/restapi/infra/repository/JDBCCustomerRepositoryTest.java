package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class JDBCCustomerRepositoryTest {
    @Autowired
    private JDBCCustomerRepository repository;

    @DisplayName("새로운 고객을 추가할 수 있어야 한다")
    @Test
    void saveTest(){
        UUID id = UUID.randomUUID();
        String name = "test name";
        String email = "test email";
        String address = "경기도 성남시 분당구";
        Customer customer = new Customer(id, name, email, address, LocalDateTime.now());

        assertDoesNotThrow(()->repository.save(customer));
    }
}