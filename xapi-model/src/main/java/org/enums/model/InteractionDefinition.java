package org.enums.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.enums.enums.InteractionType;

import java.util.List;
@Getter
@Setter
public class InteractionDefinition {
    @JsonProperty("interactionType")
    private InteractionType interactionType;

    @JsonProperty("choices")
    private List<InteractionComponent> choices;

    @JsonProperty("scale")
    private List<InteractionComponent> scale;

    @JsonProperty("target")
    private List<InteractionComponent> target;

    @JsonProperty("steps")
    private List<InteractionComponent> steps;

    @JsonProperty("source")
    private List<InteractionComponent> source;


}
