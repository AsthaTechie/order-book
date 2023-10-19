package com.poc.orderbook.controller;

import com.poc.orderbook.exceptionhandler.BaseException;
import com.poc.orderbook.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-book")
@Slf4j
public class OrderBookController {

    private OrderService orderService;

    public OrderBookController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping(value = "/{obid}/{updateStatus}")
    public ResponseEntity<Integer> updateOrderBookStatus(@PathVariable("obid") int obid,
                                                         @PathVariable("updateStatus") Boolean updateStatus)
            throws BaseException {
        return new ResponseEntity<>(orderService.updateOrderBookStatus(updateStatus, obid), HttpStatus.OK);
    }

    @GetMapping(value = "/{obid}")
    public ResponseEntity<Boolean> getCurrentOrderBookState(@PathVariable("obid") int obid) {
        return new ResponseEntity<>(orderService.getCurrentOrderBookStatus(obid), HttpStatus.OK);
    }
}
