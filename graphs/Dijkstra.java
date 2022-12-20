package graphs;

import data_structure.PriorityQueue;

public class Dijkstra {

	public static void main(String[] args) {
		
		Graph g = Graph.exampleDijkstra1();
		
		Dijkstra_Output(g);

	}
	
	public static void Dijkstra_Output(Graph g) {
		Dijkstra_Output(g, 0);
	}
	
	public static void Dijkstra_Output(Graph g, int start) {
		
		System.out.println(String.format("--- DIJKSTRA (from %s) ---", g.nodes.get(start).toStringShort()));
		
		PriorityQueue<Node> pQueue = new PriorityQueue<>();
		for (Node n : g.nodes) {
			n.comparableCost = Integer.MAX_VALUE/3;
			n.marked = false;
		}
		
		Node startNode = g.nodes.get(start);
		startNode.comparableCost = 0;
		pQueue.offer(startNode);
		
		while (!pQueue.isEmpty()) {
			
			Node node = pQueue.pop();
			if (node.marked) continue;
			node.marked = true;
//			System.out.println("Discovered: " + node + " " + node.comparableCost);
			
			for (Edge nEdge : node.nEdges) {
				int newCost = node.comparableCost + nEdge.weight;		// DIFFERENCE TO PRIM
				if (!nEdge.to.marked && newCost < nEdge.to.comparableCost) {
					nEdge.to.comparableCost = newCost;
					pQueue.offer(nEdge.to);
					
				}
			}
			
		}
		
		for (Node n : g.nodes) {
			int cost = n.comparableCost + n.johnsonAdditionalCost - startNode.johnsonAdditionalCost;// also works for johnson
			
			System.out.print(n + " w.COST " + (n.comparableCost==Integer.MAX_VALUE/3 ? "∞" : cost) + " ");
		}
		System.out.println();
	}

}
