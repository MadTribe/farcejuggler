package com.madtribe.farcejuggler.projectfile;

import java.util.List;

public class WorkStream {
    private String schedule;
    private List<FileTask> tasks;

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public List<FileTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<FileTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "WorkStream{" +
                "fileTasks=" + tasks +
                '}';
    }
}
