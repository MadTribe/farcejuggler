package com.madtribe.farcejuggler.core;

import com.madtribe.farcejuggler.intervals.Interval;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class IntervalTest {


    @Test
    public void getStart() {
        LocalDateTime start = LocalDateTime.of(2018, 12, 16, 0, 0);
        LocalDateTime end = LocalDateTime.of(2018, 12, 18, 0, 0);
        Interval interval = new Interval(start, end);

        LocalDateTime retStart = interval.getStart();

        assertEquals(start, retStart);
    }

    @Test
    public void getEnd() {
        LocalDateTime start = LocalDateTime.of(2018, 12, 16, 0, 0);
        LocalDateTime end = LocalDateTime.of(2018, 12, 18, 0, 0);
        Interval interval = new Interval(start, end);

        LocalDateTime retEnd = interval.getEnd();

        assertEquals(end, retEnd);
    }


    @Test
    public void getDuration() {
        LocalDateTime start = LocalDateTime.of(2018, 12, 16, 0, 0);
        LocalDateTime end = LocalDateTime.of(2018, 12, 18, 0, 0);
        Interval interval = new Interval(start, end);

        Duration retEnd = interval.getDuration();

        assertEquals(Duration.ofDays(2), retEnd);
    }

    @Test
    public void contains_justBefore_returnsFalse() {
        LocalDateTime start = LocalDateTime.of(2018, 12, 16, 0, 0);
        LocalDateTime end = LocalDateTime.of(2018, 12, 18, 0, 0);
        LocalDateTime before = LocalDateTime.of(2018, 12, 15, 23, 59);
        Interval interval = new Interval(start, end);

        boolean retEnd = interval.contains(before);

        assertFalse(retEnd);
    }

    @Test
    public void contains_justAfter_returnsFalse() {
        LocalDateTime start = LocalDateTime.of(2018, 12, 16, 0, 0);
        LocalDateTime end = LocalDateTime.of(2018, 12, 18, 0, 0);
        LocalDateTime after = LocalDateTime.of(2018, 12, 18, 0, 1);
        Interval interval = new Interval(start, end);

        boolean retEnd = interval.contains(after);

        assertFalse(retEnd);
    }

    @Test
    public void contains_during_returnsTrue() {
        LocalDateTime start = LocalDateTime.of(2018, 12, 16, 0, 0);
        LocalDateTime end = LocalDateTime.of(2018, 12, 18, 0, 0);
        Interval interval = new Interval(start, end);
        LocalDateTime during1 = LocalDateTime.of(2018, 12, 17, 23, 59);
        LocalDateTime during2 = LocalDateTime.of(2018, 12, 16, 0, 1);

        boolean retEnd1 = interval.contains(during1);
        boolean retEnd2 = interval.contains(during2);

        assertTrue(retEnd1);
        assertTrue(retEnd2);
    }

    @Test
    public void add_overlappingRight_returnsLongerPeriod() {
        LocalDateTime i1Start = LocalDateTime.of(2018, 12, 16, 0, 0);
        LocalDateTime i1End = LocalDateTime.of(2018, 12, 18, 0, 0);
        LocalDateTime i2Start = LocalDateTime.of(2018, 12, 17, 23, 59);
        LocalDateTime i2End = LocalDateTime.of(2018, 12, 18, 23, 59);
        Interval interval1 = new Interval(i1Start, i1End);
        Interval interval2 = new Interval(i2Start, i2End);

        Interval[] retEnd = interval1.add(interval2);

        assertEquals(1,retEnd.length);
        assertEquals(i1Start, retEnd[0].getStart());
        assertEquals(i2End, retEnd[0].getEnd());
    }

    @Test
    public void add_overlappingLeft_returnsLongerPeriod() {
        LocalDateTime i1Start = LocalDateTime.of(2018, 12, 16, 0, 0);
        LocalDateTime i1End = LocalDateTime.of(2018, 12, 18, 0, 0);
        LocalDateTime i2Start = LocalDateTime.of(2018, 12, 17, 23, 59);
        LocalDateTime i2End = LocalDateTime.of(2018, 12, 18, 23, 59);
        Interval interval1 = new Interval(i1Start, i1End);
        Interval interval2 = new Interval(i2Start, i2End);

        Interval[] retEnd = interval2.add(interval1);

        assertEquals(1,retEnd.length);
        assertEquals(i1Start, retEnd[0].getStart());
        assertEquals(i2End, retEnd[0].getEnd());
    }

    @Test
    public void add_notOverlappingLeft_returnsTwoIntervals() {
        LocalDateTime i1Start = LocalDateTime.of(2018, 12, 16, 0, 0);
        LocalDateTime i1End = LocalDateTime.of(2018, 12, 18, 0, 0);
        LocalDateTime i2Start = LocalDateTime.of(2018, 12, 18, 1, 59);
        LocalDateTime i2End = LocalDateTime.of(2018, 12, 19, 0, 0);
        Interval interval1 = new Interval(i1Start, i1End);
        Interval interval2 = new Interval(i2Start, i2End);

        Interval[] ret = interval2.add(interval1);

        assertEquals(2, ret.length);
        assertEquals(i1Start, ret[0].getStart());
        assertEquals(i1End, ret[0].getEnd());

        assertEquals(i2Start, ret[1].getStart());
        assertEquals(i2End, ret[1].getEnd());
    }

}