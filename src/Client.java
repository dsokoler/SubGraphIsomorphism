import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Client {

	static Socket socketConnection;
	static ObjectOutputStream clientOutputStream;
	static ObjectInputStream clientInputStream;
	static Graph G1, G2;

	Client() {
		try {
			socketConnection = new Socket("127.0.0.1", 34695);
			clientOutputStream = new ObjectOutputStream(socketConnection.getOutputStream());
			clientInputStream = new ObjectInputStream(socketConnection.getInputStream());
			
			G1 = Graph.readGraphFromFile("G1.txt");
			//System.out.println("Client intial read of G1");
			//Graph.printGraph(G1.graph);
			//System.out.println();
			G2 = Graph.readGraphFromFile("G2.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public static void writeObject(Object ob) {
		try {
			//System.out.println("Sending: " + (int) ob);
			clientOutputStream.writeObject(ob);
			clientOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object readObject() {
		try {
			Object ob = clientInputStream.readObject();
			return ob;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
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
			clientOutputStream.close();
			clientInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 1- reply with random bit, 1 or 0
	 * 2- wait for reply, verify reply
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		
		new Client();
		int numberOfRuns = Integer.parseInt(args[0]);
		int[] challenges = new int[numberOfRuns];
		Random random = new Random();
		System.out.println("Challenges: ");
		for (int i = 0; i < numberOfRuns; i++) {
			challenges[i] = random.nextInt(2);
			System.out.printf("%d ", challenges[i]);
		}
		System.out.println();
		System.out.println();
		for (int runs = 0; runs < numberOfRuns; runs++) {
			//System.out.println("Run " + runs);
			Commitment commit = (Commitment) Client.readObject();
			
			//int challenge = random.nextInt(2);
			//Client.writeBit(challenge);
			Client.writeBit(challenges[runs]);
			
			if (challenges[runs] == 0) {
				int[] alpha = (int[]) Client.readObject();
				Graph Q = (Graph) Client.readObject();
				
				//Verify the graph received is the same as what was committed
				if (!Q.verifyCommitment(commit)) {
					System.out.printf("CHALLENGE %d (0): COMMITMENT VERIFICATION FAILURE\n", runs);
					System.exit(0);
				}
				
				
				//Verify alpha(G2) == Q
				if (!G2.verifyG2Isomorphism(alpha, Q)) {
					System.out.printf("CHALLENGE %d (0): ISOMORPHISM VERIFICATION FAILURE\n", runs);
					System.exit(0);
				}
				
				System.out.printf("CHALLENGE %d (0): VERIFICATION SUCCESS\n", runs);
			}
			else if (challenges[runs] == 1) {
				int[] pi = (int[]) Client.readObject();
				int[][] QPrimeinQ = (int[][]) Client.readObject();
				
				Graph temp = new Graph(G1.adjacencyMatrix);
				Graph QPrime = Graph.doIsomorphism(pi, temp.graph); 
	
				//Verify Q' is among the committed values
				if (!Graph.isSubgraph(commit, QPrimeinQ)) {
					System.out.printf("CHALLENGE %d (1): COMMITMENT VERIFICATION FAILURE\n", runs);
					System.exit(0);
				}
				//Verify pi(G1) == Q'
				if (!G1.verifyG1Isomorphism(pi, QPrime)) {
					System.out.printf("CHALLENGE %d (1): ISOMORPHISM VERIFICATION FAILURE\n", runs);
					System.exit(0);
				}
				
				System.out.printf("CHALLENGE %d (1): VERIFICATION SUCCESS\n", runs);
			}
			else {
				System.out.println("Invalid challenge: " + challenges[runs]);
				System.exit(0);
			}
			//System.out.println();
		}
		System.out.println("Server Knowledge Verified.");
		

		Client.close();
	}
}