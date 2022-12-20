package graphs;

public class FloydWarshall {

	public static void main(String[] args) {

		Graph g = new Graph(4, true, true);
		
		g.addDirected(0,2,1);
		g.addDirected(0,3,1);
		g.addDirected(1,3,1);
		g.addDirected(2,0,1);
		g.addDirected(2,1,1);
		g.addDirected(3,2,1);
		
		Warshall_Output(g);

	}
	
	public static void Warshall_Output(Graph g) {
		
		int[][] dp = new int[g.n][g.n];
		
		// init
		for (int j=0; j<g.n; j++) {for (int k=0; k<g.n; k++) {
			dp[j][k] = Integer.MAX_VALUE/3;
			
		}}
		for (Edge e : g.getAllEdges()) {
			dp[e.from.id][e.to.id] = e.weight;
		}
		for (int diag=0; diag<g.n; diag++) {
			dp[diag][diag] = 0;
		}


		printDP(dp, 0);
		
		
		
		for (int i=1; i<=g.n; i++) {
			for (int j=0; j<g.n; j++) {
				for (int k=0; k<g.n; k++) {
					
					dp[j][k] = Math.min(dp[j][k], dp[j][i-1]+dp[i-1][k]);
				}
			}
			
			printDP(dp, i);
		}
		
		
		
	}
	
	private static void printDP(int[][] dp, int iter) {
		System.out.println("\ni = " + iter);
		for (int i=0; i<dp.length; i++) {
			for (int j=0; j<dp.length; j++) {

				System.out.print((dp[i][j]==Integer.MAX_VALUE/3 ? "∞" : dp[i][j]) + " ");
				
			}
			System.out.println();
		}
	}

}
