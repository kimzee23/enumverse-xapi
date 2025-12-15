package org.enums.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result {
    @JsonProperty("score")
    private Score score;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("completion")
    private Boolean completion;

    @JsonProperty("response")
    private String response;
}
