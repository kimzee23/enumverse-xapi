package testing.model;

import org.enums.xapi.model.Activity;
import org.enums.xapi.model.LanguageMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest {
    @DisplayName("Activity name resolution")
    @Test
    public void testActivityNameResolution() {
        Activity act = new Activity("activityOne", "watching video");

        assertEquals("activityOne", act.getId());
        assertEquals("watching video", act.getName());
    }
    @DisplayName("Activity definition set")
    @Test
    public void testActivityDefinitionSet() {
        LanguageMap map = LanguageMap.of("en-US", "Lesson one");
        Activity.Definition def = new Activity.Definition(map);
        Activity act = new Activity();
        act.setDefinition(def);
        assertEquals("Lesson one", act.getName());
    }

}
