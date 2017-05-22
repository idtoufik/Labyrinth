package com.ider;

import com.ider.agents.AgentGui;
import com.ider.agents.CommunicationAgent;
import com.ider.agents.ExplorerAgent;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;


public class Labyrinth {
	public static void main(String[] args) {
		
		Runtime runtime = Runtime.instance();
		Profile config = new ProfileImpl("localhost", 8888, null);
		config.setParameter("gui", "true");
		AgentContainer mc = runtime.createMainContainer(config);
		AgentController ac;
		try {
			Object[] agentArguments = null;
			for(int i = 0 ; i < Params.NumberOfExplorerAgents ; i++)
			{
				ac = mc.createNewAgent(Params.ExplorerAgent + i, ExplorerAgent.class.getName(), agentArguments);
				ac.start();
			}
			ac = mc.createNewAgent(Params.CommunicationAgent, CommunicationAgent.class.getName(), agentArguments);
			ac.start();
			ac = mc.createNewAgent(Params.AgentGui, AgentGui.class.getName(), agentArguments);
			ac.start();
			
		} catch (StaleProxyException e) {
		}
	}
}
