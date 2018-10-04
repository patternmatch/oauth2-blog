package com.patternmatch.oauth2blog.model;

public class Welcome {

    private static final String GREETINGS_FORMAT = new String("Welcome %s!");

    public String greetings;

    public Welcome(String who) {
        this.greetings = String.format(GREETINGS_FORMAT, who);
    }
}
