package test;

import java.lang.InterruptedException;

import java.util.concurrent.TimeUnit;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
public class PongAgent extends Agent {
    private static final long serialVersionUID = 3663966406239393054L;
    protected void setup(){
        addBehaviour(new CyclicBehaviour(this) {
            private static final long serialVersionUID = -1912882200351395625L;
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    try {
                        String senderName = msg.getSender().getLocalName();
                        System.out.println(senderName);
                        System.out.println("PongAgent Начинается пауза ПОЛУЧЕНИЯ");
                        TimeUnit.SECONDS.sleep(20);
                        System.out.println("PongAgent Закончилась пауза ПОЛУЧЕНИЯ");
                    } catch (InterruptedException e) {

                    }
                }
                if (msg != null) {
                    // Вывод на экран локального имени агента и полученного сообщения
                    System.out.println(" – " + myAgent.getLocalName() + " received: " + msg.getContent());
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM); // устанавливаем перформатив сообщения
                    reply.setContent("Pong"); // содержимое сообщения
                    send(reply);// отправляем сообщения
                }
                block();
            }
        });
    }
}