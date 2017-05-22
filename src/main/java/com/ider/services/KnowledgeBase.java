package com.ider.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.BidirectionalDijkstraShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.ider.Params;
import com.ider.Params.CellValues;
import com.ider.agents.ExplorerAgent;
import com.ider.agents.ExplorerAgent.Action;
import com.ider.objects.Node;
import com.ider.objects.Node.NodeState;
import com.ider.objects.Position;

public class KnowledgeBase {

	private SimpleWeightedGraph<Node, DefaultWeightedEdge> graph;
	private Params.CellValues [][] labyrinthWorld = LabyrinthWorldService.getLabyritnWorld(); 
	private HashMap<Position, Integer> wayChooser = new HashMap<Position, Integer>();
	
	public KnowledgeBase() {
		
		graph = new SimpleWeightedGraph<Node, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		
	}
	
	
	public synchronized void  iAmHere(Position position)
	{
		Node actual = findVertex(position);
		if(actual == null)
		{
			 actual = new Node(position, Node.NodeState.VISITED); 
			 graph.addVertex(actual);
		}
		else
		{
			if(actual.getNodeState().equals(NodeState.TO_VISIT))
			actual.setNodeState(NodeState.VISITED);
		}
		
		createIfNotFound(actual);
		
	}
	
	public Action getExitingAction(Position position)
	{
		
		if(getCellValue(position.getRightPosition()).equals(CellValues.EXIT))
		{
			return Action.goRight;
		}
		if(getCellValue(position.getUpPosition()).equals(CellValues.EXIT))
		{
			return Action.goUp;
		}
		if(getCellValue(position.getLeftPosition()).equals(CellValues.EXIT))
		{
			return Action.goLeft;
		}
		if(getCellValue(position.getDownPosition()).equals(CellValues.EXIT))
		{
			return Action.goDown;
		}
		return null;
	}
	
	public boolean iAmOut(Position position)
	{
		return getCellValue(position).equals(CellValues.EXIT);
	}
	
	public List<ExplorerAgent.Action> getExploringActions(Position position)
	{
		List<ExplorerAgent.Action> actionList = new ArrayList<ExplorerAgent.Action>();
		graph.vertexSet().stream()
		.filter(t -> (position.isNeighbor(t)) && t.getNodeState().equals(NodeState.TO_VISIT))
		.forEach(t -> actionList.add(Action(position, t)));
		
		return actionList;
	}
	
	public Node findVertex(Position position)
	{
		Node node = null;
		try
		{
			node =  graph.vertexSet().stream().filter(t -> t.equals(position)).findFirst().get();
		}
		catch(Exception e)
		{
			
		}
		
		return node;
	}
	
	private CellValues getCellValue(Position position)
	{
		if(position.getX()< Params.HORIZENTAL && position.getY() < Params.VERTICAL)
			return labyrinthWorld[position.getY()][position.getX()];
		return labyrinthWorld[1][1];
	}
	
	private synchronized void createIfNotFound(Position source)
	{
		Position position = source.getRightPosition();
		Node node = findVertex(position);
		if(node == null)
		{
			CellValues cellValue = getCellValue(position);
			if(cellValue == CellValues.NOTHING || cellValue == CellValues.EXIT)
			{
				Node newNode = new Node(position, cellValue == CellValues.NOTHING ? NodeState.TO_VISIT : NodeState.EXIT);
				graph.addVertex(newNode);
				graph.addEdge(findVertex(source), newNode);
			}
		}
		else if(node.getNodeState().equals(NodeState.TO_VISIT))
		{
			graph.addEdge(findVertex(source), node);
		}
		
		position = source.getUpPosition();
		node = findVertex(position);
		if(node == null)
		{
			CellValues cellValue = getCellValue(position);
			if(cellValue == CellValues.NOTHING || cellValue == CellValues.EXIT)
			{
				Node newNode = new Node(position, cellValue == CellValues.NOTHING ? NodeState.TO_VISIT : NodeState.EXIT);
				graph.addVertex(newNode);
				graph.addEdge(findVertex(source), newNode);
			}
		}
		else if(node.getNodeState().equals(NodeState.TO_VISIT))
		{
			graph.addEdge(findVertex(source), node);
		}
		
		position = source.getLeftPosition();
		node = findVertex(position);
		if(node == null)
		{
			CellValues cellValue = getCellValue(position);
			if(cellValue == CellValues.NOTHING || cellValue == CellValues.EXIT)
			{
				Node newNode = new Node(position, cellValue == CellValues.NOTHING ? NodeState.TO_VISIT : NodeState.EXIT);
				graph.addVertex(newNode);
				graph.addEdge(findVertex(source), newNode);
			}
		}
		else if(node.getNodeState().equals(NodeState.TO_VISIT))
		{
			graph.addEdge(findVertex(source), node);
		}
		
		position = source.getDownPosition();
		node = findVertex(position);
		if(node == null)
		{
			CellValues cellValue = getCellValue(position);
			if(cellValue == CellValues.NOTHING || cellValue == CellValues.EXIT)
			{
				Node newNode = new Node(position, cellValue == CellValues.NOTHING ? NodeState.TO_VISIT : NodeState.EXIT);
				graph.addVertex(newNode);
				graph.addEdge(findVertex(source), newNode);
			}
		}
		else if(node.getNodeState().equals(NodeState.TO_VISIT))
		{
			graph.addEdge(findVertex(source), node);
		}
		
		
	}
	
	
	public List<Node> getListOfUnexploredNodesSortedByPathLength(Position position)
	{
		return
		graph.vertexSet().stream().filter(t -> t.getNodeState().equals(NodeState.TO_VISIT)).sorted(new Comparator<Node>() {

			@Override
			public int compare(Node node1, Node node2) {
				
				Node source = findVertex(position);
				DijkstraShortestPath<Node, DefaultWeightedEdge> dp =
						new DijkstraShortestPath<Node, DefaultWeightedEdge>(graph);
				int length1 = dp.getPath(source, node1).getLength();
				int length2 = dp.getPath(source, node2).getLength();
				return Long.compare(length1, length2);
			}
			
		}).collect(Collectors.toList());
	}
	
