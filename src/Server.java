import java.net.*;
import java.util.Arrays;
import java.io.*;


public class Server 
{
	ServerSocket socketConnection;
	Socket pipe ;
	static ObjectInputStream serverInputStream;
	static ObjectOutputStream serverOutputStream;

	Server() {
		try {
			socketConnection = new ServerSocket(11111);
			pipe = socketConnection.accept();
			serverInputStream = new ObjectInputStream(pipe.getInputStream());
			serverOutputStream = new ObjectOutputStream(pipe.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
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
	
	
	/*public static void main(String [] args)	{
		new Server();

		int[] a = (int[])Server.readObject();
		System.out.println(Arrays.toString(a));
		Server.writeObject(new int[]{1,9,1});

		System.out.println(Server.readBit());
		
		Server.writeBit(1);
		Server.writeBit(0);
		
		
		Server.close();
	}*/
}