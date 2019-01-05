package com.madtribe.farcejuggler.core;

import com.madtribe.farcejuggler.intervals.DailyIntervalSequence;
import com.madtribe.farcejuggler.model.MutableProjectDefinition;
import com.madtribe.farcejuggler.model.ScheduledProject;
import com.madtribe.farcejuggler.model.Task;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class DefaultSchedulerTest {

    private DefaultScheduler instance;
    private final LocalDateTime today = of(2018,12,14, 0, 0);

    @Before
    public void setup(){
        instance = new DefaultScheduler(today);

    }

    @Test
    public void schedule_returnsScheduledProject() throws Exception {
        MutableProjectDefinition project = new MutableProjectDefinition();

        ScheduledProject scheduled = instance.schedule(project);

        assertNotNull(scheduled);
    }

    @Test
    public void schedule_projectHasNoStartDate_usesToday() throws Exception {
        MutableProjectDefinition project = new MutableProjectDefinition();

        ScheduledProject scheduled = instance.schedule(project);

        assertEquals(today, scheduled.startDate());
    }

    @Test
    public void schedule_projectHasStartDate_usesStartDate() throws Exception {
        LocalDateTime startDate = of(2018,11,15, 0, 0);
        MutableProjectDefinition project = new MutableProjectDefinition().withStartDate(startDate);

        ScheduledProject scheduled = instance.schedule(project);

        assertEquals(startDate, scheduled.startDate());
    }

    @Test
    public void schedule_projectHasTask_scheduleHasTask() throws Exception {
        LocalDateTime startDate = of(2018,11,15, 0, 0);
        Task task = new Task().withName("kickoff");
        MutableProjectDefinition project = new MutableProjectDefinition().withStartDate(startDate).withTask(task);

        ScheduledProject scheduled = instance.schedule(project);

        assertThat( scheduled.allTasks().get(0).name(), equalTo("kickoff"));
    }

    @Test
    public void schedule_projectHas1DayTask_scheduleHasEndDate() throws Exception {
        LocalDateTime startDate = of(2018,11,15, 0, 1);
        Task task = new Task().withDuration(Duration.ofDays(1));
        MutableProjectDefinition project = new MutableProjectDefinition().withStartDate(startDate).withTask(task);

        ScheduledProject scheduled = instance.schedule(project);

        assertThat( scheduled.endDate(), equalTo(of(2018,11,16, 0, 2)));
    }

    @Test
    public void schedule_projectHasTwo1DayTasks_scheduleHasEndDate() throws Exception {
        LocalDateTime startDate = of(2018,11,15, 0, 1);
        Task task1 = new Task().withDuration(Duration.ofDays(1));
        Task task2 = new Task().withDuration(Duration.ofDays(1));
        MutableProjectDefinition project = new MutableProjectDefinition().withStartDate(startDate).withTask(task1).withTask(task2);

        ScheduledProject scheduled = instance.schedule(project);

        assertThat( scheduled.endDate(), equalTo(of(2018,11,17, 0, 3)));
    }

    @Test
    public void schedule_9hourTaskAt3hoursPerDay_endDate3daysLater() throws Exception {
        LocalDateTime startDate = of(2018,11,15, 0, 0);
        Task task = new Task().withDuration(Duration.ofHours(9)).withSchedule(new DailyIntervalSequence(11,30, 14, 30));
        MutableProjectDefinition project = new MutableProjectDefinition().withStartDate(startDate).withTask(task);

        ScheduledProject scheduled = instance.schedule(project);

        assertThat( scheduled.endDate(), equalTo(of(2018,11,17, 14, 30)));
    }
}