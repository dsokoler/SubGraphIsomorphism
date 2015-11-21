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
			G2 = Graph.readGraphFromFile("G2.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public static void writeObject(Object ob) {
		try {
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
	public static void main(String[] arg) throws NoSuchAlgorithmException {

		/*int[] arr = {1, 2, 7};
		new Client();
		Client.writeObject(arr);

		System.out.println(Arrays.toString((int[])Client.readObject()));
		
		Client.writeBit(1);
		
		System.out.println(readBit());
		System.out.println(readBit());*/
		
		new Client();
		Commitment commit = (Commitment) Client.readObject();
		Random random = new Random();
		//int challenge = random.nextInt();
		int challenge = 0;
		Client.writeBit(challenge);
		
		if (challenge == 0) {
			int[] alpha = (int[]) Client.readObject();
			Graph Q = (Graph) Client.readObject();
			
			//Verify the graph received is the same as what was committed
			if (!Q.verifyCommitment(commit)) {
				System.out.println("CHALLENGE 0: COMMITMENT VERIFICATION FAILURE");
				System.exit(0);
			}
			//Verify alpha(G2) == Q
			if (!G2.verifyG2Isomorphism(alpha, Q)) {
				System.out.println("CHALLENGE 0: ISOMORPHISM VERIFICATION FAILURE");
				System.exit(0);
			}
			
			System.out.println("CHALLENGE 0: VERIFICATION SUCCESS");
		}
		else if (challenge == 1) {
			int[] pi = (int[]) Client.readObject();
			int[] QPrimeinQ = (int[]) Client.readObject();
			
			Graph temp = new Graph(G1.graph);
			Graph QPrime = Graph.doIsomorphism(pi, temp.graph); 

			//Verify Q' is among the committed values
			if (QPrime.isSubgraph(commit, QPrimeinQ)) {
				System.out.println("CHALLENGE 1: COMMITMENT VERIFICATION FAILURE");
				System.exit(0);
			}
			//Verify pi(G1) == Q'
			if (!G1.verifyG1Isomorphism(pi, QPrime)) {
				System.out.println("CHALLENGE 1: ISOMORPHISM VERIFICATION FAILURE");
				System.exit(0);
			}
			
			System.out.println("CHALLENGE 1: VERIFICATION SUCCESS");
		}
		else {
			System.out.println("Invalid challenge: " + challenge);
			System.exit(0);
		}
		
		

		Client.close();
	}
}