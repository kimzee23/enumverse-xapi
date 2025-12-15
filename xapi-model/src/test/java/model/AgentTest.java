package model;

import org.enums.model.Account;
import org.enums.model.Agent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AgentTest {

    @DisplayName("Agent with mbox")
    @Test
    public void testAgentWithMbox() {
        Agent agent = new Agent("mailto:test@mailinator.com", "Ade ope");

        assertEquals("Agent", agent.getObjectType());
        assertEquals("mailto:test@mailinator.com", agent.getMbox());
        assertEquals("Ade ope", agent.getName());
    }

    @DisplayName("Agent with account")
    @Test
    public void testAgentWithAccount() {
        Account acc = new Account();
        acc.setHomePage("https://sit.com");
        acc.setName("userName");

        Agent agent = new Agent();
        agent.setAccount(acc);

        assertNotNull(agent.getAccount(), "Agent should have an account");
        assertEquals("userName", agent.getAccount().getName());
        assertEquals("https://sit.com", agent.getAccount().getHomePage());

        assertEquals("userName", acc.getName());
        assertEquals("https://sit.com", acc.getHomePage());
    }

    @DisplayName("Group Agent with members")
    @Test
    public void testGroupAgent() {
        Agent member1 = new Agent("mailto:one@mail.com", "Member One");
        Agent member2 = new Agent("mailto:two@mail.com", "Member Two");

        Agent group = new Agent();
        group.setObjectType("Group");
        group.setName("Team A");
        group.setMember(List.of(member1, member2));

        assertEquals("Group", group.getObjectType());
        assertEquals("Team A", group.getName());
        assertEquals(2, group.getMember().size());
        assertEquals("Member One", group.getMember().get(0).getName());
    }

    @DisplayName("Agent with openid")
    @Test
    public void testAgentWithOpenId() {
        Agent agent = new Agent();
        agent.setOpenid("https://openid.example.com/ade");

        assertEquals("https://openid.example.com/ade", agent.getOpenid());
    }
}
