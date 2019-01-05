package com.madtribe.farcejuggler.reports;

import com.google.gson.JsonObject;
import com.madtribe.farcejuggler.json.JsonBuilder;
import com.madtribe.farcejuggler.model.ScheduledProject;
import com.madtribe.farcejuggler.model.ScheduledTask;

public class DefaultReport {

    public JsonObject report(ScheduledProject project) throws Exception {
        JsonBuilder jsonBuilder = new JsonBuilder();
        JsonObject out = (JsonObject) jsonBuilder.object(b -> {
            b.field("start", project.startDate().toString())
                    .field("end",project.endDate().toString())
                    .array("tasks", c -> {
                        for (ScheduledTask st :  project.allTasks()){

                                c.addObject( d -> {
                                    try {
                                        d.field("name",st.name() )
                                        .field("start",st.getStart().toString() )
                                        .field("end",st.getEnd().toString() );

                                        return d;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return d;
                                });

                        }

                        return null;
                    });

            return b;
        }).build();


        return out;
    }
}
