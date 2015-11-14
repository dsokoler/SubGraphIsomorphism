import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class Main {
	
	/*
	 * Main method
	 */
	public static void main(String[] args) {
		/*
		 * As seen in written protocol:
		 * G1, G2 are public (GIVEN)
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
		 * Generate isomorphism Beta from G' & alpha (Beta is the relevant parts of alpha as compared to G')
		 * Generate graph Q by applying Alpha to G2
		 * Generate subgraph Q' by applying Beta to G'
		 * Generate Q commitment by hashing Q adjacency matrix
		 * 
		 * Send commitment of Q to verifier
		 * 
		 * Receive Challenge:
		 * -0: send alpha and Q
		 * -1: send the combined gamma and beta, reveal subgraph in Q (only unhash subgraph nodes)
		 * -else: fail
		 */
		
		
		
		
		
		/*
		 * Verifier Protocol, picks up where prover left off
		 * Challenge:
		 * 0: hash received graph (Q) and compare to that of commitment
		 *  : apply alpha to the public G2 and compare to the received Q
		 *  
		 * 1: hash the revealed entries in the received graph (Q) and compare to those of the commitment
		 *  : apply the gamma/beta combo received G1 and confirm it matches the subgraph revealed in the sent Q
		 */
		
		
		
		
		
		
		
		/*
		 * Testing implemented methods
		 */
		
		/*Graph test = new Graph();
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
		System.out.println("Testing the combining of Isomorphisms");
		int testIsomorphism1[] = {3, 2, 1, 0};
		int testIsomorphism2[] = {2, 3, 0, 1};
		Isomorphism test2 = new Isomorphism();
		Isomorphism test3 = new Isomorphism();
		test2.isomorphism = testIsomorphism1;
		test2.size = 4;
		test3.isomorphism = testIsomorphism2;
		test3.size = 4;
		Isomorphism combined = new Isomorphism(test2, test3);
		printIntArray(test2.isomorphism);
		System.out.printf(" + ");
		printIntArray(test3.isomorphism);
		System.out.printf(" = ");
		combined.printIsomorphism();
		System.out.printf("\nNewPos = a2[a1[N]]\n");
		System.out.println("------------------------");
		testIsomorphism();
		System.out.println("------------------------");
		Graph isoGSubgraph = isoG.generateSubgraph(3);
		printIntArray(isoG.subgraph);
		System.out.println();
		isoGSubgraph.printGraph();
		System.out.println("------------------------");
		
		try {
			testHashing(test);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		Graph G2test = new Graph();
		boolean testAdjacencyMatrix[][] = 
			{
				{true,  true,  false, false, true,  false, false, false},
				{true,  true,  false, true,  true,  false, false, true},
				{false, false, true,  true,  true,  false, true,  true},
				{false, true,  true,  true,  false, true,  true,  false},
				{true,  true,  true,  false, true,  true,  false, false},
				{false, false, false, true,  true,  true,  false, false},
				{false, false, true,  true,  false, false, true,  true},
				{false, true,  true,  false, false, false, true,  true}
			};
		G2test.adjacencyMatrix = testAdjacencyMatrix;
		G2test.size = 8;
		int[] G2toGPrime = {0, 1, 2, 3, 4};
		G2test.subgraph = G2toGPrime;
		G2test.subgraphSize = 5;
		
		Graph GprimeTest = new Graph();
		boolean testAdjacencyMatrix2[][] = 
			{
				{true,  true,  false, false, true},
				{true,  true,  false, true,  true},
				{false, false, true,  true,  true},
				{false, true,  true,  true,  false},
				{true,  true,  true,  false, true}
			};
		GprimeTest.adjacencyMatrix = testAdjacencyMatrix2;
		GprimeTest.size = 5;
		
		
		
		System.out.println("------------------------------------");
		System.out.println("The following two graphs should be identical:");
		Graph subgraphTest = G2test.generateSubgraph();
		subgraphTest.printGraph();
		GprimeTest.printGraph();
		if ( Arrays.deepEquals(subgraphTest.adjacencyMatrix, GprimeTest.adjacencyMatrix) ) {
			System.out.println("IDENTICAL");
		}
		else {
			System.out.println("DIFFERENT");
		}
		System.out.println("------------------------------------");
		
		
		
		Isomorphism alphaTest = new Isomorphism();
		int[] alphaTestIsomorphism = {2, 4, 1, 6, 5, 7, 3, 0};
		alphaTest.isomorphism = alphaTestIsomorphism;
		alphaTest.size = 8;
		
		Graph Qtest = G2test.applyIsomorphism(alphaTest);
		alphaTest.printIsomorphism();
		G2test.printGraph();
		Qtest.printGraph();
		
		//At this point we have G2, G', alpha, and Q
		
		Graph QprimeTest = Qtest.generateSubgraph(5);
		Isomorphism alphaPrime = Qtest.generateSubIsomorphism(alphaTest);
		alphaPrime.printIsomorphism();
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
	
	public static void printIntArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.printf("[%d]", array[i]);
		}
	}
}
