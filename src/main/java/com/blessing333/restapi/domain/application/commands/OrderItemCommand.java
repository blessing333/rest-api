package com.blessing333.restapi.domain.application.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemCommand {
    @NotNull
    private UUID itemId;
    @Min(1)
    private int itemCount;
}
