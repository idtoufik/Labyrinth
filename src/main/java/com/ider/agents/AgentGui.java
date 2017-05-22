package com.ider.agents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.ider.Params;
import com.ider.gui.Gui;
import com.ider.objects.Position;

import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class AgentGui  extends GuiAgent{

	private Gui gui;
	
	private void sendMessageToCommunicationAgent()
	{
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(new AID(Params.CommunicationAgent, AID.ISLOCALNAME));
		//message.setContent("send me the positions");
		send(message);
	}
	@Override
	protected void setup() {
		gui = new Gui();
		
		addBehaviour(new TickerBehaviour(this, Params.renderingTime) {
			
			@Override
			protected void onTick() {
				sendMessageToCommunicationAgent();
				ACLMessage message = myAgent.blockingReceive();
				//System.out.println(message.getContent());
				ObjectMapper objectMapper = new ObjectMapper();
				List<Position> positions = new ArrayList<>();
				
				try {
					positions = objectMapper.readValue(message.getContent(), new TypeReference<List<Position>>() {});
				} catch (Exception e) {
					
					e.printStackTrace();
				} 
				gui.getLabyrinthGui().update(positions);
				
			}
		});
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		
	}
	
	
	

}
