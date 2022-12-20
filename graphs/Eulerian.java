package graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Eulerian {

	public static void main(String[] args) {

		
		for (int i=0; i<=5; i++) {
			GraphForEuler g = GraphForEuler.fullyConnectedGraph(i);
			System.out.println("Trail with " + i + " nodes: " + eulerianTrailExists(g));
			System.out.println("Circuit with " + i + " nodes: " + eulerianCircuitExists(g));
		}
		
		
		GraphForEuler g = GraphForEuler.exampleGraph1();
		System.out.println("Example1 (trail): " + eulerianTrailExists(g));
		System.out.println("Example1 (circuit): " + eulerianCircuitExists(g));
		
		
		System.out.println("Example1 (trail): " + eulerianCircuitEdges(g));

	}
	
	public static boolean eulerianTrailExists(GraphForEuler g) {
		
		int countUngerade = 0;
		for (AdjaList adja : g.edges) {
			countUngerade += adja.size() % 2;
		}
		return countUngerade <= 2;
	}
	
	public static boolean eulerianCircuitExists(GraphForEuler g) {
		
		for (AdjaList adja : g.edges) {
			if (adja.size() % 2 == 1) return false;
		}
		
		
		return true;
	}
	
	public static List<EdgeForEuler> eulerianCircuitEdges(GraphForEuler g) {
		ArrayList<EdgeForEuler> eulerEdges = new ArrayList<EdgeForEuler>();
		
		ArrayList<Integer> insertionPoint = new ArrayList<>();
		
		for (AdjaList adja : g.edges) {
			if (adja.size()%2 == 1) {
				EdgeForEuler toRemove = adja.get(adja.size()-1);
				g.edges[toRemove.from].remove(toRemove);
				g.edges[toRemove.to].remove(new EdgeForEuler(toRemove.to, toRemove.from, toRemove.weight));
				eulerEdges.add(toRemove);
				break;
			}
		}
		
		do {
			
			
			int chooseIndex;
			if (insertionPoint.isEmpty()) chooseIndex = eulerEdges.size()-1;
			else {
				chooseIndex = insertionPoint.remove(insertionPoint.size()-1);
			}
			
			System.out.println(eulerEdges);
			
			
			while (g.edges[eulerEdges.get(chooseIndex).to].size() > 0) {
				
				AdjaList adja = g.edges[eulerEdges.get(chooseIndex).to];
				if (adja.size() > 1) {
					insertionPoint.add(chooseIndex);
				}
				
				
				EdgeForEuler toRemove = adja.get(adja.size()-1);
				
				g.edges[toRemove.from].remove(toRemove);
				g.edges[toRemove.to].remove(new EdgeForEuler(toRemove.to, toRemove.from, toRemove.weight));
				eulerEdges.add(++chooseIndex, toRemove);
				
				System.out.println(eulerEdges);
			}
			
		} while (!insertionPoint.isEmpty()) ;
		
		
		
		return eulerEdges;
	}

}


class GraphForEuler {
	final int n;
	final AdjaList[] edges;
	

	public static void main(String[] args) {

		GraphForEuler g = fullyConnectedGraph(4);
		
		System.out.println(g);
	}
	
	
	public static GraphForEuler fullyConnectedGraph(int anzNodes) {
		GraphForEuler g = new GraphForEuler(anzNodes);
		
		for (int from=0; from<anzNodes; from++) {
			for (int to=0; to<anzNodes; to++) {
				if (from != to) {
					g.edges[from].add(new EdgeForEuler(from, to));
				}
			}
		}
		
		return g;
	}
	
	public static GraphForEuler exampleGraph1() {
		GraphForEuler g = new GraphForEuler(6);
		
		g.addUndirected(0,1,6);
		g.addUndirected(0,2,5);
		g.addUndirected(1,2,9);
		g.addUndirected(1,3,8);
		g.addUndirected(2,3,7);
		g.addUndirected(2,4,10);
		g.addUndirected(3,4,3);
		g.addUndirected(3,5,1);
		g.addUndirected(4,5,2);
		
		
		
		
		return g;
	}
	
	public static GraphForEuler exampleGraph2() {
		GraphForEuler g = new GraphForEuler(7);
		
		g.addDirected(0, 1, 1);
		g.addDirected(0, 5, 1);
		g.addDirected(0, 4, 1);
		g.addDirected(1, 3, 1);
		g.addDirected(1, 6, 1);
		g.addDirected(2, 1, 1);
		g.addDirected(3, 2, 1);
		g.addDirected(4, 3, 1);
		g.addDirected(4, 5, 1);
		g.addDirected(4, 6, 1);
		
		return g;
	}
	
	public static GraphForEuler exampleGraph3() {
		GraphForEuler g = new GraphForEuler(8);
		
		g.addDirected(0, 1, 1);
		g.addDirected(0, 3, 1);
		g.addDirected(1, 3, 1);
		g.addDirected(1, 4, 1);
		g.addDirected(1, 5, 1);
		g.addDirected(2, 4, 1);
		g.addDirected(2, 5, 1);
		g.addDirected(2, 6, 1);
		g.addDirected(2, 7, 1);
		g.addDirected(3, 2, 1);
		g.addDirected(4, 3, 1);
		g.addDirected(4, 5, 1);
		g.addDirected(7, 5, 1);
		
		return g;
	}
	
	
	
	public GraphForEuler(int anzNodes) {
		n = anzNodes;
		edges = new AdjaList[n];
		for (int i=0;i<n;i++) edges[i] = new AdjaList();
	}
	
	public void addUndirected(int from, int to, int weight) {
		edges[from].add(new EdgeForEuler(from,to,weight));
		edges[to].add(new EdgeForEuler(to,from,weight));
	}
	
	public void addDirected(int from, int to, int weight) {
		edges[from].add(new EdgeForEuler(from,to,weight));
	}
	
	public void resetMarking() {
		for (AdjaList adja : edges) {
			for (EdgeForEuler e : adja) {
				e.marked = false;
			}
		}
	}
	
	@Override
	public String toString() {
		String ret = "Graph (" + n + " nodes):\n";
		int i=0;
		for (AdjaList a : edges) {
			ret += "Node " + i++ + ": " + a + " ";
		}
		
		return ret;
	}

}



class EdgeForEuler implements Comparable<EdgeForEuler>{
	int from;
	int to;
	int weight;
	boolean marked;
	
	public EdgeForEuler(int from, int to) {
		this(from,to,0);
	}
	public EdgeForEuler(int from, int to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
		this.marked = false;
	}
	
	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof EdgeForEuler)) return false;
		EdgeForEuler other = (EdgeForEuler) otherObj;
		return other.from == this.from && other.to == this.to && other.weight == this.weight;
	}
	
	@Override
	public int compareTo(EdgeForEuler other) {
		if (this.from != other.from) return this.from - other.from;
		
		return this.to - other.to;
	}
	
	@Override
	public String toString() {
		return from + "/" + to + "/" + weight;
	}
	
	public static class Comparators {
		
		public static Comparator<EdgeForEuler> REVERSE = new Comparator<EdgeForEuler>() {
			@Override
			public int compare(EdgeForEuler e1, EdgeForEuler e2) {
				return e2.compareTo(e1);
			}
		};
	}
}

class AdjaList extends ArrayList<EdgeForEuler> {
	private static final long serialVersionUID = 1874270753281808528L;
	
}

