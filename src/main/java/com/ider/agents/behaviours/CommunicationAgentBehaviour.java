package com.ider.agents.behaviours;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.ider.Params;
import com.ider.agents.CommunicationAgent;
import com.ider.objects.Position;

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
		List<Position> agentPositions = new ArrayList<>(); 
		ObjectMapper objectMapper = new ObjectMapper();
		ACLMessage message = agent.blockingReceive();
		//System.out.println(message.getContent());
		for(int i = 0 ; i< Params.NumberOfExplorerAgents; i++)
		{
			agent.sendMessageToGetPosition(Params.ExplorerAgent + i);
			message = agent.blockingReceive();
			try {
				agentPositions.add(objectMapper.readValue(message.getContent(), Position.class));
			} catch (Exception e) {
				
				e.printStackTrace();
			} 
		}
		
		
		try {
			agent.sendMessage(Params.AgentGui, objectMapper.writeValueAsString(agentPositions));
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
