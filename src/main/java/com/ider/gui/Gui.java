package com.ider.gui;

import java.awt.HeadlessException;

import javax.swing.JFrame;

import com.ider.Params;

public class Gui extends JFrame{

	private LabyrinthGui labyrinthGui;
	public Gui() throws HeadlessException {
		super();
		labyrinthGui = new LabyrinthGui();
		add(labyrinthGui);
		setSize(Params.cellSize*Params.HORIZENTAL, Params.cellSize * Params.VERTICAL);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public LabyrinthGui getLabyrinthGui() {
		return labyrinthGui;
	}

	 public static void main(String[] args)
	 {
		 Gui gui = new Gui();
		 gui.setVisible(true);
	 }
	
	
}