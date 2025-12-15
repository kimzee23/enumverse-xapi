package org.enums.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attachment {

    @JsonProperty("usageType")//IRI
    private String usageType;

    @JsonProperty("display")
    private LanguageMap display;

    @JsonProperty("description")
    private LanguageMap description;

    @JsonProperty("contentType")
    private String contentType;

    @JsonProperty("length")
    private Long length;

    @JsonProperty("sha2")
    private String sha2;

    @JsonProperty("fileUrl")
    private String fileUrl;

    public static Attachment of(String usageType,
                                String name,
                                String description,
                                String contentType,
                                long length,
                                String sha2,
                                String fileUrl) {

        Attachment a = new Attachment();
        a.setUsageType(usageType);
        a.setDisplay(LanguageMap.of("en-US", name));
        a.setDescription(LanguageMap.of("en-US", description));
        a.setContentType(contentType);
        a.setLength(length);
        a.setSha2(sha2);
        a.setFileUrl(fileUrl);

        return a;
    }
}
