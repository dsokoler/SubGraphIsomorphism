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
		 * Generate isomorphism Alpha from G2
		 * Generate isomorphism Beta from G'
		 * Generate graph Q by applying Alpha to G2
		 * Generate subgraph Q' by applying Beta to G'
		 */
		
		testIsomorphism();
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
	}
}
