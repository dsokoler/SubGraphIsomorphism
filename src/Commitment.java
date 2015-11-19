import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/*
 * Used when committing to an initial graph
 */
public class Commitment implements Serializable{

	//CONSIDER MAKING THIS

	/*
	 * The hashed version of our initial graph's adjacency matrix
	 * 2d array where each entry is an array of bytes
	 */
	byte[] [][] commit;

	/*
	 * The size of our 2d hashed adjacency matrix
	 */
	int size;

	public Commitment(boolean[][] matrix) {
		this.commit = new byte[matrix.length][matrix.length][];
		this.size = matrix.length;
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix.length; j++) {
					byte[] bool  = new byte[1];
					bool[0] = matrix[i][j] ? (byte) 1 : (byte) 0;
					this.commit[i][j] = digest.digest(bool);
					digest.reset();
				}
			}
		} catch(NoSuchAlgorithmException e) {
			System.out.println("SHA-256 does not exist");
			commit = null;
			size = -1;
		}
	}
	
	public boolean verifyCommitment(Graph g) {
		Commitment verify = new Commitment(g.adjacencyMatrix);
		
		return Arrays.deepEquals(this.commit, verify.commit);
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
