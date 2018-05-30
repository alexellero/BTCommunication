package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import pathfinding.Graph;
import pathfinding.GraphTest;
import pathfinding.Node;
import programme.LifeSaving;
import utils.Direction;
import utils.N_uplet;

public class BTInitiator extends Thread{
	
	private InputStream is = null;
	private OutputStream os = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private String nom = null;
	private String adr = null;
	private BTInitiator other = null;
	public Node position = null;
	private Node obj = null;
	public List<Node> path = new ArrayList<>();
	public List<Node> sensR = new ArrayList<>() ;
	public static List<Node> victimes = new ArrayList<>() ;
	public static List<Node> hopitaux = new ArrayList<>() ;
	public Graph graph = null;
	public boolean isWaiting = false;
	
	
	
	
	public BTInitiator(String nom, String adr) {
		super();
		this.nom = nom;
		this.adr = adr;
	}
	
	

	public void setOther(BTInitiator other) {
		this.other = other;
	}



	public void initiate () throws NXTCommException, IOException, InterruptedException {
		
		System.out.println(this.getName() + " : Connecting...");
		NXTComm comm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		NXTInfo nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, this.nom, this.adr);
		boolean isConnected = comm.open(nxtInfo);
		if (isConnected) {
			System.out.println(this.getName() + " : Connected");
		}
		else {
			System.out.println(this.getName() + " : Connection failed");
		}
		
		is = comm.getInputStream();
		os = comm.getOutputStream();
		dis = new DataInputStream(is);
		dos = new DataOutputStream(os);
		
		
		
