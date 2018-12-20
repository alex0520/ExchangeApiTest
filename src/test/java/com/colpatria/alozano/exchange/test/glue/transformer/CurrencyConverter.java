package com.colpatria.alozano.exchange.test.glue.transformer;

import com.colpatria.alozano.exchange.test.model.Currency;
import com.colpatria.alozano.exchange.test.model.UnknownCurrencyException;
import cucumber.api.Transformer;

public class CurrencyConverter extends Transformer<Currency> {
    @Override
    public Currency transform(String currencyName) {
        try{
            return Currency.valueOf(currencyName);
        }catch (IllegalArgumentException ex) {
            throw new UnknownCurrencyException(currencyName);
        }
    }
}
