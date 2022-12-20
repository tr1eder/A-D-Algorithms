package graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;


public class BFS {

	public static void main(String[] args) {

		Graph g = Graph.exampleGraph3();
		BFS_output(g);

	}
	
	public static void BFS_output(Graph g) {
		
		LinkedList<Node> active = new LinkedList<>();
		active.offerLast(g.nodes.get(0));
		
		ArrayList<Node> nodesInBFS = new ArrayList<>();
		
		while (!active.isEmpty()) {
			Node node = active.pollFirst();
			
			nodesInBFS.add(node);
			if (node.nEdges.isEmpty()) continue;
			
			Collections.sort(node.nEdges);
			
			for (Edge nextEdge : node.nEdges) {
				if (!nextEdge.to.marked) {
					active.offerLast(nextEdge.to);
					nextEdge.to.marked = true;
					nextEdge.to.text = "The edge: " + nextEdge;
				}
			}
		}
		
		System.out.println(nodesInBFS);
		for (Node node : nodesInBFS) System.out.print(node.text + " ");
	}

}
