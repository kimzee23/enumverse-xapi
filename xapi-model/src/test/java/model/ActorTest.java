package model;

import org.enums.xapi.model.Actor;
import org.enums.xapi.model.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ActorTest {

    @DisplayName("Actor with mail box")
    @Test
    public void testActorWithMbox() {
        Actor actor = new Actor("mailto:test@mailinator.com", "Ade ope");
        assertEquals("mailto:test@mailinator.com", actor.getMbox());
        assertEquals("Ade ope", actor.getName());
        assertEquals("Agent", actor.getObjectType());
    }

    @DisplayName("Actor with account")
    @Test
    public void testActorWithAccount() {
        // Use the standalone Account class
        Account acc = new Account();
        acc.setHomePage("https://sit.com");
        acc.setName("userName");

        Actor actor = new Actor();
        actor.setAccount(acc);

        assertNotNull(actor.getAccount(), "Actor should have an account");
        assertEquals("userName", actor.getAccount().getName());
        assertEquals("https://sit.com", actor.getAccount().getHomePage());

        // Extra checks on Account itself
        assertEquals("userName", acc.getName());
        assertEquals("https://sit.com", acc.getHomePage());
    }
}
