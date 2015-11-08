
/* 
 * Basic graph class
 */
public class Graph {
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
		
		this.adjacencyMatrix = new boolean[G.size][];
		for (int i = 0; i < G.size; i++) {			//Duplicate old array values into new array values
			this.adjacencyMatrix[i] = G.adjacencyMatrix[i].clone();
		}
		this.size = G.size;
		
		for (int i = 0; i < I.size; i++) {
			int newResident = I.isomorphism[i];
			if (i == newResident) continue;			//We would be moving the values into the same position
			
			this.adjacencyMatrix[i] = G.adjacencyMatrix[newResident].clone();
			
			for (int j = 0; j < G.size; j++) {
				this.adjacencyMatrix[j][i] = G.adjacencyMatrix[j][newResident];
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
	 * Creates a new isomorphism for 
	 * @param G: Graph from which to permute
	 * @param GPrime: Graph who is "parent" of this subgraph.
	 * 
	 * As seen in handwritten protocol:
	 * -Permute G to get subgraph of G'
	 * -Permute G' to get subgraph of Q
	 */
	public Graph generateSubgraph(Graph G, Graph GPrime) {
		
		return null;
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
