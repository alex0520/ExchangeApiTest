package com.colpatria.alozano.exchange.test.api;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;

public class Exchange {
    private static final String BASE_URL = "http://localhost:8080/api";

    private static final String FIND_EXCHANGE_RATE_PARAMS = "fromCurrency=%s&toCurrency=%s";
    private static final String FIND_EXCHANGE_RATE = BASE_URL + "/exchange?" + FIND_EXCHANGE_RATE_PARAMS;

    private static final String EXCHANGE_CURRENCY = BASE_URL + "/exchange";

    public static URL findExchangeRate(Integer fromCurrency, Integer toCurrency) throws MalformedURLException {

        return new URL(format(FIND_EXCHANGE_RATE, fromCurrency, toCurrency));

    }

    public static URL exchangeCurrency() throws MalformedURLException {

        return new URL(EXCHANGE_CURRENCY);

    }
}
