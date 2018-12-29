package com.colpatria.alozano.exchange.test.model;

import lombok.Data;

@Data
public class ExchangeValidationErrorResponse {
    private String[] errors;
    private String errorMessage;

    public void shouldBeValid() {

    }
}
