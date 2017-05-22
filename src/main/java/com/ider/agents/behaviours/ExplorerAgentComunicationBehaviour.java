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
			
			sendPosition(message);
			ExitFoundByOthers(message);
			
			
		}
		else
		{
			System.out.println("boucle");
		}
		
		
		
	}
	
	private void sendPosition(ACLMessage message)
	{
		if(message.getOntology() == null)
			return;
		if(!message.getOntology().equals("position") 
				|| message.getPerformative() != ACLMessage.REQUEST)
			return;
				
		agent.sendPosition();
	}
	
	private void ExitFoundByOthers(ACLMessage message)
	{
		if(message.getOntology() == null)
			return;
		
		if(message.getPerformative() != ACLMessage.INFORM)
			return;
		
		if(!message.getOntology().equals("exitFound"))
			return;
		System.out.println("exitFoundByOthers");
		agent.setExitFound(true);
		
	}

}
