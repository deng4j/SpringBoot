package com.example.demo1.pojo;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.time.Instant;

@Measurement(name = "mem")
public class Mem {
    @Column(tag = true)
    String host;
    @Column
    Double used_percent;
    @Column(timestamp = true)
    Instant time;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Double getUsed_percent() {
        return used_percent;
    }

    public void setUsed_percent(Double used_percent) {
        this.used_percent = used_percent;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Mem{" +
                "host='" + host + '\'' +
                ", used_percent=" + used_percent +
                ", time=" + time +
                '}';
    }
}