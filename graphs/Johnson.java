package graphs;

public class Johnson {

	public static void main(String[] args) {

//		Graph g3 = new Graph(3, true, true);
//		
//		g3.addDirected(0, 1, -1);
//		g3.addDirected(0, 2, -4);
//		g3.addDirected(2, 1, 2);
//		
//		Johnson_Output(g3);
		
		
		
		Graph gBellman = Graph.exampleBellman();
		
		Johnson_Output(gBellman);
	}
	
	public static void Johnson_Output(Graph g) {
		// Johnson-Graph
		Graph bellmanGraph = new Graph(g.n+1, true, true);
		
		// Add additional vertex
		for (Edge e : g.getAllEdges()) {
			bellmanGraph.addDirected(e.from.id, e.to.id, e.weight);
		}
		for (Node n : bellmanGraph.nodes) {
			if (n.id != g.n) {
				bellmanGraph.addDirected(g.n, n.id, 0);
			}
			else {
				n.marked = true;
				n.name = "NEU";
			}
		}
		
		// Do Bellman-Ford
		try {
			BellmanFord.Bellman_Output(bellmanGraph, g.n);
		} catch (BellmanException e1) {
			e1.printStackTrace();
		}
		
		System.out.println();
		
		// Create new Graph with changed Edge weights
		Graph johnsonGraph = new Graph(g.n, true, true);
		
		for (Edge e : g.getAllEdges()) {
			johnsonGraph.addDirected(e.from.id, e.to.id, e.weight + bellmanGraph.nodes.get(e.from.id).comparableCost - bellmanGraph.nodes.get(e.to.id).comparableCost);
		}
		for (Node n : johnsonGraph.nodes) {
			n.johnsonAdditionalCost = bellmanGraph.nodes.get(n.id).comparableCost;
		}
		
		
		
		
		for (Node node : johnsonGraph.nodes) {
			Dijkstra.Dijkstra_Output(johnsonGraph, node.id);
		}
		
		System.out.println("\n------ JOHNSON TERMINATED ------");
		
	}

}
