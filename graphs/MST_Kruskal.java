package graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class MST_Kruskal {

	public static void main(String[] args) {
		
		Graph g = Graph.exampleMST1();
		
		Kruskal_output(g);

	}
	
	public static void Kruskal_output(Graph g) {
		// INIT some stuff
		Set<Edge> certainEdges = new HashSet<>();
		UnionFind union = new UnionFind(g.n);
		ArrayList<Edge> allEdges = new ArrayList<>();
		
		// FILL & SORT allEdges
		for (Node n : g.nodes) allEdges.addAll(n.nEdges);
		Collections.sort(allEdges, Edge.Comparators.WEIGHT);
		
		// PICK edges from lowest to highest weight
		for (Edge e : allEdges) {
			if (union.find(e.from.id) != union.find(e.to.id)) {
				System.out.println(certainEdges);
				certainEdges.add(e);					// select this edge (connects 2 ZHKs)
				union.union(e.from.id, e.to.id);		// do your UnionFind magic to connect the ZHKs fast
			}
		}
		
		System.out.println(certainEdges);
		
	}

}
