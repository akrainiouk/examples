package com.agk.sandbox;

import java.io.StringWriter;
import java.util.Collections;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import io.prometheus.client.exporter.common.TextFormat;
import lombok.SneakyThrows;

public class NonStaticRegistryReportsToStdOut {

  @SneakyThrows
  public static void main(String[] args) {
    var registry = new CollectorRegistry(true);
    
    var counter = Counter.build().name("counter").help("counter help").register(registry);
    var gauge = Gauge.build().name("gauge").help("gauge help").register(registry);
    var summary = Summary.build().name("summary").help("summary help").register(registry);
    var histogram = Histogram.build().name("histogram").help("histogram help").register(registry);
    counter.inc(123);
    gauge.set(432);
    try (var ignore = summary.startTimer()) {
      Thread.sleep(100);
    }
    try (var ignore = histogram.startTimer()) {
      Thread.sleep(100);
    }
    reportToConsole(registry);
  }

  @SneakyThrows
  private static void reportToConsole(CollectorRegistry registry) {
    var buffer = new StringWriter();
    TextFormat.write004(buffer, registry.filteredMetricFamilySamples(Collections.emptySet()));
    System.out.println(buffer);
  }

}
