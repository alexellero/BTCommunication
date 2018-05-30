package programme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;

import communication.BTInitiator;

import pathfinding.AstarSearch;
import pathfinding.Graph;
import pathfinding.Node;
//import robotSuiveurLigne.RobotSuiveurLigne;
import utils.Direction;

public class LifeSaving {
	
	//private RobotSuiveurLigne theRobot ;
	private List<Node> sensRobot ;
	private static List<Node> victimes ;
	private List<Node> hopitaux ;
	private int nbVictimSave ;
	private boolean demiTour = false ;

	
	public LifeSaving( List<Node> sensR, List<Node> victimes, List<Node> hopitaux) {
	//	this.theRobot = robot ;
		this.sensRobot = sensR ;
		this.victimes = victimes ;
		this.hopitaux = hopitaux ;
		this.nbVictimSave = 0 ;
	}
	
	public List<Node> getVictimes() {
		return victimes;
	}

	public int getNbVictimSave() {
		return nbVictimSave;
	}

	public List<Node> saveLives(Graph graph, Node start) throws NXTCommException, IOException {	
		AstarSearch astarAlgo = new AstarSearch(graph) ; 
		List<Node> path = new ArrayList<>() ;
		List<Node> nodeCost = new ArrayList<>() ;
		Node goal, bestGoal, goalV, goalH ;
		System.out.println("sens : " + this.sensRobot);
		
		//Départ à la victime la plus proche
		if (nbVictimSave == 0) {
			start = updateStart(graph, start) ;
			goal = closestNode(graph, start, victimes) ;
			path = astarAlgo.findPath(start, goal, this.sensRobot) ;
			//DEBUG
			System.out.println("Depart : "+start.getNodeName());
			System.out.println("Goal : "+goal.getNodeName());
			victimes.remove(goal) ;
			nbVictimSave++;
			
		}
		else if (victimes.isEmpty()) {
			start = updateStart(graph, start) ;
			goal = closestNode(graph, start, hopitaux) ;
			path = goToHospital(graph, astarAlgo, goal,start);
		}
		

		
		//Iterator<Node> nodeIter = victimes.iterator() ;  
		//while(nodeIter.hasNext()) {
		
		else if(nbVictimSave == 1 && victimes.size() == 1) {
			//Mis à jour de la position de départ
			start = updateStart(graph, start) ;
			//Recherche de la victime la plus proche
			goalV = closestNode(graph, start, victimes) ;
			
			path = goToVictim(graph, astarAlgo, goalV,start) ;
		}
		
		else if(nbVictimSave < 2) {
				//Mis à jour de la position de départ
				start = updateStart(graph, start) ;
				//Recherche de la victime la plus proche
				goalV = closestNode(graph, start, victimes) ;
				//Recherche de l'hopital le plus proche
				goalH = closestNode(graph, start, hopitaux) ;
				
				nodeCost.add(goalV) ;
				nodeCost.add(goalH) ;
				//DEBUG
				//System.out.println("NodeCost : "+graph.printListNode(nodeCost));
				
				bestGoal = getBestCost(nodeCost) ;
				nodeCost.remove(goalV) ;
				nodeCost.remove(goalH) ;
				//System.out.println("Best Goal : "+bestGoal.getNodeName());

				
				if(hopitaux.contains(bestGoal)) {
					path = goToHospital(graph, astarAlgo, bestGoal,start) ;
					//path = goToVictim(graph, astarAlgo, goal) ;
					//goal = path.get(path.size()-1);
				}else {
					path = goToVictim(graph, astarAlgo, bestGoal,start) ;
				}

			}
		else {
				start = updateStart(graph, start) ;
				goal = closestNode(graph, start, hopitaux) ;
				path = goToHospital(graph, astarAlgo, goal,start);
				//path = goToVictim(graph, astarAlgo, goal) ;
				//goal = path.get(path.size()-1);

			}
			
		
		//}
		
		return path;

	}

	// Le robot se deplace vers la victime
	private List<Node> goToVictim(Graph graph, AstarSearch astarAlgo, Node goal, Node start)
			throws IOException {
		//Node start;
		List<Node> path;
		//Mis à jour de la position de départ
		//start = updateStart(graph, goal) ;
		//Recherche de la victime la plus proche
		//goal = closestNode(graph, start, victimes) ;
		path = astarAlgo.findPath(start, goal, this.sensRobot) ;
		//DEBUG
		System.out.println("*******Go To Victim******");
		System.out.println("Depart : "+start.getNodeName());
		System.out.println("Goal : "+goal.getNodeName());

		victimes.remove(goal) ;
		nbVictimSave++ ;
		
		//DEBUG
		System.out.println("NB Victim Save : "+nbVictimSave);
		
		//sendOrderToRobot(graph, path);
		
		return path;
	}

