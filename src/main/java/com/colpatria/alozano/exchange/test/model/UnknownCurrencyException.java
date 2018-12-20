package com.colpatria.alozano.exchange.test.model;

public class UnknownCurrencyException extends RuntimeException  {

    public UnknownCurrencyException(String message) {
        super(message);
    }
}
