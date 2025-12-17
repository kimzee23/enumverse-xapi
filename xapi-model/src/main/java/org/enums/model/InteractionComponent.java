package org.enums.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class InteractionComponent {
    private String Id;
    private LanguageMap LanguageMap;

    public InteractionComponent(String Id, LanguageMap LanguageMap) {
        this.Id = Id;
        this.LanguageMap = LanguageMap;
    }
}
