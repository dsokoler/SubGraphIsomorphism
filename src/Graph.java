
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
}
