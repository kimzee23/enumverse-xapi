package org.enums.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.enums.enums.InteractionType;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
