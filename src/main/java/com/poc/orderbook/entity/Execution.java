package com.poc.orderbook.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "execution")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Execution {

    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Column(name = "eid", updatable = false, nullable = false)
    private String eid;

    private Integer fid;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "etype")
    private String eType;

    @Column(name = "price")
    private float price;

    @Column(name = "is_executed")
    private Boolean isExecuted;

}
