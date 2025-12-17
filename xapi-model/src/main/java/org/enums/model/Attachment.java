package org.enums.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

        Attachment attachment = new Attachment();
        attachment.setUsageType(usageType);
        attachment.setDisplay(LanguageMap.of("en-US", name));
        attachment.setDescription(LanguageMap.of("en-US", description));
        attachment.setContentType(contentType);
        attachment.setLength(length);
        attachment.setSha2(sha2);
        attachment.setFileUrl(fileUrl);

        return attachment;
    }
}
