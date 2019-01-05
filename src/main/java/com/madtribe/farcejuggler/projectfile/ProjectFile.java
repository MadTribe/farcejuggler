package com.madtribe.farcejuggler.projectfile;

import java.util.List;

public class ProjectFile {
    private String startDate;
    private List<WorkStream> workstreams;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<WorkStream> getWorkstreams() {
        return workstreams;
    }

    public void setWorkstreams(List<WorkStream> workstreams) {
        this.workstreams = workstreams;
    }

    @Override
    public String toString() {
        return "ProjectFile{" +
                "startDate='" + startDate + '\'' +
                ", workstreams=" + workstreams +
                '}';
    }
}
