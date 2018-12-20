package com.colpatria.alozano.exchange.test.glue;

import cucumber.api.java.Before;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;

public class SettingTheStage {

    @Before
    public void recruitTheActors() {
        OnStage.setTheStage(new Cast());
    }
}
