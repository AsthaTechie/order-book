package com.poc.orderbook.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "order_record")
@NoArgsConstructor
@AllArgsConstructor
public class OrderRecord implements Serializable {

    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Column(name = "oid")
    @JsonProperty("oid")
    private String oid;

    @JsonProperty("obid")
    @Column(name="obid")
    private Integer obid;

    @JsonProperty("quantity")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "entry_date", nullable = false)
    @JsonProperty("entryDate")
    private Date entryDate;

    @Column(name = "otype")
    @JsonProperty("oType")
    private String oType;

    @Column(name = "price")
    @JsonProperty("price")
    private Float price;

    @Column(name = "fid")
    @JsonProperty("fid")
    private Integer fid;

    @Column(name = "is_complete")
    @JsonProperty("isComplete")
    private Boolean isComplete;

    @PrePersist
    private void onCreate() {
        entryDate = new Date();
    }

}
