package com.madtribe.farcejuggle.integrationtests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.madtribe.farcejuggler.cmd.Main;
import com.madtribe.farcejuggler.core.SchedulingConstants;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class Can_schedule_tasks_in_stream {
    private Main sut;

    @Before
    public void setup(){
           sut = new Main();

    }

    @Test
    public void can_schedule_1_task_6_hours() throws Exception {
        // TODO fix path separator.
        StringBuilder report = sut.run(new String[]{"file=./target/test-classes/1-task-6-hours.json"});
        System.err.println(report);

        JsonParser parser = new JsonParser();
        JsonElement projectRoot = parser.parse(report.toString());

        assertEquals(LocalDateTime.of(2018,12,19, 0 ,0, 0),projectStart(projectRoot));

        JsonObject task1 = taskWithIndex(projectRoot, 0);

        LocalDateTime expectedStartTime = LocalDateTime.of(LocalDate.of(2018,12,19), SchedulingConstants.DEFAULT_WORKING_HOURS.getStartTime());
        assertEquals(expectedStartTime, taskStart(task1));
        assertEquals(expectedStartTime.plus(6,ChronoUnit.HOURS),taskEnd(task1));

    }


    @Test
    public void can_schedule_5_tasks_each_6_hours() throws Exception {
        // TODO fix path separator.
        LocalDate startDay = LocalDate.of(2018,12,19);
        StringBuilder report = sut.run(new String[]{"file=./target/test-classes/5-tasks-each-6-hours.json"});
        System.err.println(report);

        JsonParser parser = new JsonParser();
        JsonElement projectRoot = parser.parse(report.toString());

        assertEquals(LocalDateTime.of(2018,12,19, 0 ,0, 0),projectStart(projectRoot));

        JsonObject task1 = taskWithIndex(projectRoot, 0);
        LocalDateTime expectedStartTime = LocalDateTime.of(startDay, SchedulingConstants.DEFAULT_WORKING_HOURS.getStartTime());
        assertEquals(expectedStartTime, taskStart(task1));
        assertEquals(expectedStartTime.plus(6,ChronoUnit.HOURS),taskEnd(task1));

        JsonObject task2 = taskWithIndex(projectRoot, 1);
        LocalDateTime expectedStartTime2 = LocalDateTime.of(LocalDate.of(2018,12,19), SchedulingConstants.DEFAULT_WORKING_HOURS.getStartTime().plus(6,ChronoUnit.HOURS));
        LocalDateTime expectedEndTime2 = LocalDateTime.of(LocalDate.of(2018,12,20), SchedulingConstants.DEFAULT_WORKING_HOURS.getStartTime().plus(4,ChronoUnit.HOURS));
        assertEquals(expectedStartTime2, taskStart(task2));
        assertEquals(expectedEndTime2,taskEnd(task2));
    }


    private LocalDateTime projectStart(JsonElement projectRoot){
        return LocalDateTime.parse(projectRoot.getAsJsonObject().get("start").getAsString());
    }

    private LocalDateTime projectEnd(JsonElement projectRoot){
        return LocalDateTime.parse(projectRoot.getAsJsonObject().get("end").getAsString());
    }

    private JsonObject taskWithIndex(JsonElement projectRoot, int index){
        return projectRoot.getAsJsonObject().get("tasks").getAsJsonArray().get(index).getAsJsonObject();
    }

    private String taskName(JsonObject task){
        return task.get("name").getAsString();
    }

    private LocalDateTime taskStart(JsonObject task){
        return LocalDateTime.parse(task.get("start").getAsString());
    }

    private LocalDateTime taskEnd(JsonObject task){
        return LocalDateTime.parse(task.get("end").getAsString());
    }
}
