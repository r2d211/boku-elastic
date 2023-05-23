package com.boku.hometask;

import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

public class WeeklyCycleGenerator implements RowGenerator {

    private Random randy = new SecureRandom();
    int peakhour = 13;
    int randomFactor = 3;

    @Override
    public List<TrafficRecord> generateForMinute(ZonedDateTime minuteInTime) {
        ArrayList<TrafficRecord> list = new ArrayList<>();
        int currentHour = minuteInTime.getHour();
        int mean = 5 - Math.abs(peakhour - currentHour)/3;
        int dayPoints = Math.abs((int) (randy.nextGaussian() + mean));
        int weekDay = minuteInTime.getDayOfWeek().getValue();
        int points = dayPoints * (7- weekDay);
        IntStream.range(0, points).forEach(n -> list.add(new TrafficRecord(
                minuteInTime,
                UUID.randomUUID().toString(),
                "weekly-traffic",
                randy.nextBoolean() ? TrafficRecord.Status.SUCCESS.name()
                        : TrafficRecord.Status.FAILED.name()
        )));
        return list;
    }
    
}
