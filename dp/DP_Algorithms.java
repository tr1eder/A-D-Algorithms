package dp;

import search.Search_BinarySearch;

public class DP_Algorithms {

	public static void main(String[] args) {
		
		new MaxSubarraySum();
		
		new LongestIncreasing();
		
		new LCommonSubstring(true);
		
		new MEditingDist(true);
		
		new SubsetSum(true);
		
		new Knapsack();
		
		new MatrixChain();
	}

}


class MaxSubarraySum {
	
	public MaxSubarraySum() {
		int[] smallEx = new int[] {1, -3, 5, 2, -2, 3};
		// Answer should be: 5+2-2+3 = 8
		
		System.out.println("Max subarray sum: " + MaxSubarraySum.dp_Max(smallEx));
//		System.out.println(MaxSubarraySum.naive_Max(smallEx));
		
	}
	
	public static int dp_Max(int[] arr) {
		
		int[] prefix = new int[arr.length];
		
		prefix[0] = arr[0];
		for (int i=1; i<arr.length; i++) {
			prefix[i] = Math.max(arr[i], prefix[i-1]+arr[i]);
		}
		
		
		
		int maxSum = 0;
		for (int p : prefix) {
			if (p>maxSum) maxSum = p;
		}
		
		return maxSum;
	}
	
	public static int naive_Max(int[] arr) {
		
		int maxSum = 0;
		
		for (int i=0; i<arr.length; i++) {
			int sum = 0;
			for (int j=i; j<arr.length; j++) {
				sum += arr[j];
				if (sum>maxSum) maxSum = sum;
			}
		}
		
		
		return maxSum;
	}
}

class LongestIncreasing {
	public LongestIncreasing() {
		int[] smallEx = new int[] {1,4,3,6,4,3,1,7,9,4,6,7,8,3};
		// Answer should be 1,3,4,6,7,8 = 6
		
		dp_Increasing(smallEx);
	}
	
	public static void dp_Increasing(int[] arr) {
		
		int[] solution = new int[arr.length];
		for (int i=1; i<solution.length; i++) {
			solution[i] = Integer.MAX_VALUE/2;
		}
		
		int maxPos = 0;
		for (int a : arr) {
			int pos = Search_BinarySearch.bin_searchInsertionPoint(solution, a);
			solution[pos] = a;
			if (pos > maxPos) maxPos = pos;
		}
		
		System.out.print("Longest increasing subarray: ");
		for (int i=1; i<=maxPos; i++) {
			System.out.print(solution[i] + ", ");
		}
		System.out.println("a total of: " + maxPos);
		
	}
}

class LCommonSubstring {
	static boolean showSteps;
	
	public LCommonSubstring() {this(false);}
	public LCommonSubstring(boolean withIntermediateSteps) {
		showSteps = withIntermediateSteps;
		char[] A = "tiger".toCharArray();
		char[] B = "tester".toCharArray();
		
		dp_LCommon(A, B);
	}
	
