package com.boku.hometask;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataGenerator {

    private static final long SLEEP_DURATION_IN_MS = 1000;

    public boolean stop = false;

    private final ZonedDateTime startDate = ZonedDateTime.parse("2021-12-03T00:00:00Z");

    private final List<RowGenerator> rowGenerators = List.of(new DailyCycleGenerator(), new WeeklyCycleGenerator());
    private final ElasticClient elasticClient = new ElasticClient();

    public static void main(String[] args) throws Exception {
        new DataGenerator().run();
    }

    private void run() throws Exception {
        System.out.println("Starting data generation");
        generate(startDate);
        System.out.println("Done with data generation");
    }

    private void generate(ZonedDateTime startDate) throws Exception {
        ZonedDateTime currentDate = startDate;
        List<TrafficRecord> buffer = new ArrayList<>();
        while (!shouldStop()) {
            final ZonedDateTime dateCopy = currentDate;
            while (currentDateIsInFuture(dateCopy)) {
                Thread.sleep(SLEEP_DURATION_IN_MS);
                flushBufferIfExceedsLimit(buffer, 0, currentDate);
            }
            List<TrafficRecord> rowsForMinute = rowGenerators.stream()
                    .map(r -> r.generateForMinute(dateCopy))
                    .flatMap(Collection::stream)
                    .toList();
            buffer.addAll(rowsForMinute);
            flushBufferIfExceedsLimit(buffer, 1000, currentDate);
            currentDate = currentDate.plusMinutes(1);
        }
    }

    private boolean shouldStop() {
        return stop;
    }

    private boolean currentDateIsInFuture(ZonedDateTime currentDate) {
        return currentDate.isAfter(ZonedDateTime.now());
    }

    private void flushBufferIfExceedsLimit(List<TrafficRecord> buffer, int limit, ZonedDateTime currentDate)
            throws Exception
    {
        if (buffer.size() > limit) {
            elasticClient.sendToElastic(buffer);
            System.out.println("Generating " + currentDate.format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
                    + " rows: " + buffer.size());
            buffer.clear();
        }
    }
}
