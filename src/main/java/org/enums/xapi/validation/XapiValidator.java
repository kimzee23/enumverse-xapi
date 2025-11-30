package org.enums.xapi.validation;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.enums.xapi.model.Actor;
import org.enums.xapi.model.XapiStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class XapiValidator {
    private final ObjectMapper mapper = new ObjectMapper();

    public static class ValidationResult {
        private final boolean valid;
        private final List<String> messages;
        public ValidationResult(boolean valid, List<String> messages) {
            this.valid = valid; this.messages = messages;
        }
        public boolean isValid() { return valid; }
        public List<String> getMessages() { return messages; }
    }

    public ValidationResult validate(JsonNode json) {
        List<String> errors = new ArrayList<>();
        try {
            XapiStatement st = mapper.treeToValue(json, XapiStatement.class);
            // id (optional)
            if (st.getId() != null) {
                try { UUID.fromString(st.getId().toString()); } catch (Exception e) {
                    errors.add("id is not valid UUID");
                }
            }
            // actor
            if (st.getActor() == null) errors.add("missing actor");
            else {
                Actor a = st.getActor();
                boolean hasId = (a.getMbox()!=null && !a.getMbox().isBlank()) ||
                        (a.getAccount()!=null && a.getAccount().getName()!=null && !a.getAccount().getName().isBlank());
                if (!hasId) errors.add("actor must have mbox or account.name");
            }
            // verb
            if (st.getVerb()==null || st.getVerb().getId()==null || st.getVerb().getId().isBlank())
                errors.add("missing verb.id");
            else if (!looksLikeIri(st.getVerb().getId()))
                errors.add("verb.id does not look like an IRI");
            // object
            if (st.getObject()==null || st.getObject().getId()==null || st.getObject().getId().isBlank())
                errors.add("missing object.id");
            // timestamp
            if (json.has("timestamp")) {
                try { java.time.Instant.parse(json.get("timestamp").asText()); }
                catch (Exception e) { errors.add("timestamp invalid: "+e.getMessage()); }
            }
            // attach raw
            st.setRaw(json);
        } catch (Exception e) {
            errors.add("invalid JSON mapping: " + e.getMessage());
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }

    private boolean looksLikeIri(String s) {
        return s != null && (s.startsWith("http://") || s.startsWith("https://") || s.contains(":"));
    }
}
