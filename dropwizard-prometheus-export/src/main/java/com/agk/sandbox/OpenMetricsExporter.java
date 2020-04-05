package com.agk.sandbox;

import java.io.StringWriter;
import java.util.Random;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Timed;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.dropwizard.samplebuilder.DefaultSampleBuilder;
import io.prometheus.client.exporter.common.TextFormat;
import lombok.SneakyThrows;

import static java.util.Collections.emptySet;

public class OpenMetricsExporter {

    private static final Random random = new Random(0);

    @SneakyThrows
    public static void main(String[] args) {
        var registry = CollectorRegistry.defaultRegistry;
        var metrics = new MetricRegistry();
        var exports = new DropwizardExports(metrics, new DefaultSampleBuilder());
        exports.register(registry);

        var timer = metrics.timer("my_timer");
        var meter = metrics.meter("meter");
        var hist = metrics.histogram("histogram");
        var counter = metrics.counter("counter");
        var gauge = metrics.gauge("gauge", () -> System::currentTimeMillis);
        var writer = new StringWriter();
        for (var i = 0; i < 10; i++) {
            meter.mark();
            meter.mark(20);
            counter.inc(3);
            hist.update(random.nextInt());
            timer.time(() -> sleep(100));
        }
        // Report in prometheus format
        var samples = registry.filteredMetricFamilySamples(emptySet());
        TextFormat.write004(writer, samples);
        System.out.println(writer.toString());
    }

    /**
     * This one doesn't work yet
     */
    @Timed(name="foobar")
    private void foobar() {

    }

    @SneakyThrows
    private static void sleep(long millis) {
        Thread.sleep(millis);
    }
}
