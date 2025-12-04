package org.enums.xapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContextActivities {

    // parent activities, e.g., course
    @JsonProperty("parent")
    private List<Activity> parent;

    @JsonProperty("grouping")
    private List<Activity> grouping;     // group of related activities

    // category or type of activity
    @JsonProperty("category")
    private List<Activity> category;

    @JsonProperty("other")
    private List<Activity> other;  // any other related activities
}
