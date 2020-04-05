package com.agk.sandbox;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

@Singleton
public class Main {

    /** manual inicialization of CDI container
     *  Alternative to simply running org.jboss.weld.environment.se.StartMain
     * @param args
     */
    public static void main(String[] args) {
        var initializer = SeContainerInitializer.newInstance();
        try (var container = initializer.initialize()) {
            var service = container.select(MyService.class);
            service.forEach(inst -> System.out.println(inst));
        }
    }

    @Inject
    private Ticker ticker;

    public void printHello(
            @Observes ContainerInitialized event,
            @Parameters List<String> parameters
    ) {
        var message = parameters.isEmpty() ? "nothing" : parameters.get(0);
        System.out.println("Hello " + message);
        ticker.tick("tak");
    }

}
