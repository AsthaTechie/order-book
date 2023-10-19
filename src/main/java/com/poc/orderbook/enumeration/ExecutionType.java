package com.poc.orderbook.enumeration;

public enum ExecutionType {

    OFFER("offer"), ASK("ask");

    private String value;

    ExecutionType(String value) { this.value = value; }

    public String getValue() {
        return value;
    }
}
