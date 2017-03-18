package com.ider.agents.behaviours;

import com.ider.Params;
import com.ider.agents.CommunicationAgent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class CommunicationAgentBehaviour extends CyclicBehaviour{

	private CommunicationAgent agent;
	
	
	public CommunicationAgentBehaviour(CommunicationAgent agent) {
		super();
		this.agent = agent;
	}


	@Override
	public void action() {
		agent.doWait();
		ACLMessage message = agent.receive();
		System.out.println(message.getContent());
		agent.sendMessage(Params.AgentGui, "those are the positions");
	}

}
