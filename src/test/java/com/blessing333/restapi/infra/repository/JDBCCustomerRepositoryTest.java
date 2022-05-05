package com.blessing333.restapi.infra.repository;

import com.blessing333.restapi.domain.model.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @DisplayName("ID로 고객을 찾을 수 있어야한다")
    @Test
    void findByIdTest(){
        UUID id = UUID.randomUUID();
        String name = "test name";
        String email = "test email";
        String address = "경기도 성남시 분당구";
        Customer customer = new Customer(id, name, email, address, LocalDateTime.now());

        repository.save(customer);
        Customer found = repository.findById(id);

        assertNotNull(found);
    }
}