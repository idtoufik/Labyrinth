package com.ider.agents;

import com.ider.Params;
import com.ider.agents.behaviours.CommunicationAgentBehaviour;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class CommunicationAgent extends Agent{
	
	@Override
	protected void setup() {
		
		addBehaviour(new CommunicationAgentBehaviour(this));
	}
	
	public void sendMessage(String Aid, String msg)
	{
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(new AID(Aid, AID.ISLOCALNAME));
		message.setContent(msg);
		send(message);
	}
}
