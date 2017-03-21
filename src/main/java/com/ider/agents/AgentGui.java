package com.ider.agents;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

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

	private int x = 1,y = 1;
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
				Position position = new Position();
				
				try {
					position = objectMapper.readValue(message.getContent(), Position.class);
				} catch (JsonParseException e) {
					
					//e.printStackTrace();
				} catch (JsonMappingException e) {
					
					//e.printStackTrace();
				} catch (IOException e) {
					
					//e.printStackTrace();
				}
				gui.getLabyrinthGui().update(position.getX(), position.getY());
				
			}
		});
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		
	}
	
	
	

}
