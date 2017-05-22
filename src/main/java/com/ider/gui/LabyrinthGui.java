package com.ider.gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import com.ider.Params;
import com.ider.objects.Node;
import com.ider.objects.Position;
import com.ider.services.LabyrinthWorldService;
import com.ider.agents.ExplorerAgent;
import com.ider.services.KnowledgeBase;
@SuppressWarnings("serial")
public class LabyrinthGui extends JPanel {
    List<Position> agentPositions ;
    private List<Color> agentColors = new ArrayList();
    private static int [][] labyrinth_world;
      
    public LabyrinthGui() {
		super();
		labyrinth_world = LabyrinthWorldService.read();
		agentPositions = new ArrayList<Position>();
		for(int i = 0; i < Params.NumberOfExplorerAgents; i++)
		{
			agentPositions.add(new Position(1,1));
		}
		
		agentColors.add(new Color(50,200, 200));
		agentColors.add(new Color(0,200, 0));
		agentColors.add(new Color(200,0, 0));
		agentColors.add(new Color(0,0, 0));
		agentColors.add(new Color(0,0, 200));
	}

	@Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
        for(int i = 0; i < Params.HORIZENTAL; i++)
        {
        	for(int j = 0; j < Params.VERTICAL; j++)
        	{
        		if(labyrinth_world[i][j] == Params.CellValues.WALL.getValue())
        			g2d.fillRect(Params.cellSize * j, Params.cellSize*i, Params.cellSize, Params.cellSize);
        	}
        }
        
        for(int i = 0; i < Params.HORIZENTAL; i++)
        {
        	for(int j = 0; j < Params.VERTICAL; j++)
        	{
        		
        		g2d.fillRect(Params.cellSize * j + Params.cellSize*Params.HORIZENTAL +20, 
        				Params.cellSize*i, Params.cellSize, Params.cellSize);
        	}
        }
        
        Set<Node> nodes = ExplorerAgent.getKnowledgeBase().getNodes();
        
        for(Node node : nodes)
        {
        	if(node.getNodeState().equals(Node.NodeState.VISITED))
        		g2d.setColor(new Color(0,0,0));
        	else if(node.getNodeState().equals(Node.NodeState.TO_VISIT))
        		g2d.setColor(new Color(150, 0, 0));
        	
        	g2d.fillRect(node.getX()*Params.cellSize + Params.cellSize*Params.HORIZENTAL +20,
        			node.getY() * Params.cellSize, Params.cellSize, Params.cellSize);
        }
        
        
        
        
        int i = 0;
        for(Position agentPosition : agentPositions)
        {
        	if( i > agentColors.size()-1)
        	{
        		g2d.setColor(new Color(0,0,255));
        	}
        	else
        	{
        		g2d.setColor(agentColors.get(i));
        	}
        	g2d.fillRect(agentPosition.getX()*Params.cellSize, agentPosition.getY()*Params.cellSize, Params.cellSize, Params.cellSize);
        	g2d.setColor(new Color(255, 255, 255));
        	g2d.fillRect(agentPosition.getX()*Params.cellSize + Params.cellSize*Params.HORIZENTAL +20
        			, agentPosition.getY()*Params.cellSize, Params.cellSize, Params.cellSize);
        	i++;
        }
        
        
    }
	
   
	
	public void update(List<Position> positions)
	{
		agentPositions = positions;
		repaint();
	}
}