package com.madtribe.farcejuggler.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class MutableProjectDefinition {
    private Optional<LocalDateTime> startDate = Optional.empty();
    private ArrayList<Task> tasks = new ArrayList<>();

    public MutableProjectDefinition() {
    }

    public MutableProjectDefinition withStartDate(LocalDateTime startDate) {
        this.startDate = Optional.of(startDate);
        return this;
    }

    public MutableProjectDefinition withTask(Task task) {
        this.tasks.add(task);
        return this;
    }

    public Optional<LocalDateTime> getStartDate() {
        return startDate;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
