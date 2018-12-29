Feature: Calculate the value of a currency in another currency
  In order to exchange a currency
  As a person on foot
  I want to know the value of a currency exchanged to another currency rate in the system
  That not have a vexchange

  Scenario: Provide the value of a currency exchanged to another currency
    Given Alex is planning to exchange 15 USD
    When he asks to exchange it to USD and the exchange value not exists
    Then he should receive "There is not exchange rate configured to that currencies" as a error message
