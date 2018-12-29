package com.colpatria.alozano.exchange.test.features;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/exchange_null_currency",
        glue = "com.colpatria.alozano.exchange.test")
public class ExchangeNullCurrency {
}
