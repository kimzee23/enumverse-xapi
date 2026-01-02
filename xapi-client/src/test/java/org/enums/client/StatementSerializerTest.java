package org.enums.client;

import org.enums.model.*;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StatementSerializerTest {

    // 1. Happy Path: Fully Populated Statement
    @Test
    void toJson_shouldSerializeValidStatement() {
        XapiStatement st = new XapiStatement();
        st.setId((UUID.randomUUID()));
        Actor actor = new Actor();
        actor.setName("actor");
        actor.setMbox("test@example.com");

        st.setActor(actor);

        st.setVerb(new Verb("http://adlnet.gov/expapi/verbs/experienced", "experienced"));

        Activity activity = new Activity();
        activity.setId("http://example.com/activity");
        st.setObject(activity);

        String json = StatementSerializer.toJson(st);

        assertNotNull(json);
        assertTrue(json.contains("test@example.com"));
        assertTrue(json.contains("experienced"));
    }

    // 2. Empty Object: Verifies FAIL_ON_EMPTY_BEANS config
    @Test
    void toJson_shouldSerializeEmptyStatement() {
        XapiStatement st = new XapiStatement();
        // No fields set

        String json = StatementSerializer.toJson(st);

        // Should return empty JSON object "{}" not crash
        assertEquals("{}", json);
    }

    // 3. Null Input Handling
    @Test
    void toJson_shouldHandleNullInput() {
        String json = StatementSerializer.toJson(null);

        // Jackson standard behavior for null object is the string "null"
        assertEquals("null", json);
    }

    // 4. Date Serialization (JSR-310 Check)
    @Test
    void toJson_shouldSerializeDatesAsISOString() {
        XapiStatement st = new XapiStatement();
        Instant now = Instant.parse("2025-12-20T10:00:00.000Z");
        st.setTimestamp(now);

        String json = StatementSerializer.toJson(st);

        // Verify it is a String "2025..." not an array [2025, 12, ...]
        assertTrue(json.contains("\"2025-12-20T10:00:00Z\""));
    }

    // 5. Special Characters & Unicode
    @Test
    void toJson_shouldHandleSpecialCharacters() {
        XapiStatement st = new XapiStatement();
        // Strings with quotes, newlines, and Emojis
        st.setActor(new Actor("mailto:admin@example.com", "O'Connor \n \"The Boss\" 🚀"));

        String json = StatementSerializer.toJson(st);

        // Verify JSON escaping works (quotes escaped, emoji preserved)
        assertTrue(json.contains("O'Connor \\n \\\"The Boss\\\" 🚀"));
    }

    // 6. Complex Nested Maps (Context Extensions)
    @Test
    void toJson_shouldSerializeExtensionsMap() {
        XapiStatement st = new XapiStatement();
        Context context = new Context();

        Map<String, Object> extensions = new HashMap<>();
        extensions.put("http://example.com/int", 123);
        extensions.put("http://example.com/bool", true);
        extensions.put("http://example.com/str", "value");

        context.setExtensions(extensions);
        st.setContext(context);

        String json = StatementSerializer.toJson(st);

        assertTrue(json.contains("123"));
        assertTrue(json.contains("true"));
        assertTrue(json.contains("\"value\""));
    }

    // 7. Exception Handling (The "Impossible" Test)
    // To test the catch block, we must force Jackson to fail.
    // We do this by creating an anonymous subclass that throws on access.
    @Test
    void toJson_shouldWrapJsonProcessingException() {
        XapiStatement toxicStatement = new XapiStatement() {
            @Override
            public UUID getId() {
                // Throwing inside a getter causes Jackson to fail serialization
                throw new RuntimeException("Force Serialization Failure");
            }
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            StatementSerializer.toJson(toxicStatement);
        });

        assertEquals("Failed to serialize XapiStatement", exception.getMessage());
    }
}