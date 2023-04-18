package com.boku.hometask;

import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SyntheticTraffic implements RowGenerator   {

    private Random randy = new SecureRandom();

    @Override
    public List<TrafficRecord> generateForMinute(ZonedDateTime momentInTime) {
        ArrayList<TrafficRecord> list = new ArrayList<>();
        list.add(new TrafficRecord(
                momentInTime,
                UUID.randomUUID().toString(),
                "synthetic-traffic",
                randy.nextBoolean() ? TrafficRecord.Status.SUCCESS.name()
                        : TrafficRecord.Status.FAILED.name()
        ));
        return list;
    }
}
