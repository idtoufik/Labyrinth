package com.ider.agents.behaviours;

import com.ider.agents.ExplorerAgent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;;

@SuppressWarnings("serial")
public class ExplorerAgentComunicationBehaviour extends CyclicBehaviour{

	private ExplorerAgent agent;
	
	
	public ExplorerAgentComunicationBehaviour(ExplorerAgent agent) {
		super(agent);
		this.agent = agent;
	}


	@Override
	public void action() {
		block();
		ACLMessage message = agent.receive();
		if(message != null)
		{
			//System.out.println(message.getContent());
			agent.sendPosition();
			
		}
		else
		{
			System.out.println("boucle");
		}
		
		
		
	}

}
