import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Main {
	
	/*
	 * Main method
	 */
	public static void main(String[] args) {
		/*
		 * As seen in written protocol:
		 * G, G2 are public
		 * G' is isomorphic to G and subgraph of G2 (is private to prover, GIVEN)
		 * Q is isomorphic to G2
		 * Q' is isomorphic to G' and a subgraph of Q
		 */
		Graph G, G2, GPrime, Q, QPrime;
		
		/*
		 * As seen in written protocol:
		 * -Alpha: G2 -> Q
		 * -Beta:  G' -> Q'
		 * -Gamma: G  -> G'	(is private to prover, GIVEN)
		 */
		Isomorphism alpha, beta, gamma;
		
		/*
		 * Hashed version of Q adjacency matrix
		 */
		Commitment Qcommit;
		
		/*
		 * Generate isomorphism Alpha from G2
		 * Generate isomorphism Beta from G'
		 * Generate graph Q by applying Alpha to G2
		 * Generate subgraph Q' by applying Beta to G'
		 * Generate Q commitment by hashing Q adjacency matrix
		 */
		alpha 	= new Isomorphism(G2);
		beta	= new Isomorphism(GPrime);
		Q		= new Graph(G2, alpha);
		QPrime	= new Graph(GPrime, beta);
		try {
			Qcommit	= Q.hash();
		} catch (NoSuchAlgorithmException e1) {
			System.out.println("Q.hash(): NoSuchAlgorithmException");
			e1.printStackTrace();
			return;
		}
		
		
		
		
		
		
		
		/*
		 * Testing implemented methods
		 */
		
		Graph test = new Graph();
		boolean testAdjacencyMatrix[][] = { {true, true,  true,  true},
							   				{true, true,  false, false},
							   				{true, false, true,  true},
							   				{true, false, true,  true}};		
		test.adjacencyMatrix = testAdjacencyMatrix;
		test.size = 4;
		
		Isomorphism test1 = new Isomorphism();
		int testIsomorphism[] = {3, 1, 0, 2};
		test1.isomorphism = testIsomorphism;
		test1.size = 4;
		
		Graph isoG = test.applyIsomorphism(test1);
		System.out.println("------------------------");
		System.out.println("Testing the application of isomorphism");
		System.out.printf("Isomorphism: ");
		test1.printIsomorphism();
		System.out.println();
		System.out.println("Original");
		test.printGraph();
		System.out.println("New");
		isoG.printGraph();
		System.out.println("------------------------");
		
		testIsomorphism();
		System.out.println("------------------------");
		
		try {
			testHashing(test);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Print ten isomorphisms of varying size
	 */
	public static void testIsomorphism() {
		System.out.printf("Testing 10 isomorphisms ranging in size from 10 to 20:\n");
		for (int i = 0; i < 10; i++) {
			Random random = new Random();
			Isomorphism test = new Isomorphism(random.nextInt(20 - 10 + 1) + 10);
			test.printIsomorphism();
		}
		System.out.printf("\n\n");
	}
	
	/*
	 * Test the hashing functionality
	 */
	public static void testHashing(Graph G) throws NoSuchAlgorithmException {
		System.out.println("Testing Hashing Functionality");
		Commitment commit = G.hash();
		commit.printCommit();
	}
}
