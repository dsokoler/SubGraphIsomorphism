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
			System.out.println("readObject: " + (int) ob);
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
		int i = (int)readObject();
		System.out.println("readBit: " + i);
		return i;
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
	public static void main(String [] args) throws IOException	{
		
		new Server();
		
		for (int runs = 0; runs < 10; runs++) {
			System.out.println("Run " + runs);
			//Generate alpha (G2 -> Q)
			int[] alpha = G2.generateIsomorphism();
			//int[] alpha = {7, 6, 5, 4, 3, 2, 1, 0};
			
			//Generate Q by alpha(G2)
			Graph temp = Graph.readGraphFromFile("G2.txt");
			Q = Graph.doIsomorphism(alpha, temp.graph);
			
			int[] subgraph2 = Graph.genSubgraph2(G2toGPrime, alpha);
			
			//int[] subgraph2 = {6, 5, 4, 7};
			
			//Applying subgraph2 to Q
			Graph Qp = new Graph(Q.getSubgraph(subgraph2));
			
			//Generate alpha' through Q' -> alpha -> G'
			// G2toGPrime is same as subgraph2
			alphaPrime = Graph.getalphaPrime(subgraph2, alpha, G2toGPrime);
			
			try {
				Commitment commit = Q.commit();
				Server.writeObject(commit);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				System.exit(0);
			}
			int challenge = Server.readBit();
			if (challenge == 0) {
				Server.writeObject(alpha);
				Server.writeObject(Q);
			}
			else if (challenge == 1) {
				// pi = gamma + alphaPrime
				int[] pi = Graph.addIsomorphism(gamma, alphaPrime);
				Server.writeObject(pi);
				
				Graph temp1 = new Graph(G1.graph);
				Graph QPrime = Graph.doIsomorphism(pi, temp1.graph); 
				
				//generate open-commitment of Q-Prime in Q
				int[] QPrimeinQ = Graph.generateSubgraphList(Qp);
				
				int[][] toSend = Q.generateSend(QPrimeinQ);
				
				Server.writeObject(toSend);
				
				int[][] test = {
						{1, 1, 1, 1, 1, 1, 1, 1},
						{1, 1, 1, 1, 1, 1, 1, 1},
						{1, 1, 1, 1, 1, 1, 1, 1},
						{1, 1, 1, 1, 1, 1, 1, 1},
						{1, 1, 1, 1, 1, 1, 1, 1},
						{1, 1, 1, 1, 1, 1, 1, 1},
						{1, 1, 1, 1, 1, 1, 1, 1},
						{1, 1, 1, 1, 1, 1, 1, 1},
				};
				
				//Server.writeObject(test);
			}
			else {
				System.out.println("Invalid Challenge: " + challenge);
				System.exit(0);
			}
			System.out.println();
		}
		Server.close();
	}
}