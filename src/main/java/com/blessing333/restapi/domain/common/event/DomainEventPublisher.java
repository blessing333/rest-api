package com.blessing333.restapi.domain.common.event;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
