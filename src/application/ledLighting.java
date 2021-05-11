package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ledLighting {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner in = new Scanner(file);
		int number = in.nextInt();
		in.nextLine();
		String str = in.nextLine();
		System.out.println("LED numbers: "+str);
		int[] B = Arrays.stream(str.split(" ")).mapToInt(Integer::parseInt).toArray();
		int[] A = new int[number];
		for(int i=1;i<=number;i++) {
			A[i-1]=i;
		}
		
		int m = A.length;
		int n = B.length;
		int[][] dp = new int[m+1][n+1];

		Set<Integer> hash_Set = new HashSet<Integer>(); 
		for(int i=1;i<m+1;++i) {
			for(int j=1;j<n+1;++j) {
				if(A[i-1] == B[j-1]) {
					dp[i][j] = 1+dp[i-1][j-1];
					hash_Set.add(dp[i][j]);
				}
				else {
					dp[i][j] = Math.max(dp[i][j-1],dp[i-1][j]);
				}
			}
		}
		System.out.println("Max numbers of LEDS ON = "+dp[m][n]);
		System.out.println("LEDs ON are: "+hash_Set+"\n");
		System.out.println("Solution Table: ");
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				System.out.print(dp[i][j]+" ");
			}
			System.out.println();
		}
	}
}
