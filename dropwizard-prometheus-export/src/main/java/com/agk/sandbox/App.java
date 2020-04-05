package com.agk.sandbox;

import java.io.File;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import lombok.SneakyThrows;
import org.dhatim.dropwizard.prometheus.PrometheusReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Logger LOGGER = LoggerFactory.getLogger(App.class);

    @SneakyThrows
    public static void main( String[] args )
    {
        // Inside an initialisation function.
        var metrics = new MetricRegistry();
        CollectorRegistry.defaultRegistry.register(new DropwizardExports(metrics));
        var meter = metrics.meter("my-meter");
        ConsoleReporter
                .forRegistry(metrics)
                .convertRatesTo(SECONDS)
                .convertDurationsTo(MILLISECONDS)
                .build()
                .start(1, SECONDS);
        CsvReporter
                .forRegistry(metrics)
                .build(new File("/tmp/metrics"))
                .start(1, SECONDS);
        Slf4jReporter
                .forRegistry(metrics)
                .outputTo(LOGGER)
                .build()
                .start(1, SECONDS);
        PrometheusReporter                 // nothing prometheus about it
                .forRegistry(metrics)
                .build(new PrometheusSenderImpl())
                .start(1, SECONDS);
        while (true) {
            meter.mark();
            System.out.println("Hello World!");
            Thread.sleep(1000);
        }
    }
}
