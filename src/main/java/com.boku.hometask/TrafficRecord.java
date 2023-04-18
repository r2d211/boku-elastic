package com.boku.hometask;

import java.time.ZonedDateTime;

public record TrafficRecord(ZonedDateTime timestamp, String uuid, String qualifier, String status) {

    enum Status {
        FAILED,
        SUCCESS
    }
}
