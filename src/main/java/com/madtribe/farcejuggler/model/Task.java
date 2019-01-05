package com.madtribe.farcejuggler.model;

import com.madtribe.farcejuggler.intervals.IntervalSequence;

import java.time.Duration;
import java.util.Optional;

public class Task {
    private String name = "";
    private Duration duration = Duration.ofSeconds(0);
    private Optional<IntervalSequence> intervalSequence = Optional.empty();

    public Task withName(String name) {
        this.name = name;
        return this;
    }

    public Task withDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public String name() {
        return name;
    }

    public Duration duration() {
        return duration;
    }

    public Task withSchedule(IntervalSequence intervalSequence) {
        this.intervalSequence = Optional.ofNullable(intervalSequence);

        return this;
    }

    public Optional<IntervalSequence> getSchedule() {
        return intervalSequence;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", duration=" + (duration == null ? "-" : duration.toMinutes() + " mins") +
                ", intervalSequence=" + intervalSequence +
                '}';
    }
}
