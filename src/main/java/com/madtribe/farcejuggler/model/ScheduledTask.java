package com.madtribe.farcejuggler.model;

import com.madtribe.farcejuggler.intervals.LimitedIntervalSequence;

import java.time.LocalDateTime;

public class ScheduledTask {
    private final String name;
    private LimitedIntervalSequence taskWorkIntervals;

    public ScheduledTask(String name, LimitedIntervalSequence taskWorkIntervals) {
        this.name = name;
        this.taskWorkIntervals = taskWorkIntervals;
    }

    public String name() {
        return name;
    }

    public LocalDateTime getStart() throws Exception {
        return taskWorkIntervals.getActualStart();
    }

    public LocalDateTime getEnd () throws Exception {
        return taskWorkIntervals.getActualEnd();
    }

}
