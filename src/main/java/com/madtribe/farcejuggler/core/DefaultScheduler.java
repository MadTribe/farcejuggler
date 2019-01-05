package com.madtribe.farcejuggler.core;

import com.madtribe.farcejuggler.intervals.DailyIntervalSequence;
import com.madtribe.farcejuggler.intervals.IntervalSequence;
import com.madtribe.farcejuggler.intervals.LimitedSequence;
import com.madtribe.farcejuggler.model.MutableProjectDefinition;
import com.madtribe.farcejuggler.model.ScheduledProject;
import com.madtribe.farcejuggler.model.ScheduledTask;
import com.madtribe.farcejuggler.model.Task;

import java.time.LocalDateTime;

public class DefaultScheduler implements Scheduler {

    private final LocalDateTime today;

    private final IntervalSequence defaultIntervalSequence;

    public DefaultScheduler(LocalDateTime today) {
        this.today = today;
        defaultIntervalSequence = new DailyIntervalSequence(0,0, 23, 59);
    }

    public ScheduledProject schedule(MutableProjectDefinition project) throws Exception {
        ScheduledProject.Builder builder = new ScheduledProject.Builder();
        LocalDateTime startDate = today;
        if (project.getStartDate().isPresent()){
            startDate = project.getStartDate().get();
        } else {
            startDate = today;
        }

        LocalDateTime projectEndTime = startDate;
        for (Task t : project.getTasks()){
            log("Handling task: " + t);
            IntervalSequence intervalSequence = defaultIntervalSequence;
            if (t.getSchedule().isPresent()){
                intervalSequence = t.getSchedule().get();
                log("   Has an interval: ");
            }

            LimitedSequence limitedSequence = new LimitedSequence(projectEndTime, t.duration(), intervalSequence);
            log("   Expected start : " + limitedSequence.getActualStart());
            log("   Expected End : " + limitedSequence.getActualEnd());
            projectEndTime = limitedSequence.getActualEnd();
            builder.addTask(new ScheduledTask(t.name(),limitedSequence));

        }

        builder.withEndDate(projectEndTime);
        builder.withStartDate(startDate);

        return builder.build();
    }

    private void log(String s) {
        System.out.println(s);
    }


}
