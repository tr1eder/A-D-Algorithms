package data_structure;

public class Queue<T> {
	
	private Node topElem;
	private Node endElem;
	private int size;

	public static void main(String[] args) {

		Queue<Integer> queue = new Queue<>();
		
		queue.push(13);
		queue.push(17);
		
		System.out.println(queue);
		System.out.println(queue.peek());
		System.out.println(queue.peek());
		System.out.println(queue.pop());
		System.out.println(queue.pop());
		System.out.println(queue.size());
		System.out.println(queue.isEmpty());
		
		queue.push(1);
		queue.push(3);
		queue.push(null);
		System.out.println(queue);
		System.out.println(queue.pop());
		queue.clear();
		System.out.println(queue);

	}
	
	public Queue() {
		clear();
	}
	
	public void push(T elem) {
		Node node = new Node(elem);
		
		// add to last Elem
		if (isEmpty()) {
			topElem = endElem = node;
		}
		else {
			endElem.next = node;
			endElem = endElem.next;
		}
		size++;
	}
	
	public T pop() {
		T elem = peek();
		
		topElem = topElem.next;
		size--;
		
		if (isEmpty()) clear();
		
		return elem;
	}
	
	public T peek() {
		if (isEmpty()) throw new IllegalStateException();
		
		return topElem.value;
	}
	
	public void clear() {
		topElem = null;
		endElem = null;
		size = 0;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		if (isEmpty()) return "[Empty queue]";
		
		String ret = "Top -> [";
		Node n = topElem;
		while (n.next != null) {
			ret += n.value + ", ";
			n = n.next;
		}
		ret += n.value + "]";
		
		return ret;
	}
	
	class Node {
		private T value;
		private Node next;
		
		public Node(T value) {
			this.value = value;
		}
	}
}
