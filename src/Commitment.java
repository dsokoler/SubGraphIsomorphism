/*
 * Used when committing to an initial graph
 */
public class Commitment {
	
	/*
	 * The hashed version of our initial graph's adjacency matrix
	 */
	byte[] [][] commit;
	
	/*
	 * The size of our 2d hashed adjacency matrix
	 */
	int size;
	
	public Commitment(int size) {
		this.commit = new byte[size][size][];
		this.size = size;
	}
	
	public void printCommit() {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				System.out.printf("[%d][%d] : ", i, j);
				for (int k = 0; k < this.commit[i][j].length; k++) {
					System.out.printf("%d", this.commit[i][j][k]);
				}
				System.out.println();
			}
		}
	}
}
