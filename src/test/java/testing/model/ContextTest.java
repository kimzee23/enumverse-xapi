package testing.model;

import org.enums.xapi.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ContextModelTest {

    @Test
    @DisplayName("Context should store and return all fields correctly")
    void testContextFields() {
        Context context = new Context();

        UUID reg = UUID.randomUUID();
        Actor instructor = new Actor("mailto:instructor@example.com", "Instructor");
        Actor team = new Actor("mailto:team@example.com", "Team Member");

        ContextActivities contextActivities = getActivities();

        context.setRegistration(reg);
        context.setInstructor(instructor);
        context.setTeam(team);
        context.setContextActivities(contextActivities);
        context.setRevision("1.0");
        context.setPlatform("Android");
        context.setLanguage("en-US");

        Map<String, Object> ext = new HashMap<>();
        ext.put("https://example.com/ext", "value");
        context.setExtensions(ext);

        assertEquals(reg, context.getRegistration());
        assertEquals(instructor, context.getInstructor());
        assertEquals(team, context.getTeam());
        assertEquals(contextActivities, context.getContextActivities());
        assertEquals("1.0", context.getRevision());
        assertEquals("Android", context.getPlatform());
        assertEquals("en-US", context.getLanguage());
        assertEquals("value", context.getExtensions().get("https://example.com/ext"));
    }

    private static ContextActivities getActivities() {
        ContextActivities contextActivities = new ContextActivities();
        List<Activity> parent = List.of(new Activity("parent1", "parent"));
        List<Activity> grouping = List.of(new Activity("group1", "grouping"));
        List<Activity> category = List.of(new Activity("category1", "category"));
        List<Activity> other = List.of(new Activity("other1", "other"));

        contextActivities.setParent(parent);
        contextActivities.setGrouping(grouping);
        contextActivities.setCategory(category);
        contextActivities.setOther(other);
        return contextActivities;
    }

    @Test
    @DisplayName("Context default extensions map should not be null")
    void testContextExtensionsNotNull() {
        Context ctx = new Context();
        assertNotNull(ctx.getExtensions());
        assertTrue(ctx.getExtensions().isEmpty());
    }

    @Test
    @DisplayName("Context instructor and team can be null safely")
    void testActorNullSafety() {
        Context ctx = new Context();

        assertNull(ctx.getInstructor());
        assertNull(ctx.getTeam());

        ctx.setInstructor(new Actor("mailto:ade@mail.com", "Ade"));
        ctx.setTeam(new Actor("mailto:ope@mail.com", "Ope"));

        assertNotNull(ctx.getInstructor());
        assertNotNull(ctx.getTeam());
    }

    @Test
    @DisplayName("ContextActivities should store and return lists correctly")
    void testContextActivitiesLists() {
        ContextActivities contextActivities = getContextActivities();

        assertEquals(1, contextActivities.getParent().size());
        assertEquals("parent1", contextActivities.getParent().get(0).getId());

        assertEquals(1, contextActivities.getGrouping().size());
        assertEquals("group1", contextActivities.getGrouping().get(0).getId());

        assertEquals(1, contextActivities.getCategory().size());
        assertEquals("category1", contextActivities.getCategory().get(0).getId());

        assertEquals(1, contextActivities.getOther().size());
        assertEquals("other1", contextActivities.getOther().get(0).getId());
    }

    private static ContextActivities getContextActivities() {
        ContextActivities contextActivities = new ContextActivities();

        Activity parent = new Activity("parent1", "parent");
        Activity group = new Activity("group1", "group");
        Activity category = new Activity("category1", "category");
        Activity other = new Activity("other1", "other");

        contextActivities.setParent(List.of(parent));
        contextActivities.setGrouping(List.of(group));
        contextActivities.setCategory(List.of(category));
        contextActivities.setOther(List.of(other));
        return contextActivities;
    }

    @Test
    @DisplayName("ContextActivities lists can be null safely")
    void testContextActivitiesNullSafety() {
        ContextActivities ca = new ContextActivities();

        assertNull(ca.getParent());
        assertNull(ca.getGrouping());
        assertNull(ca.getCategory());
        assertNull(ca.getOther());
    }

    @Test
    @DisplayName("Context can hold empty ContextActivities")
    void testContextWithEmptyContextActivities() {
        Context ctx = new Context();
        ContextActivities ca = new ContextActivities();

        ctx.setContextActivities(ca);

        assertNotNull(ctx.getContextActivities());
        assertNull(ca.getParent());
        assertNull(ca.getGrouping());
        assertNull(ca.getCategory());
        assertNull(ca.getOther());
    }
}
