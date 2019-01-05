package com.madtribe.farcejuggler.intervals;

import java.time.Duration;
import java.time.LocalDateTime;

public class Interval {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Interval(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }

    public boolean contains(LocalDateTime dateTime){
        return (dateTime.isEqual(start) ||dateTime.isEqual(end) ||
                (dateTime.isAfter(start) && dateTime.isBefore(end)));
    }
    @Override
    public String toString() {
        return "Interval{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    public Interval[] add(Interval interval2) {
        if (contains(interval2.getStart())){
            return new Interval[]{ new Interval(this.start, interval2.end)};
        } else if (contains(interval2.getEnd())) {
            return new Interval[]{ new Interval(interval2.start, this.end)};
        } else {
            if (this.getEnd().isBefore(interval2.getStart())) {
                return new Interval[]{this, interval2};
            } else {
                return new Interval[]{interval2, this};
            }
        }
    }
}
