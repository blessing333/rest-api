package com.blessing333.restapi.api;

import com.blessing333.restapi.api.response.OrderCreateResponse;
import com.blessing333.restapi.domain.application.commands.CreateOrderCommand;
import com.blessing333.restapi.domain.application.commands.OrderItemCommand;
import com.blessing333.restapi.domain.model.order.OrderRepository;
import com.blessing333.restapi.infra.repository.TestDataManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderApiControllerTest {
    @Autowired
    private TestDataManager dataManager;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private OrderRepository orderRepository;
    private final UUID customerId = UUID.randomUUID();
    private final UUID categoryId = UUID.randomUUID();
    private final UUID orderId = UUID.randomUUID();
    private final UUID orderItemId = UUID.randomUUID();
    private final UUID itemId = UUID.randomUUID();

    @BeforeEach
    void initDBData(){
        dataManager.insertDefaultDataToDB(categoryId,customerId,orderId,itemId,orderItemId);
    }

    @AfterEach
    void deleteAllData(){
        dataManager.deleteAllData();
    }

    @DisplayName("주문 아이디를 입력받아 주문 상세정보를 반환한다.")
    @Test
    void inquiryOrderDetail() throws Exception {
        mockMvc.perform(get("/api/v1/orders/" + orderId).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @DisplayName("주문 생성을 요청받으면 주문을 생성하고 생성된 주문 아이디를 반환한다")
    @Test
    void createOrderTest() throws Exception {
        List<OrderItemCommand> orderItemCommands = List.of(new OrderItemCommand(itemId, 5));
        CreateOrderCommand orderCommand = new CreateOrderCommand(customerId,orderItemCommands);
        String s = mapper.writeValueAsString(orderCommand);
        System.out.println(s);
        MvcResult result = mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        OrderCreateResponse response = mapper.readValue(responseString, OrderCreateResponse.class);
        Assertions.assertDoesNotThrow(()->orderRepository.findById(UUID.fromString(response.getId())));
    }

    @DisplayName("주문 아이템에 존재하지 않는 아이템이 포함되어 있으면 OrderCreateFailException 발생")
    @Test
    void

    createOrderShouldFail() throws Exception {
        UUID invalidId = UUID.randomUUID();
        List<OrderItemCommand> orderItemCommands = List.of(new OrderItemCommand(invalidId, 5));
        CreateOrderCommand orderCommand = new CreateOrderCommand(customerId,orderItemCommands);
        String s = mapper.writeValueAsString(orderCommand);
        System.out.println(s);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}