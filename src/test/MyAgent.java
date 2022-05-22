package test;

import jade.core.Agent;

public class MyAgent extends Agent {
    @Override
    public void setup () {
// Agent initialization.
        System.out.println("MyAgent ready.");
// Kill agent.
        doDelete();
    }
}