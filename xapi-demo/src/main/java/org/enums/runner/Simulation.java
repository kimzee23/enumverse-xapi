package org.enums.runner;

import org.enums.client.XapiClient;
import org.enums.client.XapiResponse;
import org.enums.model.Actor;
import org.enums.model.Activity;
import org.enums.model.Verb;
import org.enums.model.XapiStatement;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Simulation {

    private final XapiClient xapiClient;

    public Simulation(XapiClient xapiClient) {
        this.xapiClient = xapiClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runSimulation() {
        System.out.println("--- SIMULATION STARTING (APPLICATION READY) ---");

        try {
            XapiStatement statement = getXapiStatement();

            System.out.println("Sending Statement to LRS...");
            XapiResponse response = xapiClient.sendStatement(statement);

            if (response.isSuccess()) {
                System.out.println("SUCCESS! Statement stored in LRS.");
                System.out.println("Status: " + response.getStatus());
            } else {
                System.out.println("FAILED. LRS rejected it.");
                System.out.println("Reason: " + response.getBody());
            }
        } catch (Exception e) {
            System.out.println("Simulation failed — LRS not reachable");
            e.printStackTrace();
        }
    }

    private static XapiStatement getXapiStatement() {
        Actor actor = getActor();

        Verb verb = new Verb(
                "http://adlnet.gov/expapi/verbs/completed",
                "completed"
        );

        Activity activity = getActivity();

        return getXapiStatement(actor, verb, activity);
    }

    private static Actor getActor() {
        Actor actor = new Actor();
        actor.setMbox("mailto:demo.student@enumverse.com");
        actor.setName("Demo Student");
        return actor;
    }

    private static Activity getActivity() {
        Activity activity = new Activity();
        activity.setId("https://enumverse.com/course/java-101");
        return activity;
    }

    private static XapiStatement getXapiStatement(
            Actor actor,
            Verb verb,
            Activity activity
    ) {
        XapiStatement statement = new XapiStatement();
        statement.setActor(actor);
        statement.setVerb(verb);
        statement.setObject(activity);
        return statement;
    }
}
