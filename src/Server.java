import java.net.*;
import java.io.*;

// Note: never close an input/output stream or any wrapper over it. 

public class Server 
{
	Socket server;
	DataInputStream in = null;
	DataOutputStream out = null;
	//ObjectOutputStream oout = null;
	//ObjectInputStream oin = null;
	
	public Server()	{
		ServerSocket serverSocket  = null;
		try {
			serverSocket = new ServerSocket(9595);
			serverSocket.setSoTimeout(15000);
			System.out.println("Waiting for client on port " + serverSocket.getLocalPort());
			Socket server = serverSocket.accept();
			System.out.println("Connected to " + server.getRemoteSocketAddress());
			in = new DataInputStream(server.getInputStream());
			out = new DataOutputStream(server.getOutputStream());
			//oin = new ObjectInputStream(server.getInputStream());
			//oout = new ObjectOutputStream(server.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int readBit() {
		int ret = -1;
		try {
			ret = in.readInt();
		} catch (Exception e) {
			System.out.println("Server : readBit failed");
		}
		return ret;
	}


	public void writeBit(int bit) {
		try {
			out.writeInt(bit);
		} catch (Exception e) {
			System.out.println("Server : writeBit failed");
		}
	}

	public void writeRandomBit() {
		int n = (int)((10 * Math.random()) % 2);
		//System.out.println("n = " + n);
		writeBit(n);
	}

	public void writeObject(Object obj) {
		try {
			ObjectOutputStream oout = new ObjectOutputStream(server.getOutputStream());
			oout.writeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Server : writeObject failed");
		}
	}
	
	public Object readObject() {
		Object obj = null;
		try {
			ObjectInputStream oin = new ObjectInputStream(server.getInputStream());
			obj = oin.readObject();
		} catch (Exception e) {
			System.out.println("Server : readObject failed");
		}
		return obj;
	}
	
	public void close() {
		try {
			if(server != null) server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String [] args)
	{
		Server s1 = new Server();
		
		//Graph g1 = new Graph(2);
		//g1.printGraph();
		//s1.writeObject(g1);
		
		Graph g1 = (Graph)s1.readObject();
		g1.printGraph();
		
		//s1.close();

	}
}