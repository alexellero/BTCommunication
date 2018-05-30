package communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import pathfinding.AstarSearch;
import pathfinding.Graph;
import pathfinding.GraphTest;
import pathfinding.Node;
import programme.LifeSaving;
import utils.Direction;
import utils.N_uplet;

public class Main {

	public static void main(String[] args) throws NXTCommException, IOException {
		// TODO Auto-generated method stub
		GraphTest graph = new GraphTest ();
		Graph myGraph = graph.initializeGraph();
		BTInitiator initiator = new BTInitiator("Hydra","00:16:53:1C:24:E7");
		//BTInitiator initiator = new BTInitiator("NXT","00:16:53:1C:15:FC");
		initiator.graph =myGraph;
		initiator.position = graph.getNode('B');
		initiator.sensR.add(graph.getNode('A')) ;
		initiator.sensR.add(graph.getNode('E')) ;
		//initiator.setOther(initiator2);
		
		/*initiator2.graph = myGraph;
		initiator2.position = graph.getNode('D');
		initiator2.sensR.add(graph.getNode('A')) ;
		initiator2.sensR.add(graph.getNode('C')) ;
		initiator2.setOther(initiator);*/
		
		
		
		initiator.victimes.add(graph.getNode('C')) ;
		initiator.victimes.add(graph.getNode('J')) ;
		initiator.victimes.add(graph.getNode('F')) ;
		initiator.victimes.add(graph.getNode('E')) ;
		initiator.victimes.add(graph.getNode('D')) ;
		initiator.hopitaux.add(graph.getNode('K')) ;
		initiator.hopitaux.add(graph.getNode('I')) ;
		initiator.hopitaux.add(graph.getNode('A')) ;
		
		initiator.start();
		//initiator2.start();
		

				



			

				
				
		
		

	}

}