	//Le robot ammène la victime à l'hopital
	private List<Node> goToHospital(Graph graph, AstarSearch astarAlgo, Node goal, Node start)
			throws IOException {
		//Node start;
		List<Node> path;
		//Mis à jour de la position de départ
		//start = updateStart(graph, goal) ;
		//System.out.println("start : "+start);
		
		//Recherche de l'hopital le plus proche
		//goal = closestNode(graph, start, hopitaux) ;
		path = astarAlgo.findPath(start, goal, this.sensRobot) ;

		//DEBUG
		System.out.println("*******Go To Hospital******");
		System.out.println("Start : "+start.getNodeName());
		System.out.println("Goal : "+goal.getNodeName());
		
		nbVictimSave = 0 ;
		
		//DEBUG
		System.out.println("NB Victim Save : "+nbVictimSave);

		//On ammène la victime à l'hopital
		//sendOrderToRobot(graph, path) ;
		
		return path;
	}

	//Envoie des commandes au robot 
	public List<Direction> sendOrderToRobot(Graph graph, List<Node> path)
			throws IOException {
		List<Direction> directions = new ArrayList<>() ;
		System.out.println("+++++++++++++++++++++++++++++++");
		System.out.println("Chemin le plus court : ");
		System.out.println(graph.printListNode(path));
		System.out.println("+++++++++++++++++++++++++++++++");
		
		for (Node node : path) {
			updateSens(graph,node/*,btInit*/) ;
			System.out.println("Node name : "+node.getNodeName()
					+"  node dir : "+node.getDir());
			if(!this.demiTour) {
				//this.theRobot.turnRigth() ;	
				//this.theRobot.linefollower() ;
/*				btInit.getDos().writeChar('d') ;
				btInit.getDos().flush() ;*/
			//	btInit.sendDirectionBis(node.getDir());
				directions.add(node.getDir()) ;
			} else {
				//this.theRobot.turnLeft() ;
				//this.theRobot.linefollower() ;
/*				btInit.getDos().writeChar('q') ;
				btInit.getDos().flush() ;*/
			//	btInit.sendDirectionBis(Direction.DEMITOUR);
				directions.add(Direction.DEMITOUR) ;
				this.demiTour = false ;

			}
			
			//DEBUG
			System.out.println("Orientation du robot : ");
			System.out.println(graph.printListNode(this.sensRobot));
			
		}
		System.out.println(directions.toString());
		//btInit.sendDirection(directions);
		
		return directions;
	}
	
	
	
	// Retourne le noeud le plus proche
	private Node closestNode(Graph graph, Node start, List<Node> victOrH) {
		AstarSearch astarAlgo = new AstarSearch(graph) ;
		List<Node> path = new ArrayList<>() ;
		List<Node> nodeCout = new ArrayList<>() ;
		Node bestNode ;


		for (Node node : victOrH) {
			
			path = astarAlgo.findPath(start, node, this.sensRobot) ;
			Node newNode = path.get(path.size() - 1) ;
			nodeCout.add(newNode) ;
			
		}
		
		bestNode = getBestCost(nodeCout) ;
		
		return bestNode ;
	}
	
	//Retourne le neoud qui a le meilleur coût
	private Node getBestCost(List<Node> nodeCout) {
		Iterator <Node> nodeIterator = nodeCout.iterator();
		Node best = nodeIterator.next();
		while(nodeIterator.hasNext()) {
			Node cur = nodeIterator.next();
			if(cur.getG_score() < best.getG_score())
				best = cur;
		}
		return best;
	}

	
	private Node updateStart(Graph graph, Node start) {
		ArrayList<Node> neigh = graph.getNeighbors(this.sensRobot.get(0)) ;
		for (Node node : neigh) {
			if(start.equals(node))
				return node ;
		}
		return null ;
	}
	
	private void updateSens(Graph graph, Node curNode/*, BTInitiator btInit*/) throws IOException {
		
		List<Node> curNodeNeighbors = new ArrayList<>() ;
		
		if(!this.sensRobot.contains(curNode)) {
			//theRobot.turnAround() ;
			//theRobot.linefollower() ;
/*			btInit.getDos().writeChar('e') ;
			btInit.getDos().flush() ;*/
			this.demiTour = true ;
			
		}
		

		curNodeNeighbors = graph.getNeighbors(curNode) ;
		
		curNodeNeighbors.remove(curNode.getCamFromNode()) ;
		curNodeNeighbors.removeAll(this.sensRobot) ;
		
		//update
		this.sensRobot = curNodeNeighbors ;
		
		if(this.sensRobot.size() == 1) {
			curNodeNeighbors = graph.getNeighbors(curNode) ;
			//System.out.println("Update sens node Neighbors : "
				//	+graph.printListNode(curNodeNeighbors));
			duplicateSearch(curNodeNeighbors, graph) ;
			this.sensRobot.add(duplicateSearch(curNodeNeighbors,graph)) ;
		}
		
		
	}
	
	private Node duplicateSearch(List<Node> liste, Graph graph) {
		List<Node> subList = new ArrayList<>() ;
		int i = 1 ;
		//System.out.println("Liste duplicate : "+graph.printListNode(liste));
		for (Node node : liste) {
			 subList = liste.subList(i, liste.size()) ;
			 //System.out.println("Sublist : "+graph.printListNode(subList));
			 i++ ;
			 if(subList.contains(node))
				 return node ;
		}
		
		return null ;
		
	}

}
