package org.enums.xapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Score {
    @JsonProperty("scaled")
    private Double scaled;

    @JsonProperty("raw")
    private Double raw;

    @JsonProperty("min")
    private Double min;

    @JsonProperty("max")
    private Double max;

    public Score() {}

    public Score(Double scaled, Double raw, Double min, Double max) {
        this.scaled = scaled;
        this.raw = raw;
        this.min = min;
        this.max = max;
    }
}
