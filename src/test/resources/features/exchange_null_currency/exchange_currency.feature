Feature: Calculate the value of a currency in another currency
  In order to exchange a currency
  As a person on foot
  I want to know the value of a currency exchanged to another currency

  Scenario: Provide the value of a currency exchanged to another currency
    Given Alex is planning to exchange USD
    When he asks to exchange it to EUR
    Then he should receive a validation error that says "The value to convert must not be null"
