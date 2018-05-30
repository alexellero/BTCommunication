package pathfinding;

import java.util.ArrayList;
import java.util.Collection ;
import java.util.Iterator;
import java.util.List;

import utils.Direction;


public class AstarSearch {
	
	private Graph graph ;
	
	public AstarSearch(Graph graph) {
		this.graph = graph ;
	}
	public List<Node> findPath(Node start, Node goal, List<Node> sens) {
		
		ArrayList <Node> closedset = new ArrayList<Node>(); // The set of nodes already evaluated. Empty at start.     
		ArrayList <Node> openset = new ArrayList<Node>(); // The set of tentative nodes to be evaluated. 

		openset.add(start) ;
		// TODO : Don't forget heuristique
		
		
		while(!openset.isEmpty()) {
			
			List<Node> path = new ArrayList<>() ;
			Node node = getLowestCost(openset) ;
			
			if(node.equals(goal)) {
				
				//DEBUG
				//System.out.println("Goal Trouver");
				//System.out.println(node.getNodeName());
				path = getPath(node,sens) ;
				return path ;
			}
			
			
			openset.remove(node) ;
			closedset.add(node) ;
			
			//System.out.println(node.getNodeName());
			ArrayList<Node> neighbors = graph.getNeighbors(node) ;
			
			//System.out.println(graph.printListNode(neighbors));
			Iterator<Node> itNode = neighbors.iterator() ;
			
			while(itNode.hasNext()) {
				Node curNode = itNode.next() ;
				
				
				if(closedset.contains(curNode)) 
					continue ;
				
				curNode.setCamFromNode(node) ;
				if(sens.contains(curNode))
					curNode.calculG_score(0) ;
				else
					curNode.calculG_score(node.getG_score()) ;
				curNode.calculF_score() ;
				if(!openset.contains(curNode))
					openset.add(curNode) ;
				
				
			}
			
			//DEBUG
//			System.out.println(graph.printNeighbors(neighbors)) ;
			
			
			
			
			
		}
		
		return null ;
	}
	
	/**
	 * Finds the node within a set of neighbors with the least cost (potentially shortest distance to goal). 
	 * @return The node with the least cost.
	 */
	private static Node getLowestCost(Collection <Node> nodeSet) {
		Iterator <Node> nodeIterator = nodeSet.iterator();
		Node best = nodeIterator.next();
		while(nodeIterator.hasNext()) {
			Node cur = nodeIterator.next();
			if(cur.getF_score() < best.getF_score())
				best = cur;
		}
		return best;
	}
	
    private List<Node> getPath(Node currentNode, List<Node> sens) {
        List<Node> path = new ArrayList<Node>();
		path.add(0, currentNode);
        Node parent;
        Direction dir = null ;
        while ((parent = currentNode.getCamFromNode()) != null) {
        	if(!sens.contains(currentNode)) {
        		path.add(0, parent);
        	}
        	if (currentNode.getNodeName() == 'H' && parent.getNodeName() == 'G') {
        		if(sens.get(0).getNodeName() == 'I' || sens.get(1).getNodeName() == 'I')
        			dir = Direction.DROITE;
        		else
        			dir = Direction.GAUCHE;
        	}
        	else if (currentNode.getNodeName() == 'G' && parent.getNodeName() == 'H') {
        		if(sens.get(0).getNodeName() == 'E' || sens.get(1).getNodeName() == 'E')
        			dir = Direction.DROITE;
        		else
        			dir = Direction.GAUCHE;
        	}
        	else {
        		dir = graph.searchDirOfNode(currentNode, parent) ;
        	}
            
            currentNode.setDir(dir) ;
            currentNode = parent;
   //         System.out.println("Get Path : "+graph.printListNode(path));
        }
        return path;
    }
    
    public List<Direction> getListDir(List<Node> path) {
    	List<Direction> direction = new ArrayList<>() ;
		Iterator<Node> itNode = path.iterator() ;
		while(itNode.hasNext()) {
			Node curNode = itNode.next() ;
			direction.add(curNode.getDir()) ;
		}
		return direction ;

    	
    }



}
