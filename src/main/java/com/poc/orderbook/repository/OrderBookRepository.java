package com.poc.orderbook.repository;

import com.poc.orderbook.entity.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface OrderBookRepository extends JpaRepository<OrderBook, Integer> {

    @Query("select ob.isOpen from OrderBook ob where ob.obid = :obid")
    Boolean isOpenByObid(@Param(value = "obid") int obid);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update OrderBook ob set ob.isOpen = :isOpen where ob.obid = :obid")
    Integer updateOrderBookStatus(@Param(value = "isOpen") Boolean isOpen, @Param(value = "obid") int obid);


}
