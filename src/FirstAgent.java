import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;
import java.util.concurrent.TimeUnit;

public class FirstAgent extends Agent {
    // Инициализация агента
	protected void setup() {
        System.out.println("Привет, меня зовут : " + getAID().getLocalName() + "\n");

        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String senderAgentName = msg.getSender().getLocalName();
                    if (senderAgentName.equals("Player_1")) {

                        String messageText = msg.getContent();
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                        }

                        if (messageText.equals("End") || messageText.isEmpty()) {
                            System.out.println("Игра окончена");
                            doDelete();
                            System.exit(0);
                        }

                        sendMessage(TownsRepository.getAnswer(this.getAgent().getLocalName(), messageText));
                    }
                }
                block();
            }
        });
    }

    public void sendMessage(String text) {
        AMSAgentDescription[] agents = null;
        try {
            SearchConstraints c = new SearchConstraints();
            c.setMaxResults(Long.valueOf(-1));
            agents = AMSService.search(this, new AMSAgentDescription(), c);
        } catch (Exception e) {
            System.out.println("Problem searching AMS: " + e);
            e.printStackTrace();
        }

        assert agents != null;
        for (AMSAgentDescription agent : agents) {
            AID agentID = agent.getName();
            if (agentID.getLocalName().equals("Player_1")) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(agentID);
                msg.setLanguage("English");
                msg.setContent(text);
                send(msg);
            }
        }
    }
}
