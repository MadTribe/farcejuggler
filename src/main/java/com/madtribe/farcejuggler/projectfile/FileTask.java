package com.madtribe.farcejuggler.projectfile;

public class FileTask {
    private String name;
    private String duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "FileTask{" +
                "name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
