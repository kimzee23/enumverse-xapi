package testing.model;

import org.enums.xapi.model.Actor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActorTest {

    @DisplayName("Actor with mail box")
    @Test
    public void testActorWithMbox(){
        Actor actor = new Actor("mailto:test@mailinator.com","Ade ope");
        assertEquals("mailto:test@mailinator.com", actor.getMbox());
        assertEquals("Ade ope", actor.getName());
        assertEquals("Agent", actor.getObjectType());

    }
    @DisplayName("Actor with account")
    @Test
    public void testActorWithAccount() {
        Actor.Account acc = new Actor.Account("https://sit.com", "userName");

        Actor actor = new Actor();
        actor.setAccount(acc);

        assertNotNull(actor.getAccount(), "Actor should have an account");
        assertEquals("userName", actor.getAccount().getName());
        assertEquals("https://sit.com", actor.getAccount().getHomePage());


        assertEquals("userName", acc.getName());
        assertEquals("https://sit.com", acc.getHomePage());
}
    }