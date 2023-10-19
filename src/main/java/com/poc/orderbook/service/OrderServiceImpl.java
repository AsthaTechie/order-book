package com.poc.orderbook.service;

import com.poc.orderbook.entity.OrderRecord;
import com.poc.orderbook.exceptionhandler.BaseException;
import com.poc.orderbook.repository.OrderBookRepository;
import com.poc.orderbook.repository.OrderRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRecordRepository orderRecordRepo;

    private OrderBookRepository orderBookRepo;

    public OrderServiceImpl(OrderRecordRepository orderRecordRepo, OrderBookRepository orderBookRepo) {
        this.orderRecordRepo = orderRecordRepo;
        this.orderBookRepo = orderBookRepo;
    }

    @Override
    public List<OrderRecord> getAllOrderRecords() {
        return orderRecordRepo.findAll();
    }

    public OrderRecord addOrderRecord(OrderRecord orderRecord) throws BaseException {
        if(!orderBookRepo.isOpenByObid(Integer.parseInt(orderRecord.getObid().toString())))
            throw new BaseException("Order Book with id - " + orderRecord.getObid() + " is closed.");

        return orderRecordRepo.save(orderRecord);
    }

    public Boolean deleteOrderRecordById(String oid) throws BaseException {
        if(!orderRecordRepo.existsById(oid))
            throw new BaseException("Order with id - " + oid + " does not exist.");

        orderRecordRepo.deleteById(oid);
        return Boolean.TRUE;
    }

    public Integer updateOrderBookStatus(Boolean isOpen, int obid) throws BaseException {
        if(!orderBookRepo.existsById(obid))
            throw new BaseException("Order Book with id - " + obid + " does not exist.");

        return orderBookRepo.updateOrderBookStatus(isOpen, obid);
    }

    public Boolean getCurrentOrderBookStatus(int obid) {
        return orderBookRepo.isOpenByObid(obid);
    }


}
