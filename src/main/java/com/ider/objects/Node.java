package com.ider.objects;

public class Node extends Position{

	public static enum NodeState
	{
		TO_VISIT(0), VISITED(1), EXIT(2);
		
		private int value;
		
		NodeState(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
		
	}
	
	private NodeState nodeState;
	public Node() {
		super();
	}

	public Node(int x, int y) {
		super(x, y);
	}
	
	public Node(Position position, NodeState nodeState)
	{
		super(position.getX(), position.getY());
		this.nodeState = nodeState;
	}

	public NodeState getNodeState() {
		return nodeState;
	}

	public void setNodeState(NodeState nodeState) {
		this.nodeState = nodeState;
	}

	@Override
	public String toString() {
		return "("+getX()+","+getY()+")"+nodeState;
	}
	
	
}
