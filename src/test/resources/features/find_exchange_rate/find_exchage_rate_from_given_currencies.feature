Feature: Find exchange rate from given currencies
  In order to exchange a currency
  As a person on foot
  I want to know the exchange rate with a given currencies

  Scenario: Provide the exchange rate from given currencies
    Given Alex is planning to exchange some USD
    When he asks for the exchange rate to EUR
    Then he should receive 0.88 as the exchange rate
