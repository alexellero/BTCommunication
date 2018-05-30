package pathfinding;

import utils.Direction;


public class Node implements Cloneable{

	/**
	 * The name of this node 
	 */
	private Character nodeName ;
	
	/**
	 * The distance assigned to this node
	 */
	private int g_score = 0;
	
	/**
	 * Value of the heuristic
	 */
	private int h_score = 0;
	
	/**
	 * Eval function
	 */
	
	private int f_score = 0 ;
		
	/**
	 * The predecessor of this node 
	 */
	private Node camFromNode = null ;
	
	private Direction dir  ;
	
	/**
	 * Construct a new node
	 */
	public Node(char nodeName, int g_score) {
		this.nodeName = nodeName ;
		this.g_score = g_score ;
	}
	
	public Object clone() {
	    Node node = null;
	    try {
	    	node = (Node) super.clone();
	    } catch(CloneNotSupportedException cnse) {
	      	cnse.printStackTrace(System.err);
	    }
	    if(node.camFromNode != null)
	    	node.camFromNode = (Node) camFromNode.clone();
	    
	    return node;
	}
	
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Node) {
			Node node = (Node)obj ;
			return nodeName.equals(node.getNodeName()) ;
		} 
		return false ;
	}
	
	public int hashCode() {
		return 31 * nodeName.hashCode() ;
	}


	public char getNodeName() {
		return nodeName;
	}

	public int getG_score() {
		return g_score;
	}

	public int getH_score() {
		return h_score;
	}
	
	

	public int getF_score() {
		return f_score;
	}


	public Node getCamFromNode() {
		return camFromNode;
	}

	public void setCamFromNode(Node camFromNode) {
		this.camFromNode = camFromNode;
	}
	
	public void calculF_score() {
		this.f_score = this.g_score + this.h_score ;
	}
	
	public void calculG_score(int old_g) {
		this.g_score +=  old_g ;
	}


	public Direction getDir() {
		return dir;
	}


	public void setDir(Direction dir) {
		this.dir = dir;
	}

	@Override
	public String toString() {
		return "Node [nodeName=" + nodeName + ", camFromNode=" + camFromNode + ", dir=" + dir + "]";
	}
	
	
	
	

}
