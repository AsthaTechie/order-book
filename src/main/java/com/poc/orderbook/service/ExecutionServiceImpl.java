package com.poc.orderbook.service;

import com.poc.orderbook.entity.Execution;
import com.poc.orderbook.entity.OrderRecord;
import com.poc.orderbook.enumeration.ExecutionType;
import com.poc.orderbook.enumeration.OrderType;
import com.poc.orderbook.exceptionhandler.BaseException;
import com.poc.orderbook.repository.ExecutionRepository;
import com.poc.orderbook.repository.OrderBookRepository;
import com.poc.orderbook.repository.OrderRecordRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ExecutionServiceImpl implements ExecutionService{

    private ExecutionRepository executionRepo;

    private OrderBookRepository orderBookRepo;

    private OrderRecordRepository orderRecordRepo;

    public ExecutionServiceImpl(ExecutionRepository executionRepo, OrderBookRepository orderBookRepo,
                                OrderRecordRepository orderRecordRepo) {
        this.executionRepo = executionRepo;
        this.orderBookRepo = orderBookRepo;
        this.orderRecordRepo = orderRecordRepo;
    }

    public List<Execution> executeOrderByFid(int obid, int fid) throws BaseException {
        /*
        * 1. Check if order book is open
        * 2. Fetch orders by fid
        * 3. Fetch executions by fid
        * 4. Compare and complete orders as 'isCompleted=true'
        * 5. Update executions as 'isExecuted=true'.
        * 6. If orderList is completed, set 'isOpen=true' in OrderBook.
        * */
        if(orderBookRepo.isOpenByObid(obid))
            throw new BaseException("Order Book with id - " + obid + " is open.");

        List<OrderRecord> orderRecordList = orderRecordRepo.findByObidAndFidAndIsCompleteOrderByEntryDate(obid, fid, Boolean.FALSE);
        List<Execution> executionList = executionRepo.findByFidAndIsExecuted(fid, Boolean.FALSE);

        Queue<OrderRecord> buyOrderRecordQueue = orderRecordList.stream().filter(order -> order.getOType().toString()
                .equalsIgnoreCase(OrderType.BUY.getValue())).collect(Collectors.toCollection(LinkedList::new));
        Queue<OrderRecord> sellOrderRecordQueue = orderRecordList.stream().filter(order -> order.getOType().toString()
                .equalsIgnoreCase(OrderType.SELL.getValue())).collect(Collectors.toCollection(LinkedList::new));


        Predicate<Execution> isOfferTypeExecution = e -> e.getEType()
                .equalsIgnoreCase(ExecutionType.OFFER.getValue());
        Predicate<Execution> isAskTypeExecution = e -> e.getEType()
                .equalsIgnoreCase(ExecutionType.ASK.getValue());

        Queue<Execution> offerExecutionQueue = executionList.stream().filter(isOfferTypeExecution)
                .collect(Collectors.toCollection(LinkedList::new));
        Queue<Execution> askExecutionQueue = executionList.stream().filter(isAskTypeExecution)
                .collect(Collectors.toCollection(LinkedList::new));

        /* Execute BUY type Orders */
        while(!offerExecutionQueue.isEmpty()) {
            Execution currExecution = offerExecutionQueue.poll();
            int currExeQuant = currExecution.getQuantity();
            if(currExecution.getQuantity() != 0) {
                while(!buyOrderRecordQueue.isEmpty()) {
                    OrderRecord currOrder = buyOrderRecordQueue.poll();
                    if(currOrder.getPrice() >= currExecution.getPrice()) {
                        if(currOrder.getQuantity() <= currExeQuant) {
                            currExecution.setQuantity(currExeQuant - currOrder.getQuantity());
                            if(currExecution.getQuantity() == 0)
                                currExecution.setIsExecuted(Boolean.TRUE);
                            currOrder.setIsComplete(Boolean.TRUE);
                            orderRecordRepo.save(currOrder);
                        } else {
                            buyOrderRecordQueue.offer(currOrder);
                        }
                    }
                }
            } else {
                currExecution.setIsExecuted(Boolean.TRUE);
                executionRepo.save(currExecution);
            }
        }

        /* Execute SELL type Orders */
        while(!askExecutionQueue.isEmpty()) {
            Execution currExecution = askExecutionQueue.poll();
            int currExeQuant = currExecution.getQuantity();
            if(currExecution.getQuantity() != 0) {
                while(!sellOrderRecordQueue.isEmpty()) {
                    OrderRecord currOrder = sellOrderRecordQueue.poll();
                    if(currOrder.getPrice() <= currExecution.getPrice()) {
                        if(currOrder.getQuantity() <= currExeQuant) {
                            currExecution.setQuantity(currExeQuant - currOrder.getQuantity());
                            if(currExecution.getQuantity() == 0)
                                currExecution.setIsExecuted(Boolean.TRUE);
                            currOrder.setIsComplete(Boolean.TRUE);
                            orderRecordRepo.save(currOrder);
                        } else {
                            buyOrderRecordQueue.offer(currOrder);
                        }
                    }
                }
            } else {
                currExecution.setIsExecuted(Boolean.TRUE);
                executionRepo.save(currExecution);
            }
        }

        if(buyOrderRecordQueue.isEmpty() && sellOrderRecordQueue.isEmpty()) {
            orderBookRepo.updateOrderBookStatus(Boolean.TRUE, obid);
        }

        return executionRepo.findByFidAndIsExecuted(fid, Boolean.TRUE);
    }
}
