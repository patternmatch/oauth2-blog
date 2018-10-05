package com.patternmatch.oauth2blog.model;

import java.io.Serializable;

public class Welcome implements Serializable {

    private static final String GREETINGS_FORMAT = new String("Welcome %s!");

    public String greetings;

    public Welcome() {
    }

    public Welcome(String who) {
        this.greetings = String.format(GREETINGS_FORMAT, who);
    }
}
