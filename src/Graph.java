import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/* 
 * Basic graph class
 */
public class Graph implements Serializable{
	/*
	 * 2d representation of the graph
	 * Index on axis represents an individual node
	 * Coordinate points tell whether the two nodes (X, Y) are connected (True, False)
	 */
	boolean[][] adjacencyMatrix;

	/*
	 * The number of nodes in the graph
	 * G.size == G'.size == Q'.size
	 * G2.size == Q.size
	 */
	int 		size;

	/*
	 * Numbers at each index represent the vertices of the subgraph
	 * Is basically the same as a mapping (index is subgraph's node, value at index is parent's node)
	 */
	int[] subgraph;
	
	/*
	 * 
	 */
	int subgraphSize;

	/*
	 * Graph Constructor:
	 * @param file: file to build the graph out of
	 * File input should be as follows: 
	 *  4 					Number of Vertices
	 *  0 1 2 3			Vertex number followed by all connected nodes separated by 1 space
	 *  1 0 3
	 *  2 0
	 *  3 0 1
	 */
	public Graph(/* FILE/INPUTSTREAM HERE */) {
		//Build and return graph here
	}

	/*
	 * Generate a Graph by applying isomorphism I to graph G
	 * @param G: the graph onto whom the isomorphism is applied
	 * @param I: the isomorphism to apply
	 */
	public Graph (Graph G, Isomorphism I) {
		if (G.size != I.size) {
			this.adjacencyMatrix = null;
			size = -1;
			return;
		}

		this.subgraph = null;
		this.adjacencyMatrix = new boolean[G.size][];
		for (int i = 0; i < G.size; i++) {			//Duplicate old array values into new array values
			this.adjacencyMatrix[i] = G.adjacencyMatrix[i].clone();
		}
		this.size = G.size;

		/*for (int i = 0; i < I.size; i++) {
			int newResident = I.isomorphism[i];
			if (i == newResident) continue;			//We would be moving the values into the same position

			this.adjacencyMatrix[i] = G.adjacencyMatrix[newResident].clone();

			for (int j = 0; j < G.size; j++) {
				this.adjacencyMatrix[j][i] = G.adjacencyMatrix[j][newResident];
			}
		}*/
		
		for (int i = 0; i < I.size; i++) {				//i: row in G
			int newI = I.isomorphism[i];				//newI: new row in G
			for (int j = 0; j < I.size; j++) {			//j: column in G
				int newJ = I.isomorphism[j];			//newJ: new column in G
				this.adjacencyMatrix[newI][newJ] = G.adjacencyMatrix[i][j];
			}
		}
	}

	/*
	 * Generate an unconnected graph of size 'size'
	 * Mainly for testing purposes
	 */
	public Graph (int size) {
		this.adjacencyMatrix = new boolean[size][size];
		this.size = size;
		this.subgraph = null;
	}

	/*
	 * Generates a new graph based on Isomorphism i
	 * Returns null if sizes are not compatible
	 * @param i: the isomorphism to be applied to the graph
	 */
	public Graph applyIsomorphism(Isomorphism I) {
		if (I.size != this.size) {
			return null;
		}

		return new Graph(this, I);
	}

	/*
	 * Generate subgraph Q' from Q
	 * @param size: the intended size of subgraph Q'
	 * 
	 * Randomly choose 'size' nodes from Q to be in Q prime
	 * 
	 * returns a Graph generated from the subgraph array
	 */
	public Graph generateSubgraph(int size) {
		this.subgraph = new int[size];
		this.subgraphSize = size;
		
		boolean[] valSeen = new boolean[this.size];
		
		Random random = new Random();
		
		for (int i = 0; i < this.subgraphSize; i++) {
			int node = random.nextInt(this.size);
			while (valSeen[node] == true) {
				node = random.nextInt(this.size);
			}
			this.subgraph[i] = node;
			valSeen[node] = true;
		}
		
		java.util.Arrays.sort(this.subgraph);
		
		return this.generateSubgraph();
	}
	
	/*
	 * Generate an actual graph object given the parent and the listing of subgraph nodes
	 * @param subgraph: the listing of nodes that comprise the subgraph
	 * 
	 * Parent graph is 'this'
	 */
	public Graph generateSubgraph() {
		Graph Qprime = new Graph(this.subgraphSize);
		
		//i is the index into the subgraph's adjacency matrix
		for (int i = 0; i < this.subgraphSize; i++) {
			int node = this.subgraph[i];	//a node from parent graph that is included in the subgraph
			for (int j = 0; j < this.subgraphSize; j++) {
				if (i == j) {
					Qprime.adjacencyMatrix[i][j] = true;
					continue;
				}
				
				int otherNode = this.subgraph[j];
				Qprime.adjacencyMatrix[i][j] = this.adjacencyMatrix[node][otherNode]; 
			}
				
		}
				
		return Qprime;
	}
	
	/*
	 * 'this' = Q
	 * @param alpha: the large isomorphism
	 * 
	 * returns the relevant portions of alpha when used with Q'
	 */
	public Isomorphism generateSubIsomorphism(Isomorphism alpha) {
		Isomorphism I 	= new Isomorphism();
		I.isomorphism 	= new int[this.subgraphSize];
		I.size 			= this.subgraphSize;
		
		for (int i = 0; i < this.subgraphSize; i++) {
			int mapping = this.subgraph[i];
			int newPos = alpha.isomorphism[mapping];
			System.out.printf("newPos: %d\ti: %d\n", newPos, i);
			I.isomorphism[newPos] = i;
		}
		
		return I;
	}

	public Commitment hash() throws NoSuchAlgorithmException {
		Commitment Q = new Commitment(this.size);
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				digest.reset();
				byte[] booleanBytes = {this.adjacencyMatrix[i][j] ? (byte)1 : (byte)0};
				byte[] input = digest.digest(booleanBytes);

				Q.commit[i][j] = input;
			}
		}
		return Q;
	}

	public void printGraph() {
		System.out.println("Size: " + this.size);
		System.out.printf("   ");
		for (int i = 0; i < this.size; i++) {
			System.out.printf(" %d ", i);
		}
		System.out.println();
		for (int i = 0; i < this.size; i++) {
			System.out.printf(" %d ", i);
			for (int j = 0; j < this.size; j++) {
				if  (this.adjacencyMatrix[i][j] == true) {
					System.out.printf(" T ");
				}
				else {
					System.out.printf(" F ");
				}
			}
			System.out.println();
		}
		System.out.printf("\n\n");
	}
}
