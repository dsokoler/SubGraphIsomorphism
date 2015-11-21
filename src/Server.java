import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.io.*;


public class Server 
{
	ServerSocket socketConnection;
	Socket pipe ;
	static ObjectInputStream serverInputStream;
	static ObjectOutputStream serverOutputStream;
	static Graph G1, G2, GPrime, Q, QPrime;
	static int[] gamma, G2toGPrime, QtoQPrime, alphaPrime;

	Server() {
		try {
			socketConnection = new ServerSocket(34695);
			
			G1 = Graph.readGraphFromFile("G1.txt");
			G2 = Graph.readGraphFromFile("G2.txt");
			GPrime = Graph.readGraphFromFile("GPrime.txt");
			
			gamma = Graph.readIsomorphismFromFile("gamma.txt");
			G2toGPrime = Graph.readSubgraphRelationsFromFile("G2toGPrime.txt");
			
			pipe = socketConnection.accept();
			serverInputStream = new ObjectInputStream(pipe.getInputStream());
			serverOutputStream = new ObjectOutputStream(pipe.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void writeObject(Object obj) {	
		try {
			serverOutputStream.writeObject(obj);
			serverOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object readObject() {
		try {
			Object ob = serverInputStream.readObject();
			return ob;
		} catch (Exception e) {
			System.out.println("returning null");
			return null;
		}
	}

	public static void writeBit(int i) {
		writeObject(new Integer(i));
	}
	
	public static int readBit() {
		Integer i = (Integer)readObject();
		return i.intValue();
	}
	
	public static void close() {
		try {
			serverInputStream.close();
			serverOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 1- wait for client initialization
	 * 2- read graphs from file
	 * 3- generate alpha, Q, Q'
	 * 4- hash and commit Q. Wait for bit challenge
	 * 5- reply with requested information
	 */
	public static void main(String [] args)	{
		/*new Server();

		int[] a = (int[])Server.readObject();
		System.out.println(Arrays.toString(a));
		Server.writeObject(new int[]{1,9,1});

		System.out.println(Server.readBit());
		
		Server.writeBit(1);
		Server.writeBit(0);
		
		
		Server.close();*/
		
		new Server();
		
		//Generate alpha (G2 -> Q)
		//int[] alpha = G2.generateIsomorphism();
		int[] alpha = {7, 6, 5, 4, 3, 2, 1, 0};
		
		//Generate Q by alpha(G2)
		Graph temp = new Graph(G2.graph);
		Q = Graph.doIsomorphism(alpha, temp.graph);
		
		Graph.printGraph(G2.graph);
		System.out.println(Arrays.toString(alpha));
		Graph.printGraph(Q.graph);
		System.out.println();
		
		//Generate QPrime through G'->G2->Q)
		QPrime = G2.generateQPrime(Q, Server.G2toGPrime, alpha);
		
		//Generate alpha' through Q' -> alpha -> G'
		alphaPrime = Graph.getalphaPrime(Graph.generateSubgraphList(QPrime), alpha, Graph.generateSubgraphList(GPrime));
		
		try {
			Commitment commit = Q.commit();
			Server.writeObject(commit);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(0);
		}
		int challenge = Server.readBit();
		if (challenge == 0) {
			Server.writeObject(alpha);
			Server.writeObject(Q);
		}
		else if (challenge == 1) {
			int[] pi = Graph.addIsomorphism(gamma, alphaPrime);
			Server.writeObject(pi);
			Server.writeObject(Graph.generateSubgraphList(QPrime));
		}
		else {
			System.out.println("Invalid Challenge: " + challenge);
			System.exit(0);
		}
	}
}