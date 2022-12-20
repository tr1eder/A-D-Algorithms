package graphs;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class DFS {

	public static void main(String[] args) {

		Graph g = Graph.exampleGraph3();
		
		DFS_output(g);

	}
	
	public static void DFS_output(Graph g) {
		
		
		LinkedList<Node> stillOpen = new LinkedList<>();
		stillOpen.offerFirst(g.nodes.get(0));
		
		
		ArrayList<Node> nodesInDFS = new ArrayList<>();

		while (!stillOpen.isEmpty()) {
			Node node = stillOpen.pollFirst();
			
			if (node.marked) continue;
			
			nodesInDFS.add(node);
			
			node.marked = true;
			if (node.nEdges.isEmpty()) continue;
			
			
			Collections.sort(node.nEdges, Edge.Comparators.REVERSE);
			
			for (Edge edge : node.nEdges) {
				if (!edge.to.marked) {
					stillOpen.offerFirst(edge.to);
					edge.to.text = "The edge: " + edge;
					
				}
			}
		}
		
		System.out.println(nodesInDFS);
		for (Node node : nodesInDFS) System.out.print(node.text + " ");
		
		
		
	}

}
