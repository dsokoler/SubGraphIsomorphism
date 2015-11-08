import java.util.Random;

public class Isomorphism {
	/*
	 * Each graph (except Q and Q') need an isomorphism to continue the protocol
	 * Is a 1D array where the index represents the old value and the value@index represents the new value
	 */
	int[]		isomorphism;
	
	/*
	 * Generate an isomorphism based on a Graph
	 * Randomly choose new positions for vertices in G
	 * @param G: graph to generate permutation for
	 */
	public Isomorphism(Graph G) {
		this.isomorphism = new int[G.size];
		for (int i = 0; i < G.size; i++) {
			this.isomorphism[i] = i;
		}
		
		Random random = new Random();
		for (int i = 0; i < G.size; i++) {
			int permute = random.nextInt(G.size);
			swap(i, permute);
		}
	}
	
	/*
	 * Generate an isomorphism based on an integer size
	 * Randomly choose new positions for vertices in the range of 0 to size
	 * @param size: size to generate permutation for
	 */
	public Isomorphism(int size) {
		this.isomorphism = new int[size];
		for (int i = 0; i < size; i++) {
			this.isomorphism[i] = i;
		}
		
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			int permute = random.nextInt(size);
			swap(i, permute);
		}
	}
	
	/*
	 * Swap two indices of an array
	 */
	private boolean swap (int one, int two) {
		int temp = this.isomorphism[one];
		this.isomorphism[one] = this.isomorphism[two];
		this.isomorphism[two] = temp;
		
		return true;
	}
	
	/*
	 * Print the isomorphism: mainly for testing purposes
	 */
	public void printIsomorphism() {
		System.out.printf("Size: %d    ", this.isomorphism.length);
		for (int i = 0; i < this.isomorphism.length; i++) {
			System.out.printf("[%d]", this.isomorphism[i]);
		}
		System.out.println();
	}
}