		this.runOneRobot();
		
		
		Thread.sleep(2000);
		dis.close();
		dos.close();
		comm.close();
		System.out.println(this.getName() + " : End connection");
		
	}
	
	public void run() {
		this.setName(nom);
		try {
			this.initiate();
		} catch (NXTCommException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
public void runRobot () throws IOException, NXTCommException, InterruptedException {
		
		List<Direction> parc = new LinkedList<Direction>();
		List<Direction> parc2 = new ArrayList<>();
		
		LifeSaving savior = new LifeSaving(sensR,victimes,hopitaux);
		int nbVictimes = 0;
		
		while (!savior.getVictimes().isEmpty()) {
			synchronized (this) {
				path = savior.saveLives(graph, position);
				obj = path.get(path.size()-1);
				parc = savior.sendOrderToRobot(graph, path);
			}
			
			this.sendDirection(parc);
			parc2.addAll(parc);
			parc.clear();
			char a = '0';
			while (parc.isEmpty() && a!= 's') {
				a = this.receiveChar();
				if (!path.isEmpty()) {
					position = path.get(0);System.out.println(this.getName() + " : position : " + position.toString());
					path.remove(0);
					parc2.remove(0);
					if (!path.isEmpty() && path.get(0).equals(other.position) && !other.isWaiting) {
						parc.add(Direction.WAIT);
						this.sendDirection(parc);
						this.isWaiting = true;
						parc.clear();
						while (path.get(0).equals(other.position)) {
							
						}
						parc.add(Direction.GO);
						parc.addAll(parc2);
						this.sendDirection(parc);
						this.isWaiting = false;
						parc.clear();
					}
				}
				

			}
			System.out.println("nbVictimes : " + savior.getNbVictimSave() + "  " + nbVictimes);
			if (savior.getNbVictimSave() == nbVictimes + 1) {
				this.sendChar('b');
				this.receiveChar();
				nbVictimes++;
			}
			else if (savior.getNbVictimSave() == nbVictimes - 2) {
				this.sendChar('b');
				this.receiveChar();
				this.sendChar('b');
				this.receiveChar();
				this.sendChar('b');
				this.receiveChar();
				this.sendChar('b');
				this.receiveChar();
				nbVictimes = 0;
			}
			else if (savior.getNbVictimSave() == nbVictimes - 1) {
				this.sendChar('b');
				this.receiveChar();
				this.sendChar('b');
				this.receiveChar();
				nbVictimes = 0;
			}
		}
		path = savior.saveLives(graph, position);
		obj = path.get(path.size()-1);
		parc = savior.sendOrderToRobot(graph, path);
		this.sendDirection(parc);
		parc.clear();
		char a = '0';
		while (parc.isEmpty() && a!= 's') {
			a = this.receiveChar();
			if (!path.isEmpty()) {
				position = path.get(0);
				path.remove(0);
			}
		}
		System.out.println("nbVictimes : " + savior.getNbVictimSave() + "  " + nbVictimes);
		if (savior.getNbVictimSave() == nbVictimes - 2) {
			this.sendChar('b');
			this.receiveChar();
			this.sendChar('b');
			this.receiveChar();
			this.sendChar('b');
			this.receiveChar();
			this.sendChar('b');
			this.receiveChar();
			nbVictimes = 0;
		}
		else if (savior.getNbVictimSave() == nbVictimes - 1) {
			this.sendChar('b');
			this.receiveChar();
			this.sendChar('b');
			this.receiveChar();
			nbVictimes = 0;
		}
		
		
		
		
	}
	
	public void runOneRobot() throws IOException, NXTCommException, InterruptedException {
		/*GraphTest myGraph = new GraphTest();
		Graph graph = myGraph.initializeGraph();
		
		
		List<Node> sensR = new ArrayList<>() ;
		List<Node> victimes = new ArrayList<>() ;
		List<Node> hopitaux = new ArrayList<>() ;
		victimes.add(myGraph.getNode('A')) ;
		victimes.add(myGraph.getNode('F')) ;
		victimes.add(myGraph.getNode('E')) ;
		victimes.add(myGraph.getNode('K')) ;
		victimes.add(myGraph.getNode('H')) ;
		hopitaux.add(myGraph.getNode('L')) ;
		hopitaux.add(myGraph.getNode('G')) ;
		position = myGraph.getNode('B');
		sensR.add(myGraph.getNode('A')) ;
		sensR.add(myGraph.getNode('E')) ;*/
		
		
		
		List<Direction> parc = new LinkedList<>();
		LifeSaving savior = new LifeSaving(sensR,victimes,hopitaux);
		int nbVictimes = 0;
		
		while (!savior.getVictimes().isEmpty()) {
			path = savior.saveLives(graph, position);
			obj = path.get(path.size()-1);
			parc = savior.sendOrderToRobot(graph, path);
			this.sendDirection(parc);
			parc.clear();
			char a = '0';
			while (parc.isEmpty() && a!= 's') {
				a = this.receiveChar();
				if (!path.isEmpty()) {
					position = path.get(0);System.out.println("position : " + position.toString());
					path.remove(0);
				}
				

			}
			System.out.println("nbVictimes : " + savior.getNbVictimSave() + "  " + nbVictimes);
			if (savior.getNbVictimSave() == nbVictimes + 1) {
				this.sendChar('b');
				this.receiveChar();
				nbVictimes++;
			}
			else if (savior.getNbVictimSave() == nbVictimes - 2) {
				this.sendChar('n');
				this.receiveChar();
				sleep(100);
				this.sendChar('n');
				this.receiveChar();
				nbVictimes = 0;
			}
			else if (savior.getNbVictimSave() == nbVictimes - 1) {
				this.sendChar('n');
				this.receiveChar();
				nbVictimes = 0;
			}
		}
		path = savior.saveLives(graph, position);
		obj = path.get(path.size()-1);
		parc = savior.sendOrderToRobot(graph, path);
		this.sendDirection(parc);
		parc.clear();
		char a = '0';
		while (parc.isEmpty() && a!= 's') {
			a = this.receiveChar();
			if (!path.isEmpty()) {
				position = path.get(0);
				path.remove(0);
			}
		}
		System.out.println("nbVictimes : " + savior.getNbVictimSave() + "  " + nbVictimes);
		if (savior.getNbVictimSave() == nbVictimes - 2) {
			this.sendChar('n');
			this.receiveChar();
			sleep(100);
			this.sendChar('n');
			this.receiveChar();
			nbVictimes = 0;
		}
		else if (savior.getNbVictimSave() == nbVictimes - 1) {
			this.sendChar('n');
			this.receiveChar();
			nbVictimes = 0;
		}
		parc.clear();
		parc.add(Direction.ARRET);
		this.sendDirection(parc);
	}
	
	public void close (NXTComm comm) throws IOException {
		dis.close();
		dos.close();
		comm.close();
		System.out.println(this.getName() + " : End connection");
	}
	
	private void sendChar (char c) throws IOException {
		dos.writeChar(c);
		dos.flush();
		System.out.println(this.getName() + " : Envoi : " + c);
	}
	
	private char receiveChar () throws IOException {
		char c = dis.readChar();
		System.out.println(this.getName() + " : Reception : " + c);
		return c;
	}
	
	private void controlRobot () throws IOException {
		dis = new DataInputStream(is);
		dos = new DataOutputStream(os);
		char command = '0';
		Scanner sc = new Scanner(System.in);
		
		
		while(command != 'a') {
			System.out.println("Commande attendue...");
			char c = sc.next().charAt(0);
			this.sendChar(c);
			command = this.receiveChar();
		}
		sc.close();
		dis.close();
		dos.close();
	}
	
	private void controlDirection () throws IOException {
		dis = new DataInputStream(is);
		dos = new DataOutputStream(os);
		char command = '0';
		Scanner sc = new Scanner(System.in);
		
		
		while(command != 'a') {
			System.out.println("Commande attendue...");
			char c = sc.next().charAt(0);
			if (c == 'q') {
				dos.writeChar(c);
				dos.flush();
				System.out.println(this.getName() + " : Envoi : GAUCHE");
				dos.writeChar('0');
				dos.flush();
			}
			else if (c == 'd') {
				dos.writeChar(c);
				dos.flush();
				System.out.println(this.getName() + " : Envoi : DROITE");
				dos.writeChar('0');
				dos.flush();
			}
			else {
				dos.writeChar(c);
				dos.flush();
				System.out.println(this.getName() + " : Envoi : " + c);
				dos.writeChar('0');
				dos.flush();
			}
			command = this.receiveChar();
			
		}
		sc.close();
		dis.close();
		dos.close();
	}
	
	
	
	public void sendDirection (List<Direction> directions) throws IOException {
		//dis = new DataInputStream(is);
		//dos = new DataOutputStream(os);
		
		for (Direction dir : directions) {
			switch (dir) {
			case DROITE :
				dos.writeChar('d');
				dos.flush();
				System.out.println(this.getName() + " : Envoi : DROITE");
				break;
			case GAUCHE :
				dos.writeChar('q');
				dos.flush();
				System.out.println(this.getName() + " : Envoi : GAUCHE");
				break;
			case DEMITOUR :
				dos.writeChar('e');
				dos.flush();
				System.out.println(this.getName() + " : Envoi : DEMITOUR");
				break;
			case STOP :
				dos.writeChar('s');
				dos.flush();
				System.out.println(this.getName() + " : Envoi : STOP");
				break;
			case WAIT :
				dos.writeChar('w');
				dos.flush();
				System.out.println(this.getName() + " : Envoi : WAIT");
				break;
			case GO :
				dos.writeChar('z');
				dos.flush();
				System.out.println(this.getName() + " : Envoi : GO");
				break;
			case ARRET :
				dos.writeChar('a');
				dos.flush();
				System.out.println(this.getName() + " : Envoi : ARRET");
				break;
			}
		}
		dos.writeChar('0');
		dos.flush();
		
		//dis.close();
		//dos.close();
	}

	


}
