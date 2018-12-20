package com.colpatria.alozano.exchange.test.glue;

import com.colpatria.alozano.exchange.test.api.Exchange;
import com.colpatria.alozano.exchange.test.glue.transformer.CurrencyConverter;
import com.colpatria.alozano.exchange.test.model.Currency;
import com.colpatria.alozano.exchange.test.model.ExchangeResponse;
import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static net.serenitybdd.rest.SerenityRest.then;
import static net.serenitybdd.rest.SerenityRest.with;
import static org.assertj.core.api.Assertions.assertThat;

public class FindExchangeCurrency {

    private Currency fromCurrency;
    private Currency toCurrency;
    private ExchangeResponse response;

    @Given("(?:.*) is planning to exchange some (.*)")
    public void userWantsToExchange(@Transform(CurrencyConverter.class) Currency currency) {
        this.fromCurrency = currency;
    }

    @When("^s?he asks for the exchange rate to (.*)$")
    public void userWantsToExchangeTo(@Transform(CurrencyConverter.class) Currency currency) throws Throwable {
        this.toCurrency = currency;
        with().get(Exchange.findExchangeRate(fromCurrency.idCurrency, toCurrency.idCurrency));

        response = then().statusCode(200).extract().as(ExchangeResponse.class);
        response.shouldBeValid();

    }

    @Then("^he should receive ([0-9\\.]+) as the exchange rate$")
    public void heShouldReceiveTheExchangeRate(Double exchangeRate) throws Exception {
        assertThat(response.getValue()).isNotNull();
        assertThat(response.getValue()).isEqualTo(exchangeRate);
    }

}
