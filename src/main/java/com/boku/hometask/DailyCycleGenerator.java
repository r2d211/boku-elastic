package com.boku.hometask;
import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

public class DailyCycleGenerator implements RowGenerator {

    private Random randy = new SecureRandom();
    int peakhour = 13;
    int peakTrafficPerMinute = 5;

    @Override
    public List<TrafficRecord> generateForMinute(ZonedDateTime minuteInTime) {
        ArrayList<TrafficRecord> list = new ArrayList<>();
        int currentHour = minuteInTime.getHour();
        int mean = peakTrafficPerMinute - Math.abs(peakhour - currentHour)/3;
        int points = Math.abs((int) (randy.nextGaussian() + mean));
        IntStream.range(0, points).forEach(n -> list.add(new TrafficRecord(
                minuteInTime,
                UUID.randomUUID().toString(),
                "daily-traffic",
                randy.nextBoolean() ? TrafficRecord.Status.SUCCESS.name()
                        : TrafficRecord.Status.FAILED.name()
        )));
        return list;
    }
    
}
