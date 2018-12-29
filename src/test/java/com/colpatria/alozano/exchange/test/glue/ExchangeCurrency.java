package com.colpatria.alozano.exchange.test.glue;

import com.colpatria.alozano.exchange.test.api.Exchange;
import com.colpatria.alozano.exchange.test.glue.transformer.CurrencyConverter;
import com.colpatria.alozano.exchange.test.model.Currency;
import com.colpatria.alozano.exchange.test.model.ExchangeCurrencyRequest;
import com.colpatria.alozano.exchange.test.model.ExchangeErrorResponse;
import com.colpatria.alozano.exchange.test.model.ExchangeResponse;
import com.colpatria.alozano.exchange.test.model.ExchangeValidationErrorResponse;
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
    private ExchangeErrorResponse errorResponse;
    private ExchangeValidationErrorResponse validationErrorResponse;

    @Given("(?:.*) is planning to exchange ([0-9\\.]+) (.*)")
    public void userWantsToExchange(Double value, @Transform(CurrencyConverter.class) Currency currency) {
        this.value = value;
        this.fromCurrency = currency;
    }

    @Given("(?:.*) is planning to exchange ([A-Z]{3})")
    public void userWantsToExchange(@Transform(CurrencyConverter.class) Currency currency) {
        this.value = null;
        this.fromCurrency = currency;
    }

    @When("^s?he asks to exchange it to ([A-Z]{3})$")
    public void userWantsToExchangeTo(@Transform(CurrencyConverter.class) Currency currency) throws Throwable {
        this.toCurrency = currency;

        ExchangeCurrencyRequest exchangeCurrencyRequest = new ExchangeCurrencyRequest();
        exchangeCurrencyRequest.setFromCurrency(this.fromCurrency.idCurrency);
        exchangeCurrencyRequest.setToCurrency(this.toCurrency.idCurrency);
        exchangeCurrencyRequest.setValue(this.value);

        with().contentType(ContentType.JSON).body(exchangeCurrencyRequest).post(Exchange.exchangeCurrency());

        if(value != null){
            response = then().statusCode(200).extract().as(ExchangeResponse.class);
            response.shouldBeValid();
        } else {
            validationErrorResponse = then().statusCode(400).extract().as(ExchangeValidationErrorResponse.class);
            validationErrorResponse.shouldBeValid();
        }

    }

    @When("^s?he asks to exchange it to ([A-Z]{3}) and the exchange value not exists$")
    public void userWantsToExchangeToNotExistingExchange(@Transform(CurrencyConverter.class) Currency currency) throws Throwable {
        this.toCurrency = currency;

        ExchangeCurrencyRequest exchangeCurrencyRequest = new ExchangeCurrencyRequest();
        exchangeCurrencyRequest.setFromCurrency(this.fromCurrency.idCurrency);
        exchangeCurrencyRequest.setToCurrency(this.toCurrency.idCurrency);
        exchangeCurrencyRequest.setValue(this.value);

        with().contentType(ContentType.JSON).body(exchangeCurrencyRequest).post(Exchange.exchangeCurrency());

        errorResponse = then().statusCode(400).extract().as(ExchangeErrorResponse.class);
        errorResponse.shouldBeValid();

    }

    @Then("^s?he should receive ([0-9\\.]+) as the exchanged value$")
    public void heShouldReceiveTheExchangeRate(Double exchangeValue) throws Exception {
        assertThat(response.getValue()).isNull();
        assertThat(response.getValue()).isEqualTo(exchangeValue);
    }


    @Then("^s?he should receive \"(.*)\" as a error message$")
    public void heShouldReceiveTheExchangeRate(String errorMessage) throws Exception {
        assertThat(errorResponse.getMessage()).isNotNull();
        assertThat(errorResponse.getMessage()).isEqualTo(errorMessage);
    }

    @Then("^he should receive a validation error that says \"(.*)\"$")
    public void he_should_receive_a_validation_error_that_says(String errorMessage) throws Exception {
        assertThat(validationErrorResponse.getErrors().length).isEqualTo(1);
        String errorMessageReceived = validationErrorResponse.getErrors()[0];
        assertThat(errorMessageReceived).isEqualTo("The value to convert must not be null");
        assertThat(validationErrorResponse.getErrorMessage()).isNotNull();
        assertThat(validationErrorResponse.getErrorMessage()).isEqualTo("Validation failed. 1 error(s)");

    }

}
