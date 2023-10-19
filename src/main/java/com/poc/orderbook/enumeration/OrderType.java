package com.poc.orderbook.enumeration;

public enum OrderType {

    BUY("buy"), SELL("sell");

    private String value;

    OrderType(String value) { this.value = value; }

    public String getValue() {
        return value;
    }
}
