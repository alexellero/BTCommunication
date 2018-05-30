package pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Direction;
import utils.N_uplet;

public class Graph {
	
	private HashMap<Node, ArrayList<Node>> graph ;
	
	private List<N_uplet> nodeDir ;
	
	public Graph() {
		graph = new HashMap<>() ;
		nodeDir = new ArrayList<>() ;
	}
	
	
	public void putNode(Node node, ArrayList<Node> neighbors) {
		graph.put(node, neighbors) ;
	}
	
	public void addNUplet(N_uplet nDir) {
		nodeDir.add(nDir) ;
	}
	
	public String toString() {
		String result = "";
		for (Map.Entry<Node, ArrayList<Node>> entry : graph.entrySet()) {
			Node curNode = entry.getKey() ;
			ArrayList<Node> curVal = entry.getValue() ;
			result += curNode.getNodeName() + " "+printListNode(curVal)+" "  ;
		}
		
		return "[ " + result +"]" ;
	}
	
	// print neighbors of node
	public String printListNode(List<Node> neighbors) {
		String result = "" ;
		if(neighbors == null)
			return "null" ;
		for (Node node : neighbors) {
			result += node.getNodeName() +" " ;
		}
		return "[ "+ result + "]" ;
	}


	public HashMap<Node, ArrayList<Node>> getGraph() {
		return graph;
	}
	
	public ArrayList<Node> getNeighbors(Node node) {
		ArrayList<Node> neigh = new ArrayList<>() ;
/*		System.out.println("getNeigh dnas graph node  : "+node.getNodeName());
		System.out.println("Graph getNeighbors : "+printListNode(graph.get(node)));*/
		if(this.graph.get(node) != null) {
			for (Node node2 : this.graph.get(node)) {
				neigh.add(new Node(node2.getNodeName(), node2.getG_score())) ;
			}
		}
		return neigh ;
	}
	
	public Direction searchDirOfNode(Node c, Node P) {
		
		for (N_uplet element : nodeDir) {
			
			if(element.dirEqualsNode(c, P))
				return element.getDirection() ;
		}
		return null ;
	}
	
	
	

}
