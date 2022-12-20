package graphs;

import java.util.ArrayList;
import java.util.Comparator;

public class Graph {
	final boolean directed;
	final int n;
	final ArrayList<Node> nodes;
	final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	

	public static void main(String[] args) {
		Graph g = exampleMST1();
		
		System.out.println(g);
	}
	
	
	// Eulerian trail
	public static Graph exampleGraph1() {
		Graph g = new Graph(6, false);
		
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
	// DFS / BFS
	public static Graph exampleGraph3() {
		Graph g = new Graph(8, true);
		
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
	
	// MST
	public static Graph exampleMST1() {
		Graph g = new Graph(7, false, true);
		
		g.addUndirected(0, 1, 5);
		g.addUndirected(0, 2, 2);
		g.addUndirected(1, 3, 3);
		g.addUndirected(2, 3, 7);
		g.addUndirected(3, 4, 9);
		g.addUndirected(3, 6,11);
		g.addUndirected(4, 5, 1);
		g.addUndirected(6, 5, 6);
		
		return g;
	}
	
	// Dijkstra
	public static Graph exampleDijkstra1() {
		Graph g = new Graph(7, true, true);
		
		g.addDirected(0, 1, 2);
		g.addDirected(0, 2, 5);
		g.addDirected(0, 3, 3);
		g.addDirected(1, 3, 2);
		g.addDirected(1, 4, 8);
		g.addDirected(2, 5, 3);
		g.addDirected(3, 2, 1);
		g.addDirected(3, 5, 3);
		g.addDirected(4, 3, 1);
		g.addDirected(5, 4, 3);
		g.addDirected(5, 6, 1);
		g.addDirected(6, 4, 1);
		
		return g;
	}
	
	// Bellman-Ford
	public static Graph exampleBellman() {
		Graph g = new Graph(7, true, true);

		g.addDirected(0, 6, 10);
		g.addDirected(0, 4, 4);
		g.addDirected(0, 5, 1);
		g.addDirected(5, 2, 5);
		g.addDirected(5, 6, 5);
		g.addDirected(2, 3, 1);
		g.addDirected(2, 1, 3);
		g.addDirected(3, 4, -1);
		g.addDirected(4, 6, -2);
		g.addDirected(1, 6, -8);
		g.addDirected(6, 3, 4);
		
		return g;
	}
	
	// Bellman-Ford
	public static Graph exampleNegativeCycle() {
		Graph g1 = new Graph(7, true, true);
		g1.addDirected(0, 6, 10);
		g1.addDirected(0, 4, 4);
		g1.addDirected(0, 5, 1);
		g1.addDirected(5, 2, 5);
		g1.addDirected(5, 6, 5);
		g1.addDirected(2, 3, 1);
		g1.addDirected(2, 1, 3);
		g1.addDirected(3, 4, -1);
		g1.addDirected(4, 6, -2);
		g1.addDirected(1, 6, -8);
		g1.addDirected(6, 3, 2);
		
		return g1;
	}
	
	public Graph(int anzNodes, boolean directed, boolean alphabetic) {
		this(anzNodes, directed);
		int c=0;
		for (Node node : nodes) {
			node.name = alphabet.substring(c++, c);
		}
	}
	
	public Graph(int anzNodes, boolean directed) {
		n = anzNodes;
		this.directed = directed;
		nodes = new ArrayList<>();
		Node.__init__(nodes);
		for (int i=0;i<n;i++) nodes.add(new Node(directed));
	}
	
	public void addUndirected(int from, int to, int weight) {
		nodes.get(from).addEdge(to, weight); 
		nodes.get(to).addEdge(from, weight);
	}
	
	public void addDirected(int from, int to, int weight) {
		nodes.get(from).addEdge(to, weight);
	}
	
	public ArrayList<Edge> getAllEdges() {
		ArrayList<Edge> edges = new ArrayList<>();
		
		for (Node n : nodes) {
			edges.addAll(n.nEdges);
		}
		
		
		return edges;
	}
	
	
	
	public void resetMarking() {
		for (Node adja : nodes) {
			for (Edge e : adja.nEdges) {
				e.marked = false;
			}
		}
	}
	
	@Override
	public String toString() {
		String ret = "Graph (" + n + " nodes):\n";
//		int i=0;
		for (Node a : nodes) {
//			ret += "Node " + i++ + ": " + a + " ";
			ret += a.toStringCost() + " ";
		}
		
		return ret;
	}

}



class Edge implements Comparable<Edge>{
	Node from;
	Node to;
	int weight;
	boolean marked;
	
	public Edge(Node from, Node to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
		this.marked = false;
	}
	
	@Override
	public int hashCode() {
		return (this.from.id+this.to.id)*this.weight;
	}
	
	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof Edge)) return false;
		Edge other = (Edge) otherObj;
		if (this.from.directed) return other.from == this.from && other.to == this.to && other.weight == this.weight;
		else return ((other.from==this.from && other.from==this.to) || (other.from==this.to && other.to==this.from)) && other.weight == this.weight;
	}
	
	@Override
	public int compareTo(Edge other) {
		if (this.from != other.from) return this.from.id - other.from.id;
		
		return this.to.id - other.to.id;
	}
	
	public String toStringName() {
		return from.id + "/" + to.id + "/" + weight;
	}
	public String toString() {
		return from.toStringShort() + "/" + to.toStringShort() + "/" + weight;
	}
	
	public static class Comparators {
		
		public static Comparator<Edge> REVERSE = new Comparator<Edge>() {
			@Override
			public int compare(Edge e1, Edge e2) {
				return e2.compareTo(e1);
			}
		};
		
		public static Comparator<Edge> WEIGHT = new Comparator<Edge>() {
			@Override 
			public int compare(Edge e1, Edge e2) {
				return e1.weight - e2.weight;
			}
		};
	}
}

class Node implements Comparable<Node> {
	private static int countID = 0;
	private static ArrayList<Node> nodes;
	
	
	final boolean directed;
	final int id;
	String name;
	int comparableCost;
	int johnsonAdditionalCost;
	
	ArrayList<Edge> nEdges;
	boolean marked;
	
	String text;
	Object obj;
	
	
	public static void __init__(ArrayList<Node> nodes) {
		Node.nodes = nodes;
		Node.countID = 0;
	}
	
	public Node(String name, boolean directed) {
		nEdges = new ArrayList<>();
		id = countID++;
		this.name = name;
		this.marked = false;
		this.directed = directed;
	}
	public Node(boolean directed) {
		this(null, directed);
	}
	
	public void addEdge(int to, int weight) {
		nEdges.add(new Edge(this, nodes.get(to), weight));
	}
	
	
	@Override
	public int compareTo(Node other) {
		return this.comparableCost - other.comparableCost;
	}
	
	@Override 
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof Node)) return false;
		Node other = (Node) otherObj;
		
		return other.id == this.id;
	}
	@Override
	public String toString() {
		return "Node: " + (name==null ? id : name);
	}
	public String toStringShort() {
		return (name==null ? ""+id : name);
	}
	public String toStringLong() {
		return "Node: " + (name==null ? id : name) + " " + nEdges;
	}
	public String toStringCost() {
		return "Node: " + toStringShort() + " w.COST" + ((comparableCost==Integer.MAX_VALUE/3) ?  "∞" : comparableCost);
	}
}
