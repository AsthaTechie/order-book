package com.poc.orderbook.service;

import com.poc.orderbook.entity.OrderRecord;
import com.poc.orderbook.exceptionhandler.BaseException;

import java.util.List;

public interface OrderService {

    List<OrderRecord> getAllOrderRecords();

    OrderRecord addOrderRecord(OrderRecord orderRecord) throws BaseException;

    Boolean deleteOrderRecordById(String oid) throws BaseException;

    Integer updateOrderBookStatus(Boolean isOpen, int obid) throws BaseException;

    Boolean getCurrentOrderBookStatus(int obid);
}
