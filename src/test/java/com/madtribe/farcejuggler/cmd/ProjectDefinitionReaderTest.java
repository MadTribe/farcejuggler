package com.madtribe.farcejuggler.cmd;

import com.madtribe.farcejuggler.model.MutableProjectDefinition;
import com.madtribe.farcejuggler.projectfile.ProjectDefinitionReader;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class ProjectDefinitionReaderTest {

    private static final String JSON_DATA_EMPTY = "{}";
    private static final String JSON_DATA_ALL_FIELDS = "{\n" +
            "    \"startDate\":\"2018-12-19\",\n" +
            "    \"workstreams\" : [\n" +
            "          {\n" +
            "            \"tasks\":[\n" +
            "                {\n" +
            "                  \"name\":\"task1\",\n" +
            "                  \"duration\":\"3mins\"\n" +
            "                }\n" +
            "            ]\n" +
            "\n" +
            "          }\n" +
            "     ]\n" +
            "}";

    @Test
    public void readFile_empty_no_errors() throws Exception {
        ProjectDefinitionReader instance = new ProjectDefinitionReader();

        InputStream jsonStream = new ByteArrayInputStream(JSON_DATA_EMPTY.getBytes());
        instance.readFile(jsonStream);
    }

    @Test
    public void readFile_start_date_no_errors() throws Exception {
        System.err.println(JSON_DATA_ALL_FIELDS);

        ProjectDefinitionReader instance = new ProjectDefinitionReader();

        InputStream jsonStream = new ByteArrayInputStream(JSON_DATA_ALL_FIELDS.getBytes());
        MutableProjectDefinition projectDefintion = instance.readFile(jsonStream);
        System.err.println(projectDefintion);


        assertEquals(LocalDateTime.of(2018,12, 19,0,0,0), projectDefintion.getStartDate().get());
        assertEquals(1,projectDefintion.getTasks().size());
        assertEquals("task1",projectDefintion.getTasks().get(0).name());
        assertEquals(Duration.ofMinutes(3).toMinutes(),projectDefintion.getTasks().get(0).duration().toMinutes());
    }
}