	public static void dp_LCommon(char[] A, char[] B) {
		int nA = A.length;
		int nB = B.length;
		int[][] dp = new int[nA+1][nB+1];
		
		for (int i=0; i<=nA; i++) {
			dp[i][0] = 0;
		}
		for (int i=0; i<=nB; i++) {
			dp[0][i] = 0;
		}
		
		for (int i=1; i<=nA; i++) {
			for (int j=1; j<=nB; j++) {
				dp[i][j] = Math.max(Math.max(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]+(A[i-1]==B[j-1]? 1 : 0));
			}
		}
		
		if (showSteps) {
			System.out.print("\n  _ ");
			for (char b : B) System.out.print(b + " ");
			
			System.out.print("\n_ ");
			
			for (int j=0; j<=nB; j++) {
				System.out.print(dp[0][j] + " ");
			}
			
			System.out.println();
			
			for (int i=1; i<=nA; i++) {
				System.out.print(A[i-1] + " ");
				for (int j=0; j<=nB; j++) {
					System.out.print(dp[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
		
		// Read out
		int i = nA;
		int j = nB;
		String subString = "";
		while (i>0 && j>0) {
			if (dp[i-1][j-1] + 1 == dp[i][j]) {
				subString = String.valueOf(A[i-1]) + subString;
				i--; j--;
			}
			else if (dp[i-1][j] == dp[i][j]) i--;
			else if (dp[i][j-1] == dp[i][j]) j--;
			else if (dp[i-1][j-1] == dp[i][j]) {
				i--; j--;
				System.out.println("interesting");
			}
			else {
				throw new IllegalStateException();
			}
		}
		
		System.out.println("Longest common substring: '" + subString + "' with length: " + dp[nA][nB]);
	}
	
	
}


class MEditingDist {
	static boolean showSteps;
	public MEditingDist() {this(false);}
	public MEditingDist(boolean showSteps) {
		MEditingDist.showSteps = showSteps;
		
		char[] A = "workspace".toCharArray();
		char[] B = "eclipse".toCharArray();
		
		dp_minEditDist(A, B);
	}
	
	public static void dp_minEditDist(char[] A, char[] B) {
		
		int nA = A.length;
		int nB = B.length;
		
		int[][] dp = new int[nA+1][nB+1];
		
		for (int i=0;i<=nA;i++) dp[i][0] = i;
		for (int j=0;j<=nB;j++) dp[0][j] = j;
		
		for (int i=1;i<=nA;i++) {
			for (int j=1;j<=nB;j++) {
				dp[i][j] = Math.min(Math.min(dp[i-1][j]+1, dp[i][j-1]+1), dp[i-1][j-1]+(A[i-1]==B[j-1] ? 0 : 1));
			}
		}
		
		if (showSteps) {
			System.out.print("\n  _ ");
			for (char b : B) System.out.print(b + " ");
			System.out.print("\n_ ");
			for (int first : dp[0]) System.out.print(first + " ");
			
			System.out.println();
			for (int i=1;i<=nA;i++) {
				System.out.print(A[i-1] + " ");
				for (int j=0; j<=nB; j++) {
					System.out.print(dp[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
		
		// Read out
		int i=nA;
		int j=nB;
		String edits = "";
		
		while (i>0 || j>0) {
			if (i>0 && dp[i-1][j]+1==dp[i][j]) {
				edits = "del. " + A[i-1] + ", " + edits;
				i--;
			}
			else if (j>0 && dp[i][j-1]+1==dp[i][j]) {
				edits = "ins. " + B[j-1] + ", " + edits;
				j--;
			}
			else if (i>0 && j>0 && dp[i-1][j-1]+1==dp[i][j]) {
				edits = "repl. " + A[i-1] + "/" + B[j-1] + ", " + edits;
				i--; j--;
			}
			else if (i>0 && j>0 && dp[i-1][j-1]==dp[i][j]) {
				i--; j--;
			}
			else throw new IllegalStateException();
		}
		if (edits.length() > 0) edits = edits.substring(0, edits.length()-2);
		
		
		System.out.println("Minimal editing distance: " + dp[nA][nB] + ", Edits: " + edits);
		
	}
}




class SubsetSum {
	private static boolean showSteps;
	public SubsetSum(boolean show) {
		showSteps = show;
		int[] values = new int[] {2, 3, 2};
		int b = 4;
		
		dp_subsetSum(values, b);
	}
	
	
	public static void dp_subsetSum(int[] set, int subsetVal) {
		int n=set.length;
		
		boolean[][] dp = new boolean[n+1][subsetVal+1];
		
		for (int j=0; j<=subsetVal; j++) dp[0][j] = false;
		for (int i=0; i<=n; i++) dp[i][0] = true;
		
		for (int i=1; i<=n; i++) {
			for (int j=0; j<=subsetVal; j++) {
				
				if (j>=set[i-1]) {
					dp[i][j] = dp[i-1][j] || dp[i-1][j-set[i-1]];
				}
				else {
					dp[i][j] = dp[i-1][j];
				}
				
			}
		}
		
		
		if (showSteps) {
			
			System.out.print("\n     ");
			for (int j=0; j<=subsetVal; j++) System.out.print(String.format("%2d", j));
			System.out.print("\n  _ | ");
			for (int j=0; j<=subsetVal; j++) System.out.print(dp[0][j] ? "T " : "F ");
			
			for (int i=1; i<=n; i++) {
				System.out.print(String.format("\n%3d | ", set[i-1]));
				for (int j=0; j<=subsetVal; j++) {
					System.out.print(dp[i][j] ? "T " : "F ");
				}
			}
			
			System.out.print("\n\n");
			
		}
		
		if (dp[n][subsetVal]) {
			// Read out
			String takeVals = "";
			int bBack = subsetVal;
			int row = n;
			
			while (bBack > 0) {
				if (dp[row-1][bBack]) {
					row--;
				}
				else if (dp[row-1][bBack-set[row-1]]) {
					takeVals = set[row-1] + " " + takeVals;
					bBack -= set[row-1];
					row--;
				}
				
				else throw new IllegalStateException();
			}
			
			
			System.out.println("Subset sum: " + subsetVal + ". With values: " + takeVals);
		}
		else {
			System.out.println("No subset sum with " + subsetVal);
		}
	
	}
}


class Knapsack {
	
	public Knapsack() {
		
		int[] values = new int[] {4, 5, 1, 6, 3};
		int[] weights = new int[] {3, 4, 1, 3, 2};
		int maxWeight = 9;
		int maxValue = 14;
		
		int[] valuesForApprox = new int[] {4001, 5123, 953, 6042, 2912};
		int[] weightsForApprox = new int[] {3033, 4321, 1000, 3192, 1999};
		int maxWeightForApprox = 9000;
		int maxValueForApprox = 14000;
		
		
		dp_knapsackWeightRestraint(values, weights, maxWeight);
		dp_knapsackValueRestraint(values, weights, maxValue);
		dp_knapsackValueRestraint(valuesForApprox, weightsForApprox, maxValueForApprox);
		
		dp_knapsackWeightRestraint_weightDP(values, weights, maxWeight);
		dp_knapsackWeightRestraint_weightDP(valuesForApprox, weightsForApprox, maxWeightForApprox);
		
		dp_knapsackApprox(valuesForApprox, weightsForApprox, maxValueForApprox, 0.4);
		dp_knapsackApprox(valuesForApprox, weightsForApprox, maxValueForApprox, 0.15);
		dp_knapsackApprox(valuesForApprox, weightsForApprox, maxValueForApprox, 0.01);
		dp_knapsackApprox(valuesForApprox, weightsForApprox, maxValueForApprox, 0.0001);
	}
	
	public static void dp_knapsackWeightRestraint(int[] values, int[] weights, int maxWeight) {
		int n = values.length;
		int[][] dp = new int[n+1][maxWeight+1];
		
		for (int i=0; i<=n; i++) dp[i][0] = 0;
		for (int j=0; j<=maxWeight; j++) dp[0][j] = 0;
		
		for (int i=1; i<=n; i++) {
			for (int j=1; j<=maxWeight; j++) {
				if (j>=weights[i-1]) {
					dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-weights[i-1]] + values[i-1]);
				}
				else {
					dp[i][j] = dp[i-1][j];
				}
			}
		}
		
		
		if (n < 15 && maxWeight < 40) {
			System.out.print("\n w  v |");
			for (int j=0; j<=maxWeight; j++) System.out.print(String.format("%3d", j));
			System.out.print("\n_" + "___".repeat(maxWeight+3));
			System.out.print("\n _  _ |");
			for (int wert : dp[0]) System.out.print(String.format("%3d", wert));
			
			for (int i=1; i<=n; i++) {
				System.out.print(String.format("\n%2d %2d |", weights[i-1], values[i-1]));
				for (int j=0; j<=maxWeight; j++) {
					System.out.print(String.format("%3d", dp[i][j]));
				}
			}
			System.out.print("\n\n");
		}
		
		// Read out
		String readout = "";
		
		int row = n;
		int column = maxWeight;
		
		while (column > 0 && row > 0) {
			if (dp[row-1][column]==dp[row][column]) row--;
			else if (dp[row-1][column-weights[row-1]]+values[row-1]==dp[row][column]) {
				row--;
				column -= weights[row];
				readout = weights[row] + "/" + values[row] + " " + readout;
			}
			else throw new IllegalStateException();
		}
		
		System.out.println("Knapsack (maxWeight, DP: values): Max value of " + dp[n][maxWeight] + " for weight " + maxWeight + ". Take items: " + readout);
	}
	
	
	
	public static void dp_knapsackWeightRestraint_weightDP(int[] values, int[] weights, int maxWeight) {
		int maxDP = 0; // total summed up value
		for (int v : values) maxDP += v;
		int n = values.length;
		
		int[][] dp = new int[n+1][maxDP+1];
		
				
		for (int j=1; j<=maxDP; j++) dp[0][j] = Integer.MAX_VALUE/3;
		dp[0][0] = 0;
		
		for (int i=1; i<=n; i++) {
			for (int j=0; j<=maxDP; j++) {
				if (j>=values[i-1]) {
					dp[i][j] = Math.min(dp[i-1][j], dp[i-1][j-values[i-1]]+weights[i-1]);
				}
				else {
					dp[i][j] = dp[i-1][j];
				}
			}
		}
		
		if (n < 15 && maxDP < 40) {
			
			System.out.print("\n   w    v |");
			for (int j=0; j<=maxDP; j++) System.out.print(String.format("%5d", j));
			System.out.print("\n---" + "-----".repeat(maxDP+3) + "\n");
			System.out.print("   _    _ |");
			for (int val : dp[0]) System.out.print(val==Integer.MAX_VALUE/3 ? "    ∞" : String.format("%5d", val));
			
			for (int i=1; i<=n; i++) {
				System.out.print(String.format("\n%4d %4d |", weights[i-1], values[i-1]));
				for (int j=0; j<=maxDP; j++) {
					System.out.print(dp[i][j]==Integer.MAX_VALUE/3 ? "    ∞" : String.format("%5d", dp[i][j]));
				}
			}
			System.out.println("\n");
		}
		
		// Readout
		String readout = "";
		int row = n;
		int column = maxDP+1;
		while (dp[row][--column] > maxWeight) {}
		
		int columnSolution = column;
		
		while (column>0) {
			if (dp[row-1][column]==dp[row][column]) row--;
			else if (dp[row-1][column-values[row-1]]+weights[row-1]==dp[row][column]) {
				row--;
				column -= values[row];
				readout = weights[row] + "/" + values[row] + " " + readout;
			}
			else throw new IllegalStateException();
		}
		
		System.out.println("Knapsack (maxWeight, DP: weights): Max value of " + columnSolution + " for weight " + maxWeight + ". Take items: " + readout);
				
	}
	
	
	public static void dp_knapsackValueRestraint(int[] values, int[] weights, int maxValue) {
		
		int n = values.length;
		int maxDP = 0;
		int c0=0;
		while (maxDP < maxValue) {
			if (c0+1 < n) maxDP += values[c0++]; 
			else throw new IllegalStateException();
		}
		
		
		
		int[][] dp = new int[n+1][maxDP+1];
		
		for (int j=1; j<=maxDP; j++) dp[0][j] = Integer.MAX_VALUE/3;
		dp[0][0] = 0;
		
		for (int i=1; i<=n; i++) {
			for (int j=0; j<=maxDP; j++) {
				if (j>=values[i-1]) {
					dp[i][j] = Math.min(dp[i-1][j], dp[i-1][j-values[i-1]]+weights[i-1]);
				}
				else {
					dp[i][j] = dp[i-1][j];
				}
			}
		}
		
		if (n < 15 && maxValue < 40) {
			
			System.out.print("\n w  v |");
			for (int j=0; j<=maxDP; j++) System.out.print(String.format("%3d", j));
			System.out.print("\n-" + "---".repeat(maxDP+3) + "\n");
			System.out.print(" _  _ |");
			for (int val : dp[0]) System.out.print(val==Integer.MAX_VALUE/3 ? "  ∞" : String.format("%3d", val));
			
			for (int i=1; i<=n; i++) {
				System.out.print(String.format("\n%2d %2d |", weights[i-1], values[i-1]));
				for (int j=0; j<=maxDP; j++) {
					System.out.print(dp[i][j]==Integer.MAX_VALUE/3 ? "  ∞" : String.format("%3d", dp[i][j]));
				}
			}
			System.out.println("\n");
		}
		
		// Readout
		
		String readout = "";
		int row = n;
		int column = maxValue-1;
		while (dp[row][++column]==Integer.MAX_VALUE/3) {}
		
		int columnSolution = column;
		
		while (column>0) {
			if (dp[row-1][column]==dp[row][column]) row--;
			else if (dp[row-1][column-values[row-1]]+weights[row-1]==dp[row][column]) {
				row--;
				column -= values[row];
				readout = weights[row] + "/" + values[row] + " " + readout;
			}
			else throw new IllegalStateException();
		}
		
		System.out.println("Knapsack (minValue, DP: weights): Min weight of " + dp[n][columnSolution] + " for value " + maxValue + " (" + columnSolution + ")" + ". Take items: " + readout);
	}
	
	
	public static void dp_knapsackApprox(int[] values, int[] weights, int maxWeight, double epsilon) {
		
		int n = values.length;
		int vMax = 0;
		for (int i=0; i<n; i++) {
			if (weights[i] <= maxWeight && values[i] > vMax) vMax = values[i];
		}
		
		double K = epsilon * vMax / n;
		
		int[] valuesRounded = new int[values.length];
		
		int c0=0;
		for (int v : values) valuesRounded[c0++] = (int) Math.floor(v / K);
		
		dp_knapsackWeightRestraint_weightDP(valuesRounded, weights, maxWeight);
		System.out.println("--> rounded response for an epsilon of " + epsilon);
	}
}



class MatrixChain {
	public MatrixChain() {
		
		int[][] matrices = new int[][] {{3,1}, {1,4}, {4,2}, {2,5}};
		
		dp_matrixChainMult(matrices);
	}
	
	public static void dp_matrixChainMult(int[][] matrices) {
		
		int n = matrices.length;
		
		int[][] dp = new int[n][n];
		
		for (int i=0; i<n; i++) dp[i][i] = 0;
		
		for (int diff=1; diff<n; diff++) {
			for (int start=diff; start<n; start++) {
				int row = start-diff;
				int col = start;
				
				int min = Integer.MAX_VALUE/3;
				
				for (int lastMult=row; lastMult<col; lastMult++) {
					
					int firstPart = dp[row][lastMult];
					int lastPart = dp[lastMult+1][col];
					int theMult = matrices[row][0] * matrices[lastMult][1] * matrices[col][1];
					
					min = Math.min(min, firstPart+lastPart+theMult);
					
				}
				
				dp[row][col] = min;
			}
		}
		
		
		// Show
		if (n < 15) {
			
			System.out.print("\n    │");
			for (int[] matrix : matrices) System.out.print(String.format(" %1dx%1d", matrix[0], matrix[1])); //"[" + matrix[0] + "x" + matrix[1] + "]");
			System.out.print("\n────┼" + "────".repeat(n));
			
			for (int i=0; i<n; i++) {
				System.out.print("\n" + String.format("%1dx%1d │", matrices[i][0], matrices[i][1]));
				for (int j=0; j<n; j++) {
					
					System.out.print(String.format("%4d", dp[i][j]));
					
				}
			}
			
			System.out.println("\n");
			
		}
		
		// Read out
		
		String readOut = bracketsReadout(dp, matrices, 0, n-1);
		
		System.out.println("Matrix chain multiplication: Least multiplications with best bracketing is " + dp[0][n-1] + ". Brackets are: " + readOut);
		
		
	}
	
	private static String bracketsReadout(int[][] dp, int[][] matrices, int row, int col) {
		
		if (row==col) return "["+matrices[row][0]+"x"+matrices[row][1]+"]";
		
		for (int lastMult=row; lastMult<col; lastMult++) {

			int firstPart = dp[row][lastMult];
			int lastPart = dp[lastMult+1][col];
			int theMult = matrices[row][0] * matrices[lastMult][1] * matrices[col][1];
			
			// found the right cut
			if (firstPart + lastPart + theMult == dp[row][col]) {
				
				String leftSide = bracketsReadout(dp, matrices, row, lastMult);
				String rightSide = bracketsReadout(dp, matrices, lastMult+1, col);
				
				return "("+leftSide+"x"+rightSide+")";
				
			}
			
		}
		
		throw new IllegalStateException();
	}
}





















