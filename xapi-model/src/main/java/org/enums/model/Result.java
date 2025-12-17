package org.enums.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
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
