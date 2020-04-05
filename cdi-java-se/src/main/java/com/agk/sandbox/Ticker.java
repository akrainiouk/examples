package com.agk.sandbox;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;

@ApplicationScoped
//@RequestScoped
public class Ticker {

    public void tick(String tickId) {
        System.out.println("Tick[" + tickId + "]");
    }

}
