package com.boku.hometask;

import java.time.ZonedDateTime;
import java.util.List;

public interface RowGenerator {
    List<TrafficRecord> generateForMinute(ZonedDateTime minuteInTime);
}
