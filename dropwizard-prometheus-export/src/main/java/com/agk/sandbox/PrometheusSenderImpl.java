package com.agk.sandbox;

import java.io.IOException;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import org.dhatim.dropwizard.prometheus.PrometheusSender;

public class PrometheusSenderImpl implements PrometheusSender {
    @Override
    public void connect() throws IllegalStateException, IOException {
    }

    @Override
    public void sendGauge(String s, Gauge<?> gauge) throws IOException {
       System.out.println(s + ": " + gauge);
    }

    @Override
    public void sendCounter(String s, Counter counter) throws IOException {
        System.out.println(s + ": " + counter);
    }

    @Override
    public void sendHistogram(String s, Histogram histogram) throws IOException {
        System.out.println(s + ": " + histogram);
    }

    @Override
    public void sendMeter(String s, Meter meter) throws IOException {
        System.out.println(s + ": " + meter);
    }

    @Override
    public void sendTimer(String s, Timer timer) throws IOException {
        System.out.println(s + ": " + timer);
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void close() throws IOException {

    }
}
