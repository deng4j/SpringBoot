package com.example.demo1;

import com.example.demo1.pojo.Mem;
import com.influxdb.Cancellable;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SpringBootTest
class Demo1ApplicationTests {

    private InfluxDBClient client;
    String bucket;
    String org;

    @BeforeEach
    void initializeTheClient() {
        System.out.println("初始化客户端");
        String token = "iPbdsWbXOXnY5YLNK0VTYXSfcGfk-AdBnbJAIkPU4w30mjCajvuZ0CJI7yvW05J8TFB6XmeLCkxw1b1FmMj8bQ==";
        bucket = "alming";
        org = "alming";
        client = InfluxDBClientFactory.create("http://192.168.136.10:8086", token.toCharArray());
    }

    /**
     * Use InfluxDB Line Protocol to write data
     */
    @Test
    void writeByProtocol() {
        String data = "mem,host=host1 used_percent=23.43234543";
        try (WriteApi writeApi = client.getWriteApi()) {
            writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
        }
    }

    /**
     * Use a Data Point to write data
     */
    @Test
    void writeByPoint() {
        Point point = Point
                .measurement("mem")
                .addTag("host", "host2")
                .addField("used_percent", 25.43234543)
                .time(Instant.now(), WritePrecision.NS);

        try (WriteApi writeApi = client.getWriteApi()) {
            writeApi.writePoint(bucket, org, point);
        }
    }

    /**
     * Use POJO and corresponding class to write data
     */
    @Test
    void writeByPojo() {
        Mem mem = new Mem();
        mem.setHost("host3");
        mem.setUsed_percent(29.43234543);
        mem.setTime(Instant.now());

        try (WriteApi writeApi = client.getWriteApi()) {
            writeApi.writeMeasurement(bucket, org, WritePrecision.NS, mem);
        }
    }

    @Test
    void query1() {
        String query = String.format("from(bucket: \"%s\") |> range(start: -1h)", bucket);
        List<FluxTable> tables = client.getQueryApi().query(query, org);
        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                System.out.println(record.getField() + "----" + record.getValue());
            }
        }
    }

    @Test
    void query2() {
        String query = String.format("from(bucket: \"%s\") |> range(start: -1h)", bucket);
        List<Mem> memList = client.getQueryApi().query(query, org, Mem.class);
        System.out.println(memList);
    }

    /**
     * 异步查询
     */
    @Test
    void query3() throws InterruptedException {
        String query = String.format("from(bucket: \"%s\") |> range(start: -1h)", bucket);

        BiConsumer<Cancellable, FluxRecord> biConsumer = (cancellable, fluxRecord) -> {
            System.out.println(fluxRecord.getField() + "----" + fluxRecord.getValue());
        };

        Consumer throwable = t -> {
            System.out.println("Error");
        };

        Runnable runnable = () -> {
            System.out.println("complete");
        };

        client.getQueryApi().query(query, org, biConsumer, throwable, runnable);
        TimeUnit.SECONDS.sleep(3);
    }
}
