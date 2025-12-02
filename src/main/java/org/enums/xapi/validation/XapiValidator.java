package org.enums.xapi.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.enums.xapi.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class XapiValidator {

    private final ObjectMapper mapper = new ObjectMapper();

    @Getter
    public static class ValidationResult {
        private final boolean valid;
        private final List<String> messages;
        public ValidationResult(boolean valid, List<String> messages) {
            this.valid = valid; this.messages = messages;
        }
    }

    public ValidationResult validate(JsonNode json) {
        try {
            XapiStatement st = mapper.treeToValue(json, XapiStatement.class);
            return validate(st);
        } catch (Exception e) {
            List<String> m = new ArrayList<>();
            m.add("invalid JSON mapping: " + e.getMessage());
            return new ValidationResult(false, m);
        }
    }

    public ValidationResult validate(XapiStatement st) {
        List<String> errors = new ArrayList<>();
        if (st == null) {
            errors.add("statement is null");
            return new ValidationResult(false, errors);
        }
        // actor
        if (st.getActor() == null) {
            errors.add("missing actor");
        } else {
            Actor a = st.getActor();
            boolean identified = (a.getMbox() != null && !a.getMbox().isBlank())
                    || (a.getAccount() != null && a.getAccount().getName() != null && !a.getAccount().getName().isBlank());
            if (!identified) errors.add("actor must have mbox or account.name");
        }
        // verb
        if (st.getVerb() == null || st.getVerb().getId() == null || st.getVerb().getId().isBlank()) {
            errors.add("missing verb.id");
        } else {
            // display language map must not be empty
            LanguageMap lm = st.getVerb().getDisplay();
            if (lm == null || lm.isEmpty()) {
                errors.add("verb.display must have at least one language entry");
            }
            // optional: ensure id looks like IRI
            String vid = st.getVerb().getId();
            if (!looksLikeIri(vid)) errors.add("verb.id does not look like an IRI: " + vid);
        }
        // object
        if (st.getObject() == null || st.getObject().getId() == null || st.getObject().getId().isBlank()) {
            errors.add("missing object.id");
        }
        // timestamp check (optional)
        if (st.getTimestamp() == null) {
            // it's okay, LRS can assign timestamp; not an error
        }

        return new ValidationResult(errors.isEmpty(), errors);
    }

    private boolean looksLikeIri(String s) {
        return s != null && (s.startsWith("http://") || s.startsWith("https://") || s.contains(":"));
    }

    /** Strict variant: throw if invalid */
    public void validateOrThrow(XapiStatement st) {
        ValidationResult vr = validate(st);
        if (!vr.isValid()) throw new IllegalArgumentException(String.join("; ", vr.getMessages()));
    }
}
