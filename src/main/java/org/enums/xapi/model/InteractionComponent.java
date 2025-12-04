package org.enums.xapi.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InteractionComponent {
    private String Id;
    private LanguageMap LanguageMap;

    public InteractionComponent(String Id, LanguageMap LanguageMap) {
        this.Id = Id;
        this.LanguageMap = LanguageMap;
    }
}
