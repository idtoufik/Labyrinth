package com.ider.agents.behaviours;

import java.util.ArrayList;
import java.util.List;

import com.ider.Params;
import com.ider.agents.ExplorerAgent;
import com.ider.objects.Node;
import com.ider.objects.Position;
import com.ider.services.KnowledgeBase;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;

@SuppressWarnings("serial")
public class ExplorerAgentExploringBehaviour extends FSMBehaviour{

	private ExplorerAgent agent;
	private ExplorerAgent.Action action = null;
	private List<ExplorerAgent.Action> exitingActionList = new ArrayList<ExplorerAgent.Action>();
	private static final String Reflexion = "Reflexion";
	private static final String ModeExecutif = "ModeExecutif";
	private static final String ModeExploratoire = "ModeExploratoire";
	private static final String End = "End";
	


	public ExplorerAgentExploringBehaviour(ExplorerAgent agent) {
		super(agent);
		this.agent = agent; 
		registerFirstState(new OneShotBehaviour() {
			private int exitStatus = 0;
			
			@Override
			public void action() {
				System.out.println("reflexion");
				KnowledgeBase knowledgeBase = ExplorerAgent.getKnowledgeBase();
				knowledgeBase.iAmHere(agent.getPosition());
				if(knowledgeBase.iAmOut(agent.getPosition()))
				{
					block();
					return;
				}
				ExplorerAgent.Action exitAction = knowledgeBase.getExitingAction(agent.getPosition());
				
				if(exitAction != null)
				{
					action = exitAction;
					exitStatus = 0;
					return;
				}
						
				List<ExplorerAgent.Action> actions = knowledgeBase.getExploringActions(agent.getPosition());
				
				if(!actions.isEmpty())
				{
					action = actions.get(0);
					exitStatus = 0;
				}
				else
				{
					System.out.println("agent Position: " + agent.getPosition());
					Node nearestNode= knowledgeBase.getTheNearestNode(agent.getPosition());
					
					if(nearestNode != null)
					{
						System.out.println("nearest Node"+nearestNode);
						exitingActionList = knowledgeBase.actionsToGoTo(agent.getPosition(), nearestNode);
						exitingActionList.forEach(t -> System.out.println(t));
						
						exitStatus = 1;
						
					}
				}
			}

			@Override
			public int onEnd() {
				
				return exitStatus;
			}
			
			
			
			
		}, Reflexion);
		
		registerState(new WakerBehaviour(agent, Params.renderingTime) {

			@Override
			protected void onWake() {
				//System.out.println("*************************wake******************");
				if(action != null)
				{
					agent.performAction(action);
					//System.out.println(agent.getPosition().getX() + "  " +  agent.getPosition().getY());
				}
				
				action = null;
			}

			@Override
			public int onEnd() {
				reset(Params.renderingTime);
				return super.onEnd();
			}
			
			
			
		}, ModeExploratoire);
		
		
		registerState(new WakerBehaviour(myAgent, Params.renderingTime) {
			@Override
			protected void onWake() {
				if(!exitingActionList.isEmpty())
				{
					agent.performAction(exitingActionList.get(0));
					exitingActionList.remove(0);
				}
			}

			@Override
			public int onEnd() {
				reset(Params.renderingTime);
				super.onEnd();
				return (exitingActionList.isEmpty()) ? 1 : 0;
			}
			
		}, ModeExecutif);
		
		registerTransition(Reflexion, ModeExploratoire,0);
		registerDefaultTransition(ModeExploratoire, Reflexion);
		registerTransition(Reflexion, ModeExecutif, 1);
		registerTransition(ModeExecutif, ModeExecutif, 0);
		registerTransition(ModeExecutif, Reflexion, 1);
		
		
	}
}
