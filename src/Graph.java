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
	
	public Commitment commit() throws NoSuchAlgorithmException {
		return new Commitment(this.adjacencyMatrix);
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
	 * Used for verifier to check the full graph they received is the same as the
	 * commitment they received earlier
	 */
	public boolean verifyCommitment(Commitment commit) {
		Commitment verify = new Commitment(this.adjacencyMatrix);
		
		return Arrays.deepEquals(verify.commit, commit.commit);
	}
	
	/*
	 * Used for verifier to check the subgraph they received is within
	 * the commitment they received earlier
	 */
	public boolean verifyCommitment(Commitment commit, int[] nodes) {
		
		return true;
	}

	public static void main(String[] args) {
		boolean adjMat[][] = 
			{
				{true,  true,  false, false, true,  false, false, false},
				{true,  true,  false, true,  true,  false, false, true},
				{false, false, true,  true,  true,  false, true,  true},
				{false, true,  true,  true,  false, true,  true,  false},
				{true,  true,  true,  false, true,  true,  false, false},
				{false, false, false, true,  true,  true,  true, false},
				{false, false, true,  true,  false, false, true,  true},
				{false, true,  true,  false, false, false, true,  true}
			};

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