package data_structure;


public class PriorityQueue<T extends Comparable<T>> {
	
	private Node head;
	private int size;

	public static void main(String[] args) {

		PriorityQueue<Integer> prio = new PriorityQueue<>();
		
		int[] toOffer = new int[] {13, 17, 2, 5, 10, 8, 1};
		
		for (int off : toOffer) {
			prio.offer(off);
		}
		
		
		System.out.println(prio);
		
		System.out.println("Peek: " + prio.peek());
		System.out.println("IsEmpty: " + prio.isEmpty());
		System.out.println("Size: " + prio.size);
		
		System.out.println("Pop: " + prio.pop());
		System.out.println("Pop: " + prio.pop());
		System.out.println("Pop: " + prio.pop());
		System.out.println("Pop: " + prio.pop());
		System.out.println("Pop: " + prio.pop());
		System.out.println("Pop: " + prio.pop());
		System.out.println(prio);
		
		prio.clear();
		System.out.println("Cleared. IsEmpty: " + prio.isEmpty());
		System.out.println(prio);
		
	}
	
	public PriorityQueue() {
		clear();
	}
	
	public void offer(T elem) {
		if (!(elem instanceof Comparable<?>)) throw new IllegalArgumentException();
		
		Node newNode = new Node(elem);
		if (isEmpty()) {head = newNode; size++; return;}
		
		int parentPos = (size-1)/2;
		Node parent = getNodeAtHeap(parentPos);
		
		
		if (size%2==0) parent.childR = newNode;
		else parent.childL = newNode;
		newNode.parent = parent;
		
		size++;
		
		// AUFSICKERN
		newNode.aufsickern();
	}
	
	public T pop() {
		T elem = peek();
		
		size--;
		if (isEmpty()) {clear(); return elem;}
		
		
		Node lastNode = getNodeAtHeap(size);
		
		lastNode.exchangeWith(head);
		
		if (lastNode.parent != null) {
			if (lastNode.parent.childL==lastNode) lastNode.parent.childL = null;
			else lastNode.parent.childR = null;
		}
		
		// TODO VERSICKERN
		head.absickern();
		
		return elem;
	}
	public T peek() {
		if (isEmpty()) throw new IllegalStateException();
		
		return head.value;
	}
	
	
	
	public void clear() {
		this.head = null;
		this.size = 0;
	}
	
	public boolean isEmpty() {return size==0;}
	public int size() {return size;}
	private int getDepth(int s) {return (int)Math.ceil(Math.log(s)/Math.log(2));}
	
	private Node getNodeAtHeap(int pos) {
		
		Node n = head;
		
		for (int i=getDepth(pos+2)-2; i >= 0; i--) {
			if ((int)((pos+1)%Math.pow(2,i+1)) / (int)Math.pow(2,i) == 1) n = n.childR;
			else n = n.childL;
		}
		
		return n;
	}
	
	@Override
	public String toString() {
		if (isEmpty()) return "[Empty priority queue]";
		
		String ret = "Heap -> [";
		for (int i=0; i<size(); i++) {
			ret += getNodeAtHeap(i).value + ", ";
		}
		ret = ret.substring(0, ret.length()-2) + "]";
		return ret;
	}
	
	class Node {
		private T value;
		private Node parent;
		private Node childL;
		private Node childR;
		
		public Node(T value) {
			this.value = value;
		}
		
		private void aufsickern() {
			
			if (this.parent!=null && this.parent.value.compareTo(this.value) > 0) {
				exchangeWith(this.parent);
				this.parent.aufsickern();
			}
		}
		
		private void absickern() {
			Node maxChild = null;
			if (this.childL != null && this.childR != null) {
				maxChild = childL.value.compareTo(childR.value) < 0 ? childL : childR;
			}
			else if (this.childL != null) {
				maxChild = childL;
			}
			else if (this.childR != null) {
				maxChild = childR;
			}
			else return;
			
			
			if (maxChild != null && maxChild.value.compareTo(value) < 0) {
				exchangeWith(maxChild);
				maxChild.absickern();
			}
		}
		
		private void exchangeWith(Node other) {
			T tempVal = this.value;
			
			this.value = other.value;
			other.value = tempVal;
		}
		
		
		@Override
		public String toString() {
			return "Node: " + value + " l: " + (childL==null ? "null" : childL.value) + " r: " + (childR==null ? "null" : childR.value);
		}
	}
}
