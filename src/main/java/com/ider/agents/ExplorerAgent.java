package com.ider.agents;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.ider.Params;
import com.ider.agents.behaviours.ExplorerAgentComunicationBehaviour;
import com.ider.agents.behaviours.ExplorerAgentExploringBehaviour;
import com.ider.objects.Position;
import com.ider.services.KnowledgeBase;

import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;


@SuppressWarnings("serial")
public class ExplorerAgent extends Agent{
	private static KnowledgeBase knowledgeBase = new KnowledgeBase();
	private Position position = new Position(1,1);
	@Override
	protected void setup() {
		ParallelBehaviour pb = new ParallelBehaviour(ParallelBehaviour.WHEN_ANY);
		pb.addSubBehaviour(new ExplorerAgentComunicationBehaviour(this));
		pb.addSubBehaviour(new ExplorerAgentExploringBehaviour(this));
		addBehaviour(pb);
	}
	
	public void sendPosition()
	{
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(new AID(Params.CommunicationAgent, AID.ISLOCALNAME));
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPos = "";
		
		try {
			jsonPos = objectMapper.writeValueAsString(position);
		} catch (JsonGenerationException e) {
			
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		message.setContent(jsonPos);
		send(message);
	}

	public void goUp()
	{
		position.goUp();
	}
	
	public void goDown()
	{
		position.goDown();
	}
	
	public void goLeft()
	{
		position.goLeft();
	}
	
	public void goRight()
	{
		position.goRight();
	}
	
	public void performAction(Action action)
	{
		if(action.equals(Action.goRight))
		{
			goRight();
		}
		else if(action.equals(Action.goUp))
		{
			goUp();
		}
		else if(action.equals(Action.goLeft))
		{
			goLeft();
		}
		else if(action.equals(Action.goDown))
		{
			goDown();
		}
	}
	public Position getPosition() {
		return position;
	}
	
	
	
	public static KnowledgeBase getKnowledgeBase() {
		return knowledgeBase;
	}


	
	public static enum Action{
		goRight(0),
		goUp(1),
		goLeft(2),
		goDown(3);
		
		
		
		private int value;
		
		Action(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
		
		
		
	}
	
	
}
