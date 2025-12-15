package model;

import org.enums.xapi.model.Attachment;
import org.enums.xapi.model.LanguageMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttachmentTest {

    @Test
    @DisplayName("Attachment should store all metadata correctly")
    void testAttachmentCreation() {
        Attachment att = new Attachment();
        att.setUsageType("https://id.tincanapi.com/attachment/supporting_media");
        att.setDisplay(LanguageMap.of("en-US", "User Image"));
        att.setDescription(LanguageMap.of("en-US", "Uploaded profile picture"));
        att.setContentType("image/png");
        att.setLength(2048L);
        att.setSha2("abcdef123456");
        att.setFileUrl("https://files.com/img.png");

        assertEquals("https://id.tincanapi.com/attachment/supporting_media", att.getUsageType());
        assertEquals("User Image", att.getDisplay().getFirstValue());
        assertEquals("Uploaded profile picture", att.getDescription().getFirstValue());
        assertEquals("image/png", att.getContentType());
        assertEquals(2048L, att.getLength());
        assertEquals("abcdef123456", att.getSha2());
        assertEquals("https://files.com/img.png", att.getFileUrl());
    }

    @Test
    @DisplayName("Helper method 'of' creates a valid attachment")
    void testAttachmentHelper() {
        Attachment att = Attachment.of(
                "https://semicolon.com/type",
                "Certificate",
                "Course completion certificate",
                "application/pdf",
                50000L,
                "hash123asa",
                "https://files.com/cert.pdf"
        );

        assertEquals("Certificate", att.getDisplay().getFirstValue());
        assertEquals("Course completion certificate", att.getDescription().getFirstValue());
        assertEquals("application/pdf", att.getContentType());
        assertEquals(50000L, att.getLength());
    }
}
