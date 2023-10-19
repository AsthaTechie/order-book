package com.poc.orderbook.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_book")
public class OrderBook {

    @Id
    @GeneratedValue
    @Column(name = "obid", updatable = false, nullable = false)
    private int obid;

    @Column(name = "order_book_name")
    private String orderBookName;

    @Column(name = "is_open")
    private boolean isOpen;
}
