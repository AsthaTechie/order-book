package com.poc.orderbook.service;

import com.poc.orderbook.entity.OrderRecord;
import com.poc.orderbook.exceptionhandler.BaseException;
import com.poc.orderbook.repository.OrderBookRepository;
import com.poc.orderbook.repository.OrderRecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRecordRepository orderRecordRepo;

    @Mock
    private OrderBookRepository orderBookRepo;

    private OrderRecord mockOrderRecordBuy, mockOrderRecordSell, mockOrderRecordDiffFid, mockOrderRecordBuyInput;
    private List<OrderRecord> mockAllOrderRecordList, mockOrderRecordWithSameFidList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(orderRecordRepo, orderBookRepo);

        mockOrderRecordBuy = OrderRecord.builder().oid("f87eda0a-e8b7-461a-91c5-4c8593b1fb7a").obid(1).quantity(20)
                .entryDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())).oType("buy")
                .price(50.0f).fid(1).isComplete(Boolean.FALSE).build();
        mockOrderRecordSell = OrderRecord.builder().oid("4a700022-80b0-4536-935f-7157e0c8945e").obid(1).quantity(20)
                .entryDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())).oType("sell")
                .price(50.0f).fid(1).isComplete(Boolean.FALSE).build();
        mockOrderRecordDiffFid = OrderRecord.builder().oid("5b700022-80b0-4536-935f-7157e0c8946a").obid(1).quantity(30)
                .entryDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())).oType("sell")
                .price(50.0f).fid(2).isComplete(Boolean.FALSE).build();
        mockOrderRecordBuyInput = OrderRecord.builder().obid(1).quantity(20)
                .entryDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())).oType("buy")
                .price(50.0f).fid(1).isComplete(Boolean.FALSE).build();
        mockAllOrderRecordList = List.of(mockOrderRecordBuy, mockOrderRecordSell, mockOrderRecordDiffFid);
    }

    @Test
    void getAllOrderRecords_shouldReturnListOfAllOrderWithStatusOk() {
        when(orderRecordRepo.findAll()).thenReturn(mockAllOrderRecordList);

        Assertions.assertEquals(mockAllOrderRecordList, orderService.getAllOrderRecords());
    }

    @Test
    void addOrderRecord_shouldReturnCreatedOrderWithStatusCreated() throws BaseException {
        when(orderBookRepo.isOpenByObid(1)).thenReturn(Boolean.TRUE);
        when(orderRecordRepo.save(mockOrderRecordBuyInput)).thenReturn(mockOrderRecordBuy);

        Assertions.assertEquals(mockOrderRecordBuy, orderService.addOrderRecord(mockOrderRecordBuyInput));
    }

    @Test
    void addOrderRecord_shouldThrowBaseException() throws BaseException {
        when(orderBookRepo.isOpenByObid(1)).thenReturn(Boolean.FALSE);

        Assertions.assertThrows(BaseException.class, () -> orderService.addOrderRecord(mockOrderRecordBuyInput));
    }


    @Test
    void deleteOrderRecordById_shouldReturnVoid() throws BaseException {
        when(orderRecordRepo.existsById("f87eda0a-e8b7-461a-91c5-4c8593b1fb7a")).thenReturn(Boolean.TRUE);

        Assertions.assertEquals(
                Boolean.TRUE,
                orderService.deleteOrderRecordById("f87eda0a-e8b7-461a-91c5-4c8593b1fb7a"));
    }

    @Test
    void deleteOrderRecordById_shouldThrowBaseException() throws BaseException {
        when(orderRecordRepo.existsById("abcdef")).thenReturn(Boolean.FALSE);

        Assertions.assertThrows(BaseException.class, () ->
                orderService.deleteOrderRecordById("abcdef"));
    }

    @Test
    void updateOrderBookStatus_shouldReturnUpdatedRecordCount() throws BaseException {
        when(orderBookRepo.existsById(1)).thenReturn(Boolean.TRUE);
        when(orderService.updateOrderBookStatus(Boolean.FALSE, 1)).thenReturn(1);

        Assertions.assertEquals(
                orderService.updateOrderBookStatus(Boolean.FALSE, 1)
                , 1);
    }

    @Test
    void updateOrderBookStatus_shouldThrowBaseException() throws BaseException {
        when(orderBookRepo.existsById(2)).thenReturn(Boolean.FALSE);

        Assertions.assertThrows(BaseException.class, () ->
                orderService.updateOrderBookStatus(Boolean.FALSE, 2));
    }


    @Test
    void getCurrentOrderBookStatus_shouldReturnOpenStatusAsTrue() {
        when(orderBookRepo.isOpenByObid(1)).thenReturn(Boolean.TRUE);

        Assertions.assertEquals(
                orderService.getCurrentOrderBookStatus(1),
                Boolean.TRUE
        );
    }
}
