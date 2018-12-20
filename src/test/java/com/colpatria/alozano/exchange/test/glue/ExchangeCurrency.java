package com.colpatria.alozano.exchange.test.glue;

import com.colpatria.alozano.exchange.test.api.Exchange;
import com.colpatria.alozano.exchange.test.glue.transformer.CurrencyConverter;
import com.colpatria.alozano.exchange.test.model.Currency;
import com.colpatria.alozano.exchange.test.model.ExchangeCurrencyRequest;
import com.colpatria.alozano.exchange.test.model.ExchangeResponse;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;

import static net.serenitybdd.rest.SerenityRest.then;
import static net.serenitybdd.rest.SerenityRest.with;
import static org.assertj.core.api.StrictAssertions.assertThat;

public class ExchangeCurrency {
    private Double value;
    private Currency fromCurrency;
    private Currency toCurrency;
    private ExchangeResponse response;

    @Given("(?:.*) is planning to exchange ([0-9\\.]+) (.*)")
    public void userWantsToExchange(Double value, @Transform(CurrencyConverter.class) Currency currency) {
        this.value = value;
        this.fromCurrency = currency;
    }

    @When("^s?he asks to exchange it to (.*)$")
    public void userWantsToExchangeTo(@Transform(CurrencyConverter.class) Currency currency) throws Throwable {
        this.toCurrency = currency;

        ExchangeCurrencyRequest exchangeCurrencyRequest = new ExchangeCurrencyRequest();
        exchangeCurrencyRequest.setFromCurrency(this.fromCurrency.idCurrency);
        exchangeCurrencyRequest.setToCurrency(this.toCurrency.idCurrency);
        exchangeCurrencyRequest.setValue(this.value);

        with().contentType(ContentType.JSON).body(exchangeCurrencyRequest).post(Exchange.exchangeCurrency());

        response = then().statusCode(200).extract().as(ExchangeResponse.class);
        response.shouldBeValid();

    }

    @Then("^s?he should receive ([0-9\\.]+) as the exchanged value$")
    public void heShouldReceiveTheExchangeRate(Double exchangeValue) throws Exception {
        assertThat(response.getValue()).isNotNull();
        assertThat(response.getValue()).isEqualTo(exchangeValue);
    }
}
