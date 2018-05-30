package utils;

import pathfinding.Node;

public class N_uplet {
	
	private Node currentNode ;
	private Node ParentNode ;
	private Direction direction ;
	
	public N_uplet(Node currentNode, Node parentNode, Direction direction) {
		this.currentNode = currentNode;
		this.ParentNode = parentNode;
		this.direction = direction;
	}
	
	public boolean dirEqualsNode(Node c, Node P) {
		if(currentNode.equals(c) && ParentNode.equals(P))
			return true ;
		return false ;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public Node getParentNode() {
		return ParentNode;
	}

	public Direction getDirection() {
		return direction;
	}
	
	

}
