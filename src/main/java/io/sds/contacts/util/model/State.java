package io.sds.contacts.util.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class State {
    
    @Schema(
            description = "state abbreviation",
            name = "abbreviation",
            minimum = "2",
            maximum = "2",
            example = "OH")
    @NotNull
    private String abbreviation;
    
    @Schema(
            description = "full state name",
            name = "name",
            example = "Ohio")
    @NotNull
    private String name;

    public State(String abbreviation,String name) {
        this.abbreviation = abbreviation;
        this.name = name;
    }
    
    public State() {}

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
