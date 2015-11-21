import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//TODO: Change doIsomorphism to return a new graph
//TODO: complete subgraph commitment verification

public class Graph {

	List<Vertex> graph = new ArrayList<Vertex>();
	
	boolean[][] adjacencyMatrix;

	Graph(boolean[][] mat) {
		adjacencyMatrix = mat;
		for(int i = 0; i < mat.length; i++) {
			Vertex v = new Vertex();
			v.nodeID = i;
			for(int j = 0; j < mat[i].length; j++) {
				if(mat[i][j] == true) {
					v.add(j);
				}
			}
			graph.add(i, v);
		}
	}

	Graph(List<Vertex> g) {
		graph = g;
	}

	static void printGraph(List<Vertex> graph) {
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



	public static Graph doIsomorphism(int[] iso, List<Vertex> graph) {
		
		
				
		List<Vertex> n = new ArrayList<Vertex>();
		
		// initializing arraylist to required size
		for (int i = 0; i < graph.size(); i++) {
		    n.add(null);
		}
		int[] changes = new int[graph.size()];

		// fix individual positions
		for(int i = 0; i < graph.size(); i++) {
			Vertex v = graph.get(i);
			v.sort();
			System.out.println("iso[i] = " + iso[i] + "and i = " + i);
			v.print();
			if(v.contains(i)) v.remove(i);
			v.add(iso[i]);
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
		
		return new Graph(n);
	}



	List<Vertex> getSubgraph(int[] arr) {
		List<Vertex> subgraph1 = new ArrayList<Vertex>();
		List<Vertex> subgraph2 = new ArrayList<Vertex>();
		List<Vertex> subgraph3 = new ArrayList<Vertex>();
		
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
			v.sort();
			//System.out.println("v.size =" + v.size());
			Vertex k = new Vertex();
			k.nodeID = v.nodeID;
			for (int j = 0; j < v.size(); j++) {
				//System.out.println("v.get(j) =" + v.get(j));
				if(contains(arr, v.get(j))) {
					//System.out.println("adding");
					k.add(v.get(j));
				}
			}
			//System.out.println("k.print = ");
			//k.print();
			subgraph2.add(k);
		}
		
		for (int i = 0; i < subgraph2.size(); i++) {
			Vertex v = subgraph2.get(i);
			Vertex u = new Vertex();
			for (int j = 0; j < v.size(); j++) {
				int k = v.get(j);
				int l = searchForElement(arr, k);
				System.out.println("j = " + j + " vertex = " + k + " index in arr = " + l);
				u.add(l);
			}
			subgraph3.add(u);
		}
		
		return subgraph3;
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
	/*
	 * returns index 
	 */
	 static int searchForElement(int[] arr, int element) {
		for (int i = 0; i < arr.length; i++) {
			if(arr[i] == element) {
				return i;
			}
		}
		return -1;
	 }
	
	public static int[] getalphaPrime(int[] subgraph2, int[] alpha, int[] subgraph1) {
		
		int[] x = new int[subgraph2.length];
		
		// getting subgraph2 vertices in G2
		for (int i = 0; i < subgraph2.length; i++) {
			int k = searchForElement(alpha, subgraph2[i]);
			x[i] = k;
		}
		System.out.println("x =" + Arrays.toString(x));
		
		
		// comparing those vertices to subgraph1
		int[] alphaP = new int[subgraph1.length];
		for (int i = 0; i < subgraph1.length; i++) {
			int k = searchForElement(x, subgraph1[i]);
			alphaP[i] = k;
		}
		
		System.out.println("alphaP =" + Arrays.toString(alphaP));
		
		return alphaP;
		
	}
	
	public int[] generateIsomorphism() {
		int[] isomorphism = new int[this.graph.size()];
		for (int i = 0; i < this.graph.size(); i++) {
			isomorphism[i] = i;
		}
		
		Random random = new Random();
		for (int i = 0; i < this.graph.size(); i++) {
			int permute = random.nextInt(this.graph.size());
			swap(isomorphism, i, permute);
		}
		
		return isomorphism;
	}
	
	private static boolean swap (int[] array, int one, int two) {
		int temp = array[one];
		array[one] = array[two];
		array[two] = temp;
		
		return true;
	}
	
	public static boolean contains(int[] arr, int k) {
		for(int i =0; i < arr.length; i++) {
			if(k == arr[i]) {
				return true;
			}
		}
		return false;
	}
	
	public boolean[][] getAdjacencyMatrix() {
		boolean[][] mat = new boolean[graph.size()][graph.size()];
		for(int i =0;i < graph.size(); i++) {
			Vertex v = graph.get(i);
			for(int j = 0;j < graph.size(); j++) {
				if(v.contains(j)) {
					mat[i][j] = true;
				}
				else {
					mat[i][j] = false;
				}
			}
		}
		return mat;
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
	 * FORMAT: integers (node numbers) separated by spaces
	 */
	static int[] readSubgraphRelationsFromFile(String path) {
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			String[] subString = line.split(" ");
			int[] subgraph = new int[subString.length];
			
			for (int i = 0; i < subString.length; i++) {
				subgraph[i] = Integer.parseInt(subString[i]);
			}
			
			//Success case
			return subgraph;
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//Failure case, IOException
		return null;
	}
	
	/*
	 * Returns the listing of nodes in subgraph as their IDs
	 *  in the parent graph
	 *  @param subgraph: the subgraph to be traversed
	 */
	public static int[] generateSubgraphList(Graph subgraph) {
		int[] nodes = new int[subgraph.graph.size()];
		
		for (int i = 0; i < subgraph.graph.size(); i++) {
			nodes[i] = subgraph.graph.get(i).nodeID;
		}
		
		return nodes;
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
		doIsomorphism(iso, this.graph);
		
		return Arrays.deepEquals(this.adjacencyMatrix, Q.adjacencyMatrix);
	}
	
	/*
	 * Verifier method
	 * Apply isomorphism to G1 and check the outcome matches QPrime
	 */
	public boolean verifyG1Isomorphism(int[] iso, Graph QPrime) {
		doIsomorphism(iso, this.graph);
		
		return Arrays.deepEquals(this.adjacencyMatrix, QPrime.adjacencyMatrix);
	}
	
	static int[] addIsomorphism(int[] iso1, int[] iso2) {
        
        int[] isof = new int[iso1.length];
        for(int i =0; i <iso1.length; i++) {
            isof[i] = iso2[iso1[i]];            
        }
        return isof;
    }


	public static void main(String[] args) {
		/*Graph test = readGraphFromFile("testGraphReading.txt");
		
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

		if(Arrays.deepEquals(test.adjacencyMatrix, adjMat)) {
			System.out.println("READ FROM FILE SUCCESS");
		}
		
		Graph g = new Graph(adjMat);
		printGraph(g.graph);*/
		
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
		
		Graph g2 = new Graph(adjMat);
		
		int[] alpha = {7, 2, 0, 6, 1, 4, 3, 5};
		printGraph(g2.graph);
		
		Graph g3 = new Graph(adjMat);
		
		
		Graph Q = doIsomorphism(alpha, g3.graph);
		System.out.println("g2");
		printGraph(g2.graph);
		System.out.println("Proxy");
		printGraph(g3.graph);
		
		
		System.out.println("graph Q = ");
		printGraph(Q.graph);
		
		int[] subGraph2 = {0, 1, 4, 6};
		
		List<Vertex> QPrime = Q.getSubgraph(subGraph2);
		
		System.out.println("graph QPrime = ");
		printSubgraph(QPrime, subGraph2);
		
		
		printGraph(g2.graph);
		int[] subGraph1 = {2, 3, 4, 5};
		List<Vertex> GPrime = g2.getSubgraph(subGraph1);
		
		System.out.println("graph GPrime = ");
		printSubgraph(GPrime, subGraph1);
		
		
		int[] alphaP = getalphaPrime(subGraph2,alpha, subGraph1);
		System.out.println("alphaP = " + Arrays.toString(alphaP));
		
		Graph QPrime_1 = doIsomorphism(alphaP, GPrime);
		printGraph(QPrime_1.graph);
				
	}

}

class Vertex {

	List<Integer> v = new ArrayList<Integer>();
	int nodeID;

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
	
	void sort() {
		Collections.sort(v);	
		
	}
	
	int getIndex(int element) {
		for (int i = 0; i < v.size(); i++) {
			if(v.get(i) == element) {
				return i;
			}
		}
		return -1;
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
