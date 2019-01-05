package com.madtribe.farcejuggler.intervals;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

public class DailyIntervalSequence extends AbstractIntervalSequence implements IntervalSequence {
    private final LocalTime startTime;
    private final LocalTime endTime;

    public DailyIntervalSequence(int startHour, int startMin, int endHour, int endMin) {
        startTime = LocalTime.of(startHour, startMin);
        endTime = LocalTime.of(endHour, endMin);
    }

    @Override
    public Optional<Interval> currentInterval(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();

        LocalDateTime start;
        LocalDateTime end;
        LocalDate day = dateTime.toLocalDate();
        if (time.isAfter(startTime) && time.isBefore(endTime)) {
            start = LocalDateTime.of(day, startTime);
            end = LocalDateTime.of(day, endTime);
        } else {
            return Optional.empty();
        }

        return Optional.of(new Interval(start, end));
    }

    @Override
    public Optional<Interval> lastInterval(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        LocalDateTime start;
        LocalDateTime end;
        LocalDate day = dateTime.toLocalDate();
        if (time.isAfter(endTime)) {
            start = LocalDateTime.of(day, startTime);
            end = LocalDateTime.of(day, endTime);
        } else {
            LocalDate tomoorrow = day.minus(1, DAYS);
            start = LocalDateTime.of(tomoorrow, startTime);
            end = LocalDateTime.of(tomoorrow, endTime);
        }

        return Optional.of(new Interval(start, end));
    }

    @Override
    public Optional<Interval> nextInterval(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        LocalDateTime start;
        LocalDateTime end;
        LocalDate day = dateTime.toLocalDate();
        if (time.isBefore(startTime)) {
            start = LocalDateTime.of(day, startTime);
            end = LocalDateTime.of(day, endTime);
        } else {
            LocalDate tomoorrow = day.plus(1, DAYS);
            start = LocalDateTime.of(tomoorrow, startTime);
            end = LocalDateTime.of(tomoorrow, endTime);
        }

        return Optional.of(new Interval(start, end));
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
