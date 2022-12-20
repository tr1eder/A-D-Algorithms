package graphs;

import java.util.HashSet;
import java.util.Set;


public class MST_Boruvka {

	public static void main(String[] args) {
		
		Graph g = Graph.exampleMST1();
		
		Boruvka_output(g);

	}
	
	public static void Boruvka_output(Graph g) {
		
		Set<Edge> certainEdges = new HashSet<>();
		
		UnionFind uFind = new UnionFind(g.n);
		
		
		while (certainEdges.size()+1 < g.n) {
			Edge[] minimalEdges = new Edge[g.n];
			System.out.println(certainEdges);
			
			for (Node n : g.nodes) {
				for (Edge e : n.nEdges) {
					if (uFind.find(e.from.id) != uFind.find(e.to.id)) {
						
						if (minimalEdges[uFind.find(e.from.id)] == null || minimalEdges[uFind.find(e.from.id)].weight > e.weight) {
							minimalEdges[uFind.find(e.from.id)] = e;
						}	
						
					}
				}
			}
			
			for (Edge e : minimalEdges) {
				if (e != null) {
					certainEdges.add(e);
					uFind.union(e.from.id, e.to.id);
				}
			}			
		}
		
		
		System.out.println(certainEdges);
		
		
	}

}
