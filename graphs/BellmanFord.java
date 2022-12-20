package graphs;


public class BellmanFord {

	public static void main(String[] args) {

		Graph g = Graph.exampleBellman();
		
		
		Graph g1 = Graph.exampleNegativeCycle();
		
		// A --> G: COST 1
		
		try {
			Bellman_Output(g);
		} catch (BellmanException bException) {
			System.out.println(bException.getMessage());
		}
		
		try {
			Bellman_Output(g1);
		} catch (BellmanException bException) {
			System.out.println(bException.getMessage());
		}

	}
	
	public static void Bellman_Output(Graph g) throws BellmanException {
		Bellman_Output(g, 0);
	}
	
	public static void Bellman_Output(Graph g, int start) throws BellmanException {
		System.out.println(String.format("--- BELLMAN (from %s) ---", g.nodes.get(start).toStringShort()));
		
		// init
		for (Node allNodes : g.nodes) allNodes.comparableCost = Integer.MAX_VALUE/3;
		g.nodes.get(start).comparableCost = 0;
		
		// loop
		for (int reps=0; reps<g.n-1; reps++) {
			for (Edge relaxEdge : g.getAllEdges()) {
				relaxEdge.to.comparableCost = Math.min(relaxEdge.to.comparableCost, relaxEdge.from.comparableCost+relaxEdge.weight);
			}
		}
		
		// check for negative cycles
		for (Edge relaxEdge : g.getAllEdges()) {
			if (relaxEdge.to.comparableCost > relaxEdge.from.comparableCost+relaxEdge.weight) {
				throw new BellmanException("Negative cycle detected");
			}
		}
		
		System.out.println(g);
		
	}

}

class BellmanException extends Exception {
	private static final long serialVersionUID = 8239513543584492449L;

	public BellmanException(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		return "Bellman Exception: " + super.getMessage();
	}
}
