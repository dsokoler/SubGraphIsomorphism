import java.net.*;
import java.io.*;

public class Client
{
	Socket client = null;
	InputStream inFromServer = null;
	OutputStream outToServer = null;

	Client() {
		String serverName = "localhost";
		int port = 9595;
		try {
			client = new Socket(serverName, port);
			System.out.println("Connected to " + client.getRemoteSocketAddress());
			outToServer = client.getOutputStream();
			inFromServer = client.getInputStream();
		}
		catch(IOException e) {
			System.out.println("Client : Unable to connect to server");
		}
	}

	
	public boolean writeBit(int bit) {
		try {
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeInt(bit);
		} catch (Exception e) {
			System.out.println("Client : Unable to writeBit");
			return false;
		}
		return true;
	}

	
	public int readBit() {
		int readIn;
		try {
			DataInputStream in = new DataInputStream(inFromServer);
			readIn = in.readInt();
		} catch (Exception e) {
			System.out.println("Client : Unable to readBit");
			return -1;
		}
		return readIn;
	}

	public boolean writeObject(Object obj) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(outToServer);
			out.writeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Client : Unable to writeObject");
			return false;
		}
		return true;
	}
	
	public Object readObject() {
		Object obj = null;
		try {
			ObjectInputStream in = new ObjectInputStream(inFromServer);
			obj = in.readObject();
		} catch (Exception e) {
			System.out.println("Client : Unable to readObject");
		}
		return obj;
	}
	
	public boolean writeString(String str) {
		try {
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF(str);
		} catch (Exception e) {
			System.out.println("Client : Unable to writeString");
			return false;
		}
		return true;
	}

	public String readString() {
		String readIn = null;
		try {
			DataInputStream in = new DataInputStream(inFromServer);
			readIn = in.readUTF();
		} catch (Exception e) {
			System.out.println("Client : Unable to readString");
			return "Didn't work";
		}
		return readIn;
	}

	public void close() {
		if(client != null)
			try {
				inFromServer.close();
				outToServer.close();
				client.close();
			} catch (IOException e) {
				System.out.println("Client : Failed at closing");
			}
	}

	public static void main(String [] args)
	{
		Client c1 = new Client();
		
		//Graph g1 = (Graph)c1.readObject();
		//g1.printGraph();
		
		Graph g1 = new Graph(2);
		c1.writeObject(g1);
		
		
		//c1.close();
	}
}
