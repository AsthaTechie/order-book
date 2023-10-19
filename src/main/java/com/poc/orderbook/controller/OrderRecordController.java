package com.poc.orderbook.controller;

import com.poc.orderbook.entity.OrderRecord;
import com.poc.orderbook.exceptionhandler.BaseException;
import com.poc.orderbook.service.ExecutionService;
import com.poc.orderbook.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderRecordController {

    private OrderService orderService;
    private ExecutionService executionService;

    public OrderRecordController(OrderService orderService, ExecutionService executionService) {
        this.orderService = orderService;
        this.executionService = executionService;
    }

    @GetMapping
    public ResponseEntity<List<OrderRecord>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrderRecords(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> createUpdateOrderRecord(@RequestBody OrderRecord orderRecord) throws BaseException {
        return new ResponseEntity<>(orderService.addOrderRecord(orderRecord), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteOrderRecord(@PathVariable("id") String id) throws BaseException {
        orderService.deleteOrderRecordById(id);
    }


}
