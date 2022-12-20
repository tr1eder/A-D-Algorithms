package graphs;

import java.util.HashSet;
import java.util.Set;

import data_structure.PriorityQueue;

public class MST_Prim {

	public static void main(String[] args) {

		Graph g = Graph.exampleMST1();
		
		Prim_output(g);

	}
	
	public static void Prim_output(Graph g) {
		// INIT some stuff
		Set<Edge> certainEdges = new HashSet<>();
		PriorityQueue<Node> pQueue = new PriorityQueue<>();
		
		// choose first node to start from
		pQueue.offer(g.nodes.get(0));
		
		// do sort of Dijkstra
		while (!pQueue.isEmpty()) {
			
			Node node = pQueue.pop();
			if (node.marked) continue;
			
			node.marked = true;
			System.out.println(certainEdges);
			certainEdges.add((Edge)node.obj);
			
			for (Edge e : node.nEdges) {
				e.to.comparableCost = e.weight;			// DIFFERENCE TO DIJKSTRA
				e.to.obj = e;
				pQueue.offer(e.to);
			}
			
		}
		
		System.out.println(certainEdges);
		
		
		
	}

}
