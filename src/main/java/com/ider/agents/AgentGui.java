package com.ider.agents;

import com.ider.Params;
import com.ider.gui.Gui;

import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class AgentGui  extends GuiAgent{

	private int x = 0,y = 0;
	private Gui gui;
	
	private void sendMessageToCommunicationAgent()
	{
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(new AID(Params.CommunicationAgent, AID.ISLOCALNAME));
		message.setContent("send me the positions");
		send(message);
	}
	@Override
	protected void setup() {
		gui = new Gui();
		
		addBehaviour(new TickerBehaviour(this, 1000) {
			
			@Override
			protected void onTick() {
				sendMessageToCommunicationAgent();
				x++;
				y++;
				gui.getLabyrinthGui().update(x,y);
				doWait();
				ACLMessage message = myAgent.receive();
				System.out.println(message.getContent());
				
			}
		});
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		
	}
	
	
	

}
