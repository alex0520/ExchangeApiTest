package com.colpatria.alozano.exchange.test.model;


import lombok.Data;

@Data
public class ExchangeCurrencyRequest {

    private Integer fromCurrency;

    private Integer toCurrency;

    private Double value;
}
