package com.blessing333.restapi.domain.application.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Setter
public class CreateOrderCommand {
    @NotNull
    private UUID buyerId;
    @Size(min = 1)
    private List<OrderItemCommand> orderItems;
}
