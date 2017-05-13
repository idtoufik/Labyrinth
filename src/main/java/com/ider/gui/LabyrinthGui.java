package com.ider.gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.ider.Params;
import com.ider.objects.Position;
import com.ider.services.LabyrinthWorldService;

@SuppressWarnings("serial")
public class LabyrinthGui extends JPanel {
    int x = 0;
    int y = 0;
    Position agentPosition = new Position(1,1);
    private static int [][] labyrinth_world;
    
    
    public LabyrinthGui() {
		super();
		labyrinth_world = LabyrinthWorldService.read();
		
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
        
        g2d.setColor(new Color(50,200, 200));
        g2d.fillRect(agentPosition.getX()*Params.cellSize, agentPosition.getY()*Params.cellSize, Params.cellSize, Params.cellSize);
        
    }
	
	public void update(int x , int y)
	{
		this.x = x * Params.cellSize;
		this.y = y * Params.cellSize;
		repaint();
	}

	public void update(Position position)
	{
		agentPosition = position;
		repaint();
	}
   
}