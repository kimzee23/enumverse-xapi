package org.enums.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.Instant;
import java.util.Collections;

import static org.junit.Assert.*;


public class JsonMapperTest {

    @Test
    public void instance_shouldNotBeNull() {
        assertNotNull(JsonMapper.INSTANCE.toString(), "ObjectMapper instance should be initialized");
    }

    @Test
    public void shouldSerializeEmptyBean_withoutError() {
        assertDoesNotThrow(() -> {
            String json = JsonMapper.INSTANCE.writeValueAsString(new EmptyClass());
            assertEquals("{}", json);
        }, "Should serialize empty objects because FAIL_ON_EMPTY_BEANS is disabled");
    }

    @Test
    public void shouldSerializeJavaTimeTypes() {
        assertDoesNotThrow(() -> {
            Instant now = Instant.now();
            String json = JsonMapper.INSTANCE.writeValueAsString(now);
            assertNotNull(json);
            assertFalse(json.isEmpty());
        }, "Should support JavaTimeModule (Instant, LocalDate) automatically");
    }

    @Test
    public void instance_shouldBeReusable() throws JsonProcessingException {
        String json1 = JsonMapper.INSTANCE.writeValueAsString(Collections.singletonMap("key", "value"));
        String json2 = JsonMapper.INSTANCE.writeValueAsString(123);

        assertEquals("{\"key\":\"value\"}", json1);
        assertEquals("123", json2);
    }

    private static class EmptyClass {

    }
}