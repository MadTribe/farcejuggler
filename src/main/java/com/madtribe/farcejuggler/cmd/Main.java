package com.madtribe.farcejuggler.cmd;

import com.google.gson.JsonObject;
import com.madtribe.farcejuggler.core.DefaultScheduler;
import com.madtribe.farcejuggler.model.MutableProjectDefinition;
import com.madtribe.farcejuggler.model.ScheduledProject;
import com.madtribe.farcejuggler.projectfile.ProjectDefinitionReader;
import com.madtribe.farcejuggler.reports.DefaultReport;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static final String PARAM_FORMAT_ERROR_MESSAGE = "Parameters must be <parameter-name>=<value>";
    private static final String TASK_FILE_PARAM = "file";

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    public StringBuilder run(String[] args) throws Exception {
        StringBuilder output = new StringBuilder();
        Map<String, String> params = processArgs(args);
        String taskFile =  params.get(TASK_FILE_PARAM);

        if (taskFile != null){
            ProjectDefinitionReader projectDefinitionReader = new ProjectDefinitionReader();

            Path file = new File(taskFile).toPath();
            InputStream inputStream = Files.newInputStream(file);
            MutableProjectDefinition projectDefinition = projectDefinitionReader.readFile(inputStream);


            ScheduledProject scheduled = new DefaultScheduler(LocalDateTime.now()).schedule(projectDefinition);


            JsonObject report = new DefaultReport().report(scheduled);

            // TODO setup logger
            output.append(report.toString());

        } else {
            // TODO setup logger
            System.err.println("please enter " + TASK_FILE_PARAM );
        }
        return output;
    }

    private Map<String, String> processArgs(String[] args) throws Exception {
        Map<String, String> params = new HashMap<>();
        for (String arg : args){
            String[] kv = arg.split("=");
            if (kv.length % 2 == 0) {
                params.put(kv[0], kv[1]);
            } else {
                throw new Exception(PARAM_FORMAT_ERROR_MESSAGE);
            }
        }
        return params;
    }
}
