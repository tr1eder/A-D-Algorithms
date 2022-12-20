package graphs;

public class UnionFind {
	int size;
	int[] parent;
	
	public UnionFind(int size) {
		this.size = size;
		this.parent = new int[size];
		
		for (int i=0; i<size; i++) parent[i] = i;
	}
	
	public int find(int id) {
		if (parent[id] == id) return id;
		parent[id] = find(parent[id]);		// magic. path compression for this and all nodes on this path
		return parent[id];					// 		  thus, it doesn't happen too often that we have a very long path
	}
	
	public void union(int v, int u) {
		int rootV = find(v);
		int rootU = find(u);
		
		parent[rootV] = rootU;
	}
	
	@Override
	public String toString() {
		String ret = "UnionFind (" + size + "): ";
		int c=0;
		for (int p : parent) ret += c++ + ":" + p + " ";
		
		return ret;
	}
}
