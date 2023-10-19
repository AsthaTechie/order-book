package com.poc.orderbook.controller;

import com.poc.orderbook.exceptionhandler.BaseException;
import com.poc.orderbook.service.ExecutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/execution")
@Slf4j
public class ExecutionController {

    private ExecutionService executionService;

    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @PostMapping(value = "/{obid}/{fid}")
    public ResponseEntity<Object> executeOrders(@PathVariable("obid") int obid, @PathVariable("fid") int fid)
            throws BaseException {
        return new ResponseEntity<>(executionService.executeOrderByFid(obid, fid), HttpStatus.OK);
    }
}
