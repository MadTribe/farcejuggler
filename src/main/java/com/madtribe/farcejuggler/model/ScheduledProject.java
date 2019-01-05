package com.madtribe.farcejuggler.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduledProject {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private List<ScheduledTask> allTasks;

    public ScheduledProject(LocalDateTime startDate,
                            LocalDateTime endDate,
                            List<ScheduledTask> allTasks) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.allTasks = allTasks;
    }

    public LocalDateTime startDate() {
        return startDate;
    }

    public LocalDateTime endDate() {
        return endDate;
    }

    public List<ScheduledTask> allTasks() {
        return allTasks;
    }

    public static class Builder {
        LocalDateTime startDate;
        LocalDateTime endDate;
        List<ScheduledTask> allTasks = new ArrayList<>();

        public Builder withStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public void addTask(ScheduledTask scheduledTask) {
            allTasks.add(scheduledTask);
        }

        public ScheduledProject build(){
            return new ScheduledProject(startDate, endDate, this.allTasks);
        }
    }

    @Override
    public String toString() {
        return "ScheduledProject{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", allTasks=" + allTasks +
                '}';
    }
}
