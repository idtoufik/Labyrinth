package com.ider;

public class Params {

	public static final int HORIZENTAL = 11;
	public static final int VERTICAL = 11;
	public static final int cellSize = 18;
	public static final int renderingTime = 10;
	public static final String AgentGui = "AgentGui";
	public static final String CommunicationAgent = "CommunicationAgent";
	public static final String ExplorerAgent = "ExplorerAgent";

	public static enum CellValues{
		NOTHING(0),
		WALL(1);
		
		private int value;
		
		CellValues(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
		
	}
}
