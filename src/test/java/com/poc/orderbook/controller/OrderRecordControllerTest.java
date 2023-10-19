package com.poc.orderbook.controller;

import com.poc.orderbook.entity.OrderRecord;
import com.poc.orderbook.service.ExecutionService;
import com.poc.orderbook.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class OrderRecordControllerTest {

    private static final String ORDER_RECORD_ENDPOINT = "/api/order";
    private MockMvc mockMvc;

    @InjectMocks
    private OrderRecordController orderRecordController;

    @Mock
    private OrderService orderService;

    @Mock
    private ExecutionService executionService;

    private OrderRecord mockOrderRecordBuy, mockOrderRecordSell, mockOrderRecordBuyInput;
    private List<OrderRecord> mockOrderRecordList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(orderRecordController).build();
        orderRecordController = new OrderRecordController(orderService, executionService);
        mockOrderRecordBuyInput = OrderRecord.builder().obid(1).oid(null).quantity(20)
                .entryDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())).oType("buy")
                .price(50.0f).fid(1).isComplete(Boolean.FALSE).build();
        mockOrderRecordBuy = OrderRecord.builder().oid("f87eda0a-e8b7-461a-91c5-4c8593b1fb7a").obid(1).quantity(20)
                .entryDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())).oType("buy")
                .price(50.0f).fid(1).isComplete(Boolean.FALSE).build();
        mockOrderRecordSell = OrderRecord.builder().oid("4a700022-80b0-4536-935f-7157e0c8945e").obid(1).quantity(20)
                .entryDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())).oType("sell")
                .price(50.0f).fid(1).isComplete(Boolean.FALSE).build();
        mockOrderRecordList = List.of(mockOrderRecordBuy, mockOrderRecordSell);

    }

    @Test
    void getAllOrders_shouldReturnListOfAllOrderRecords() throws Exception {
        when(orderService.getAllOrderRecords()).thenReturn(mockOrderRecordList);

        this.mockMvc.perform(get(ORDER_RECORD_ENDPOINT)).
                andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(2)));
    }

    @Test
    void deleteOrderRecord_shouldReturnStatusOk() throws Exception {
        this.mockMvc.perform(delete(ORDER_RECORD_ENDPOINT+"/f87eda0a-e8b7-461a-91c5-4c8593b1fb7a"))
                .andExpect(status().isOk());
    }

}
