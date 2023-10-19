package com.poc.orderbook.controller;

import com.poc.orderbook.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class OrderBookControllerTest {

    private static final String ORDER_BOOK_ENDPOINT = "/api/order-book";
    private MockMvc mockMvc;

    @InjectMocks
    private OrderBookController orderBookController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(orderBookController).build();
        orderBookController = new OrderBookController(orderService);
    }

    @Test
    void updateOrderBookStatus_shouldReturnStatusOk() throws Exception {
        when(orderService.updateOrderBookStatus(false, 1)).thenReturn(1);

        this.mockMvc.perform(put(ORDER_BOOK_ENDPOINT+"/"+ 1 + "/" + false))
                .andExpectAll(status().isOk(),
                        jsonPath("$").value("1"));
    }

    @Test
    void getCurrentOrderBookState_shouldReturnStatusOk() throws Exception {
        when(orderService.getCurrentOrderBookStatus(1)).thenReturn(Boolean.TRUE);

        this.mockMvc.perform(get(ORDER_BOOK_ENDPOINT + "/" + 1))
                .andExpectAll(status().isOk(),
                        jsonPath("$").value(Boolean.TRUE));
    }


}
