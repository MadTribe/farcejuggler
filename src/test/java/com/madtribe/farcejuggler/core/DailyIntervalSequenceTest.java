package com.madtribe.farcejuggler.core;

import com.madtribe.farcejuggler.intervals.DailyIntervalSequence;
import com.madtribe.farcejuggler.intervals.Interval;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.of;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class DailyIntervalSequenceTest {
    private DailyIntervalSequence instance;

    @Before
    public void setup(){

    }

    @Test
    public void nextInterval_fromMidnight_returnsToday() {
        instance = new DailyIntervalSequence(9, 0,17, 0);
        LocalDateTime now = of(2018,11,15, 0, 1);

        Optional<Interval> interval = instance.nextInterval(now);

        assertThat( interval.get().getStart() , equalTo(of(2018,11,15, 9, 0)));
        assertThat( interval.get().getEnd() , equalTo(of(2018,11,15, 17, 0)));
    }

    @Test
    public void nextInterval_fromMidday_returnsTomorrow() {
        instance = new DailyIntervalSequence(9, 0,17, 0);
        LocalDateTime now = of(2018,11,15, 12, 1);

        Optional<Interval> interval = instance.nextInterval(now);

        assertThat( interval.get().getStart() , equalTo(of(2018,11,16, 9, 0)));
        assertThat( interval.get().getEnd() , equalTo(of(2018,11,16, 17, 0)));
    }

    @Test
    public void nextInterval_fromEvening_returnsTomorrow() {
        instance = new DailyIntervalSequence(9, 0,17, 0);
        LocalDateTime now = of(2018,11,15, 18, 1);

        Optional<Interval> interval = instance.nextInterval(now);

        assertThat( interval.get().getStart() , equalTo(of(2018,11,16, 9, 0)));
        assertThat( interval.get().getEnd() , equalTo(of(2018,11,16, 17, 0)));
    }

    @Test
    public void currentInterval_fromMidday_returnsToday() {
        instance = new DailyIntervalSequence(9, 0,17, 0);
        LocalDateTime now = of(2018,11,15, 12, 1);

        Optional<Interval> interval = instance.currentInterval(now);

        assertThat( interval.get().getStart() , equalTo(of(2018,11,15, 9, 0)));
        assertThat( interval.get().getEnd() , equalTo(of(2018,11,15, 17, 0)));
    }

    @Test
    public void lastInterval_fromMidday_returnsYerday() {
        instance = new DailyIntervalSequence(9, 0,17, 0);
        LocalDateTime now = of(2018,11,15, 12, 1);

        Optional<Interval> interval = instance.lastInterval(now);

        assertThat( interval.get().getStart() , equalTo(of(2018,11,14, 9, 0)));
        assertThat( interval.get().getEnd() , equalTo(of(2018,11,14, 17, 0)));
    }


    @Test
    public void nextInterval_24hourPeriod_fromMidday() {
        instance = new DailyIntervalSequence(0, 0,23, 59);
        LocalDateTime now = of(2018,11,15, 12, 1);

        Optional<Interval> interval = instance.nextInterval(now);

        assertThat( interval.get().getStart() , equalTo(of(2018,11,16, 0, 0)));
        assertThat( interval.get().getEnd() , equalTo(of(2018,11,16, 23, 59)));
    }

    @Test
    public void getStart() {

    }

    @Test
    public void getEnd() {
    }

    @Test
    public void getDuration() {
    }
}