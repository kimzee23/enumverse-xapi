package org.enums.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContextActivities {

    @JsonProperty("parent")
    private List<Activity> parent;

    @JsonProperty("grouping")
    private List<Activity> grouping;

    @JsonProperty("category")
    private List<Activity> category;

    @JsonProperty("other")
    private List<Activity> other;  // any other related activities
}