	public synchronized Node getTheNearestNode(Position source)
	{
		Node sourceNode = findVertex(source);
		Node nearestNode = null;
		int minPathLength;
		System.out.println("source node " + sourceNode);
		List<Node> toVisit = graph.vertexSet().stream().filter(t -> t.getNodeState().equals(NodeState.TO_VISIT)).collect(Collectors.toList());
		toVisit.forEach(t ->{
			BidirectionalDijkstraShortestPath<Node, DefaultWeightedEdge> dp =
					new BidirectionalDijkstraShortestPath<Node, DefaultWeightedEdge>(graph);
			System.out.println("pathLength : "+t+" " + dp.getPathWeight(sourceNode, t));
			System.out.println("pathLength : "+t+" " + dp.getPathWeight(t,sourceNode));
		});
		if(!toVisit.isEmpty())
		{
			nearestNode = toVisit.get(0);
			toVisit.remove(0);
			BidirectionalDijkstraShortestPath<Node, DefaultWeightedEdge> dp =
					new BidirectionalDijkstraShortestPath<Node, DefaultWeightedEdge>(graph);
			minPathLength = dp.getPath(sourceNode, nearestNode).getLength();
			int pathLength;
			for(Node node : toVisit)
			{
				pathLength = dp.getPath(sourceNode, node).getLength();
				if(minPathLength > pathLength)
				{
					nearestNode = node;
					minPathLength = pathLength;
				}
			}
		}
		return nearestNode;
		
	}
	
	
	public synchronized List<Action> actionsToGoTo(Position source, Position destination)
	{
		Node sourceNode = findVertex(source);
		Node destinationNode = findVertex(destination);
		List<Action> actionList = new ArrayList<Action>();
		DijkstraShortestPath<Node, DefaultWeightedEdge> dp = new DijkstraShortestPath<Node, DefaultWeightedEdge>(graph);
		List<Node> path = dp.getPath(sourceNode, destinationNode).getVertexList();
		Node tempNode = path.get(0);
		path.remove(0);
		for(Node node : path)
		{
			actionList.add(Action(tempNode, node));
			tempNode = node;
		}
		return actionList;
	}
	
	public synchronized  Action getMyOwnExploringAction(Position position)
	{
		List<Action> exploringActions = getExploringActions(position);
		if(exploringActions.isEmpty())
		{
			return null;
		}
		else
		{
			
			Node temp = findVertex(position);
			Integer i = wayChooser.get(temp);
			if(i == null)
			{
				i = 0;
			}
			else
			{
				i++;
			}
			wayChooser.put(temp,i);
			i = i % exploringActions.size();
			Action exploringAction = exploringActions.get(i);
			
			/*int neighborsNumber = Graphs.neighborListOf(graph, findVertex(position))
					.size();
			System.out.println("number of neighbors");
			Position destination = getPositionAfterAction(position, exploringAction);
			DefaultWeightedEdge e = graph.getEdge(findVertex(position), findVertex(destination));
			graph.setEdgeWeight(e, Math.pow(10, neighborsNumber-1));
			System.out.println("weight"+graph.getEdgeWeight(e));*/
			return exploringAction;
		}
	}
	public Action Action(Position source, Position destination)
	{
		if(source.getRightPosition().equals(destination))
		{
			return Action.goRight;
		}
		else if(source.getUpPosition().equals(destination))
		{
			return Action.goUp;
		}
		else if(source.getLeftPosition().equals(destination))
		{
			return Action.goLeft;
		}
		else if(source.getDownPosition().equals(destination))
		{
			return Action.goDown;
		}
		
		return null;
	}
	
	public Position getTheExit()
	{
		return graph.vertexSet().stream().filter(t->t.getNodeState().equals(Node.NodeState.EXIT)).findFirst().get();
	}
	
	public Position getPositionAfterAction(Position initial, Action action)
	{
		Position position = new Position(initial);
		if (action.equals(Action.goUp))
		{
			position.goUp();
		}
		else if (action.equals(Action.goRight))
		{
			position.goRight();
		}
		else if (action.equals(Action.goDown))
		{
			position.goDown();
		}
		else if (action.equals(Action.goLeft))
		{
			position.goLeft();
		}
		
		return position;
	}

	
	
	

}
