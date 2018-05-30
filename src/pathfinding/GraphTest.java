package pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import utils.Direction;
import utils.N_uplet;

public class GraphTest {
	
	public List<Node> nodes = new LinkedList<>();
	
	public Graph initializeGraph () {
		Graph graph = new Graph() ;
		/*
		 * Node 
		 */
		Node A = new Node('A',15) ;
		Node B = new Node('B',10) ;
		Node C = new Node('C',5) ;
		Node D = new Node('D',25) ;
		Node E = new Node('E',7) ;
		Node F = new Node('F',7) ;
		Node G = new Node('G',10) ;
		Node H = new Node('H',10) ;
		Node I = new Node('I',5) ;
		Node J = new Node('J',7) ;
		Node K = new Node('K',5) ;	
		Node L = new Node('L',10) ;
		
		nodes.add(A);
		nodes.add(B);
		nodes.add(C);
		nodes.add(D);
		nodes.add(E);
		nodes.add(F);
		nodes.add(G);
		nodes.add(H);
		nodes.add(I);
		nodes.add(J);
		nodes.add(K);
		nodes.add(L);
		

		/*
		 * Neighbor of node A 
		 */
		ArrayList<Node> neighA = new ArrayList<>()  ;
		neighA.add(C) ;
		neighA.add(D) ;
		neighA.add(E) ;
		neighA.add(B) ;
		
		ArrayList<Node> neighB = new ArrayList<>() ;
		neighB.add(A) ;
		neighB.add(E) ;
		neighB.add(C) ;
		neighB.add(F) ;
		
		ArrayList<Node> neighC = new ArrayList<>() ;
		neighC.add(A) ;
		neighC.add(D) ;
		neighC.add(B) ;
		neighC.add(F) ;
		
		ArrayList<Node> neighD = new ArrayList<>() ;
		neighD.add(A) ;
		neighD.add(C) ;
		neighD.add(L) ;
		neighD.add(K) ;
		
		ArrayList<Node> neighE = new ArrayList<>() ;
		neighE.add(A) ;
		neighE.add(B) ;
		neighE.add(H) ;
		neighE.add(G) ;
		
		ArrayList<Node> neighF = new ArrayList<>() ;
		neighF.add(B) ;
		neighF.add(C) ;
		neighF.add(J) ;
		neighF.add(L) ;

		ArrayList<Node> neighG = new ArrayList<>() ;
		neighG.add(E) ;
		neighG.add(H) ;
		neighG.add(I) ;
		neighG.add(H) ;
		
		ArrayList<Node> neighH = new ArrayList<>() ;
		neighH.add(E) ;
		neighH.add(G) ;
		neighH.add(I) ;
		neighH.add(G) ;
		
		ArrayList<Node> neighI = new ArrayList<>() ;
		neighI.add(H) ;
		neighI.add(G) ;
		neighI.add(K) ;
		neighI.add(J) ;
		
		ArrayList<Node> neighJ = new ArrayList<>() ;
		neighJ.add(F) ;
		neighJ.add(L) ;
		neighJ.add(I) ;
		neighJ.add(K) ;
		
		ArrayList<Node> neighK = new ArrayList<>() ;
		neighK.add(I) ;
		neighK.add(J) ;
		neighK.add(L) ;
		neighK.add(D) ;

		ArrayList<Node> neighL = new ArrayList<>() ;
		neighL.add(F) ;
		neighL.add(J) ;
		neighL.add(K) ;
		neighL.add(D) ;
	

		

		
		/*
		 * Graph 
		 */

		graph.putNode(A, neighA) ;
		graph.putNode(B, neighB) ;
		graph.putNode(C, neighC) ;
		graph.putNode(D, neighD) ;
		graph.putNode(E, neighE) ;
		graph.putNode(F, neighF) ;
		graph.putNode(G, neighG) ;
		graph.putNode(H, neighH) ;
		graph.putNode(I, neighI) ;
		graph.putNode(J, neighJ) ;
		graph.putNode(K, neighK) ;
		graph.putNode(L, neighL) ;


		/*
		 *N_UPLET 
		 */
		N_uplet n1 = new N_uplet(A, B, Direction.DROITE) ;
		N_uplet n2 = new N_uplet(A, E, Direction.GAUCHE) ;
		N_uplet n3 = new N_uplet(A, D, Direction.DROITE) ;
		N_uplet n4 = new N_uplet(A, C, Direction.GAUCHE) ;

		
		N_uplet n5 = new N_uplet(B, A, Direction.GAUCHE) ;
		N_uplet n6 = new N_uplet(B, E, Direction.DROITE) ;	
		N_uplet n7 = new N_uplet(B, C, Direction.DROITE) ;
		N_uplet n8 = new N_uplet(B, F, Direction.GAUCHE) ;
		
		N_uplet n9 = new N_uplet(C, D, Direction.GAUCHE) ;
		N_uplet n10 = new N_uplet(C, A, Direction.DROITE) ;	
		N_uplet n11 = new N_uplet(C, B, Direction.GAUCHE) ;
		N_uplet n12 = new N_uplet(C, F, Direction.DROITE) ;
		
		N_uplet n13 = new N_uplet(D, A, Direction.GAUCHE) ;
		N_uplet n14 = new N_uplet(D, C, Direction.DROITE) ;
		N_uplet n15 = new N_uplet(D, K, Direction.DROITE) ;
		N_uplet n16 = new N_uplet(D, L, Direction.GAUCHE) ;
		
		N_uplet n17 = new N_uplet(E, A, Direction.DROITE) ;
		N_uplet n18 = new N_uplet(E, B, Direction.GAUCHE) ;
		N_uplet n19 = new N_uplet(E, H, Direction.GAUCHE) ;
		N_uplet n20 = new N_uplet(E, G, Direction.DROITE) ;
		
		N_uplet n21 = new N_uplet(F, C, Direction.GAUCHE) ;
		N_uplet n22 = new N_uplet(F, B, Direction.DROITE) ;
		N_uplet n23 = new N_uplet(F, J, Direction.GAUCHE) ;
		N_uplet n24 = new N_uplet(F, L, Direction.DROITE) ;
		
		N_uplet n25 = new N_uplet(G, E, Direction.GAUCHE) ;
		N_uplet n26 = new N_uplet(G, H, Direction.DROITE) ;
		N_uplet n27 = new N_uplet(G, H, Direction.DROITE);
		N_uplet n28 = new N_uplet(G, I, Direction.GAUCHE) ;
		
		N_uplet n29 = new N_uplet(H, E, Direction.DROITE) ;
		N_uplet n30 = new N_uplet(H, G, Direction.GAUCHE) ;
		N_uplet n31 = new N_uplet(H, G, Direction.DROITE) ;
		N_uplet n32 = new N_uplet(H, I, Direction.GAUCHE) ;
		
		N_uplet n33 = new N_uplet(I, H, Direction.DROITE) ;
		N_uplet n34 = new N_uplet(I, G, Direction.GAUCHE) ;
		N_uplet n35 = new N_uplet(I, J, Direction.DROITE) ;
		N_uplet n36 = new N_uplet(I, K, Direction.GAUCHE) ;

		N_uplet n37 = new N_uplet(J, F, Direction.DROITE) ;
		N_uplet n38 = new N_uplet(J, I, Direction.GAUCHE) ;
		N_uplet n39 = new N_uplet(J, K, Direction.DROITE) ;
		N_uplet n40 = new N_uplet(J, L, Direction.GAUCHE) ;
		
		N_uplet n41 = new N_uplet(K, I, Direction.DROITE) ;
		N_uplet n42 = new N_uplet(K, J, Direction.GAUCHE) ;
		N_uplet n43 = new N_uplet(K, L, Direction.DROITE) ;
		N_uplet n44 = new N_uplet(K, D, Direction.GAUCHE) ;
		
		N_uplet n45 = new N_uplet(L, J, Direction.DROITE) ;
		N_uplet n46 = new N_uplet(L, F, Direction.GAUCHE) ;
		N_uplet n47 = new N_uplet(L, D, Direction.DROITE) ;
		N_uplet n48 = new N_uplet(L, K, Direction.GAUCHE) ;
		
		/**
		 * N_uplet graph
		 */
		graph.addNUplet(n1) ;
		graph.addNUplet(n2) ;
		graph.addNUplet(n3) ;
		graph.addNUplet(n4) ;
		graph.addNUplet(n5) ;
		graph.addNUplet(n6) ;
		graph.addNUplet(n7) ;
		graph.addNUplet(n8) ;
		graph.addNUplet(n9) ;
		graph.addNUplet(n10) ;
		graph.addNUplet(n11) ;
		graph.addNUplet(n12) ;

		graph.addNUplet(n13) ;
		graph.addNUplet(n14) ;
		graph.addNUplet(n15) ;
		graph.addNUplet(n16) ;
		graph.addNUplet(n17) ;
		graph.addNUplet(n18) ;
		graph.addNUplet(n19) ;
		graph.addNUplet(n20) ;
		graph.addNUplet(n21) ;
		graph.addNUplet(n22) ;
		graph.addNUplet(n23) ;
		graph.addNUplet(n24) ;

		graph.addNUplet(n25) ;
		graph.addNUplet(n26) ;
		graph.addNUplet(n27) ;
		graph.addNUplet(n28) ;
		graph.addNUplet(n29) ;
		graph.addNUplet(n30) ;
		graph.addNUplet(n31) ;
		graph.addNUplet(n32) ;
		graph.addNUplet(n33) ;
		graph.addNUplet(n34) ;
		graph.addNUplet(n35) ;
		graph.addNUplet(n36) ;

		graph.addNUplet(n37) ;
		graph.addNUplet(n38) ;
		graph.addNUplet(n39) ;
		graph.addNUplet(n40) ;
		graph.addNUplet(n41) ;
		graph.addNUplet(n42) ;
		graph.addNUplet(n43) ;
		graph.addNUplet(n44) ;
		graph.addNUplet(n45) ;
		graph.addNUplet(n46) ;
		graph.addNUplet(n47) ;
		graph.addNUplet(n48) ;
		
		return graph;
	}
	
	public Node getNode (char name) {
		for (Node node : nodes) {
			if (node.getNodeName() == name)
				return node;
		}
		return null;
	}

}
