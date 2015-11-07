
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
	 * Each graph (except Q and Q') need an isomorphism to continue the protocol
	 * Is a 1D array where the index represents the old value and the value@index represents the new value
	 * This value will be null if the graph is Q or Q'
	 */
	int[]		isomorphism;
	
	
	
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
}
