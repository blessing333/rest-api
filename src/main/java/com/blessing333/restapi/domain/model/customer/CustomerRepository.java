package com.blessing333.restapi.domain.model.customer;

import java.util.UUID;

public interface CustomerRepository {
    UUID save(Customer customer);
}
