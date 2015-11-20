import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class Graph {

	List<Vertex> graph = new ArrayList<Vertex>();
	
	boolean[][] adjacencyMatrix;

	Graph(boolean[][] mat) {
		adjacencyMatrix = mat;
		for(int i = 0; i < mat.length; i++) {
			Vertex v = new Vertex();
			for(int j = 0; j < mat[i].length; j++) {
				if(mat[i][j] == true) {
					v.add(j);
				}
			}
			graph.add(i, v);
		}
	}

	void printGraph() {
		System.out.printf(" ");
		for(int i = 0; i < graph.size(); i++) {
			System.out.printf(" %d", i);
		}
		System.out.println();

		for(int i = 0; i < graph.size(); i++) {
			System.out.printf("%d ", i);
			Vertex v = graph.get(i);
			for(int j =0; j < graph.size(); j++) {
				if(v.contains(j)) {
					System.out.printf("T ");
				}
				else {
					System.out.printf("F ");
				}
			}
			System.out.println();
		}
	}


	void doIsomorphism(int[] iso) {
		List<Vertex> n = new ArrayList<Vertex>();
		
		// initializing arraylist to required size
		for (int i = 0; i < graph.size(); i++) {
		    n.add(null);
		}
		int[] changes = new int[graph.size()];

		// fix individual positions
		for(int i = 0; i < graph.size(); i++) {
			Vertex v = graph.get(i);
			//System.out.println("iso[i] = " + iso[i]);
			//v.print();
			v.remove(i);
			//v.add(iso[i]);
			n.set(iso[i], v);
			changes[i] = iso[i];
		}

		// fix relative positions
		for (int i = 0; i < n.size(); i++) {
			Vertex v = n.get(i);
			for(int j =0; j < v.size(); j++) {
				int x = v.get(j);
				v.set(j, iso[x]);
			}
			v.add(i);
			n.set(i, v);
		}
		graph = n;
	}

	List<Vertex> getSubgraph(int[] arr) {
		List<Vertex> subgraph1 = new ArrayList<Vertex>();
		List<Vertex> subgraph2 = new ArrayList<Vertex>();
		
		// add all connections
		for(int i =0; i < arr.length; i++) {
			Vertex v = graph.get(arr[i]);
			subgraph1.add(v);
		}
		
		
		//System.out.println(Arrays.toString(arr));
		// remove connections not needed
		//System.out.println("subgraph.size = " + subgraph1.size());
		for (int i = 0; i < subgraph1.size(); i++) {
			Vertex v = subgraph1.get(i);
			//System.out.println("v.size =" + v.size());
			Vertex k = new Vertex();
			for (int j = 0; j < v.size(); j++) {
				//System.out.println("v.get(j) =" + v.get(j));
				if(contains(arr, v.get(j))) {
					//System.out.println("adding");
					k.add(v.get(j));
				}
			}
			subgraph2.add(k);
		}
		return subgraph2;
	}
	
	static void printSubgraph(List<Vertex> s, int[] subgraph) {
		System.out.printf(" ");
		for(int i = 0; i < s.size(); i++) {
			System.out.printf(" %d", i);
		}
		System.out.println();

		for(int i = 0; i < s.size(); i++) {
			System.out.printf("%d ", i);
			Vertex v = s.get(i);
			for(int j =0; j < subgraph.length; j++) {
				if(v.contains(subgraph[j])) {
					System.out.printf("T ");
				}
				else {
					System.out.printf("F ");
				}
			}
			System.out.println();
		}
		
	}
	
	public static boolean contains(int[] arr, int k) {
		for(int i =0; i < arr.length; i++) {
			if(k == arr[i]) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Given path to a file, read the graph
	 * FORMAT: size on first line followed by adjacency matrix of
	 * 0's and 1's separated by spaces
	 */
	static Graph readGraphFromFile(String path) {
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			String line = br.readLine();
			int size = Integer.parseInt(line);
			boolean[][] matrix = new boolean[size][size];
			
			
			for (int i = 0; (line = br.readLine()) != null; i++) {
		    	String[] split = line.split(" ");
		    	for (int j = 0; j < split.length; j++) {
		    		if (split[j].equals("0")) {
		    			matrix[i][j] = false;
		    		}
		    		else if (split[j].equals("1")) {
		    			matrix[i][j] = true;
		    		}
		    		else {
		    			//ERROR
		    			System.out.println("Invalid file input: " + split[i]);
		    			return null;
		    		}
		    	}
		    }
			
			//Success case
			return new Graph(matrix);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//Failure case, IOException
		return null;
	}
	
	/*
	 * Given path to a file, read the isomorphism
	 * FORMAT: single line of integers separated by spaces
	 */
	static int[] readIsomorphismFromFile(String path) {
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			String[] isoString = line.split(" ");
			int[] iso = new int[isoString.length];
			
			for (int i = 0; i < isoString.length; i++) {
				iso[i] = Integer.parseInt(isoString[i]);
			}
			
			//Success case
			return iso;
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//Failure case, IOException
		return null;
	}
	
	/*
	 * Given path to a file, read the relation between a graph it's subgraph
	 * FORMAT: TODO
	 */
	static int[] readGraphRelationsFromFile(String path) {
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			//TODO: READING CODE HERE			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//Failure case, IOException
		return null;
	}
	
	/*
	 * Hash all values of the graph (committing)
	 */
	public Commitment commit() throws NoSuchAlgorithmException {
		return new Commitment(this.adjacencyMatrix);
	}
	
	/*
	 * Verifier method
	 * Check the full graph they received is the same as the
	 * commitment they received earlier
	 */
	public boolean verifyCommitment(Commitment commit) {
		Commitment verify = new Commitment(this.adjacencyMatrix);
		
		return Arrays.deepEquals(verify.commit, commit.commit);
	}
	
	/*
	 * Verifier method
	 * Check the subgraph they received is within
	 * the commitment they received earlier
	 */
	public boolean verifyCommitment(Commitment commit, int[] nodes) {
		Commitment verify = new Commitment(this.adjacencyMatrix);
		
		//Check if the committed values for each node in the commit
		// match to those in the new commit
		for (int i = 0; i < verify.size; i++) {
			for (int j = 0; j < verify.size; j++) {
				
			}
		}
		
		return true;
	}
	
	/*
	 * Verifier method
	 * Apply isomorphism to G2 and check the outcome matches graph Q
	 */
	public boolean verifyG2Isomorphism(int[] iso, Graph Q) {
		this.doIsomorphism(iso);
		
		return Arrays.deepEquals(this.adjacencyMatrix, Q.adjacencyMatrix);
	}
	
	/*
	 * Verifier method
	 * Apply isomorphism to G1 and check the outcome matches QPrime
	 */
	public boolean verifyG1Isomorphism(int[] iso, Graph QPrime) {
		this.doIsomorphism(iso);
		
		return Arrays.deepEquals(this.adjacencyMatrix, QPrime.adjacencyMatrix);
	}

	public static void main(String[] args) {
		Graph test = readGraphFromFile("testGraphReading.txt");
		
		boolean adjMat[][] = 
			{
				{true,  true,  false, false, true,  false, false, false},
				{true,  true,  false, true,  true,  false, false, true},
				{false, false, true,  true,  true,  false, true,  true},
				{false, true,  true,  true,  false, true,  true,  false},
				{true,  true,  true,  false, true,  true,  false, false},
				{false, false, false, true,  true,  true,  true,  false},
				{false, false, true,  true,  false, false, true,  true},
				{false, true,  true,  false, false, false, true,  true}
			};

		System.out.println(Arrays.deepEquals(test.adjacencyMatrix, adjMat));
		System.exit(1);
		
		Graph g = new Graph(adjMat);
		g.printGraph();

		int[] isomorphism = {7, 2, 0, 6, 1, 4, 3, 5};
		// 7 goes to 0, 2 goes 1, 0 goes 2 and so on
		// 7 gets 0's column/row
		
		g.doIsomorphism(isomorphism);
		g.printGraph();


		int[] subgraph = {1, 5, 6, 7};
		List<Vertex> s =g.getSubgraph(subgraph);
		

		// printing subgraph
		printSubgraph(s, subgraph);
		
		System.out.println("-----------------------------");
		try {
			Commitment GCommit = g.commit();
			GCommit.printCommit();
			
			System.out.println("Comparison of G and Commitment: " + g.verifyCommitment(GCommit));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("SHA-256 does not exist");
		}
		System.out.println("-----------------------------");
	}

}

class Vertex {

	List<Integer> v = new ArrayList<Integer>();

	void add(int i) {
		v.add(i);
	}

	void set(int index, int i) {
		v.set(index, i);
	}

	int size() {
		return v.size();
	}

	Integer get(int i) {
		return v.get(i);
	}

	boolean contains(int i) {
		if(v.contains(i)) {
			return true;
		}
		return false;
	}

	void remove(int i) {
		int index = v.indexOf(i);
		v.remove(index);
	}

	void print() {
		for (int i = 0; i < v.size(); i++) {
			System.out.printf("%d ", v.get(i));
		}
		System.out.printf("\n");
	}

	void clear() {
		v.clear();
	}

}