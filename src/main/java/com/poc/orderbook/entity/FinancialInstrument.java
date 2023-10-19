package com.poc.orderbook.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "financial_instrument")
@Getter
@Setter
public class FinancialInstrument implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "fid", updatable = false, nullable = false)
    private Integer fid;

    @Column(name = "fin_inst_name", nullable = false)
    private String finInstName;
}
