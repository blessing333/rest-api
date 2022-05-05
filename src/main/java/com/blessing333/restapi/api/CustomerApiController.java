package com.blessing333.restapi.api;

import com.blessing333.restapi.domain.model.customer.Customer;
import com.blessing333.restapi.domain.model.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerApiController {
    private final CustomerRepository repository;

    @GetMapping("/api/v1/customers/{id}")
    Customer loadCustomer(@PathVariable UUID id){
        return repository.findById(id);
    }

}
