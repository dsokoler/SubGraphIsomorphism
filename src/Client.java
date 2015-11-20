import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Client {

	static Socket socketConnection;
	static ObjectOutputStream clientOutputStream;
	static ObjectInputStream clientInputStream;
	static Graph G1, G2;

	Client() {
		try {
			socketConnection = new Socket("127.0.0.1", 11111);
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
	public static void main(String[] arg) {

		/*int[] arr = {1, 2, 7};
		new Client();
		Client.writeObject(arr);

		System.out.println(Arrays.toString((int[])Client.readObject()));
		
		Client.writeBit(1);
		
		System.out.println(readBit());
		System.out.println(readBit());*/
		
		new Client();
		Commitment commit = (Commitment) Client.readObject();
		
		

		Client.close();
	}
}