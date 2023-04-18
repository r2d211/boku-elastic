package com.boku.hometask;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

class SyntheticTrafficTest {

    @Test
    public void generates_something() {
        SyntheticTraffic traffic = new SyntheticTraffic();
        List<TrafficRecord> list = traffic.generateForMinute(ZonedDateTime.now());
        assertThat(list.size()).isEqualTo(1);
    }
}
