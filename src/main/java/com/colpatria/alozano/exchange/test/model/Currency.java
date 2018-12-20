package com.colpatria.alozano.exchange.test.model;

public enum Currency {
    USD(1),
    EUR(2);

    public final Integer idCurrency;

    Currency(Integer idCurrency){
        this.idCurrency = idCurrency;
    }
}
