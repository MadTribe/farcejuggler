package com.madtribe.farcejuggler.projectfile;

import com.google.gson.Gson;
import com.madtribe.farcejuggler.intervals.IntervalSequence;
import com.madtribe.farcejuggler.model.MutableProjectDefinition;
import com.madtribe.farcejuggler.model.Task;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.madtribe.farcejuggler.core.SchedulingConstants.DEFAULT_WORKING_HOURS;

public class ProjectDefinitionReader {
    public static final String DATE_FORMAT = "YYYY-MM-dd";
    public MutableProjectDefinition readFile(InputStream taskFile) throws Exception {
        InputStreamReader reader = new InputStreamReader(taskFile);
        Gson gson = new Gson();
        ProjectFile projectFile = gson.fromJson(reader, ProjectFile.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        MutableProjectDefinition projectDefinition = new MutableProjectDefinition();
        if (projectFile.getStartDate() != null){

            LocalDateTime projectStart =  LocalDate.parse(projectFile.getStartDate()).atStartOfDay();
            projectDefinition.withStartDate(projectStart);
        }

        if (projectFile.getWorkstreams() != null){
            for (WorkStream ws : projectFile.getWorkstreams()) {
                if (ws.getTasks() != null){
                    String schedule = ws.getSchedule();


                    for (FileTask t : ws.getTasks()){

                        Task taskModel = new Task().withName(t.getName());
                        Duration duration = parseDuration(t.getDuration());

                        if (schedule == null){
                            IntervalSequence sequence = DEFAULT_WORKING_HOURS;
                            taskModel.withSchedule(sequence);
                        }
                        if(duration != null){
                            taskModel.withDuration(duration);
                        }

                        projectDefinition.withTask(taskModel);
                    }
                }
            }
        }


        return projectDefinition;
    }

    private String durationPatternString = "(\\d+) *([a-zA-Z]+)";
    private Pattern r = Pattern.compile(durationPatternString);

    private Duration parseDuration(String duration) throws Exception {
        Duration ret = null;
        if (duration != null){
            Matcher m = r.matcher(duration);
            if (m.find()) {
                String valStr = m.group(1);
                String units = m.group(2);
                long val = Long.parseLong(valStr);

                if (units.equals("mins")){
                    ret = Duration.ofMinutes(val);
                }
                if (units.equals("hrs") || units.equals("hours")){
                    ret = Duration.ofHours(val);
                }
                if (units.equals("days")){
                    ret = Duration.ofDays(val);
                }
                if (units.equals("weeks")){
                    ret = Duration.ofDays(val * 7);
                }
            } else {
                throw new Exception("" + duration + " does not match duration format. ");
            }

        }
        return ret;
    }
}
