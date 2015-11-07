
public class Main {
	
	/*
	 * Main method
	 */
	public static void main(String[] args) {
		/*
		 * As seen in written protocol:
		 * G, G2 are public
		 * G' is isomorphic to G and subgraph of G2 (is private to prover)
		 * Q is isomorphic to G2
		 * Q' is isomorphic to G' and a subgraph of Q
		 */
		Graph G, G2, GPrime, Q, QPrime;
		
		/*
		 * As seen in written protocol:
		 * -Alpha: G2 -> Q
		 * -Beta:  G' -> Q'
		 * -Gamma: G  -> G'	(is private to prover)
		 */
		Isomorphism alpha, beta, gamma;
	}
}
