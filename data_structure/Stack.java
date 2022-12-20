package data_structure;

public class Stack<T> {
	
	private Node<T> topElem;
	private int size;

	public static void main(String[] args) {

		Stack<Integer> stack = new Stack<>();
		
		stack.push(13);
		stack.push(17);
		
		System.out.println(stack);
		System.out.println(stack.peek());
		System.out.println(stack.peek());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.size());
		System.out.println(stack.isEmpty());
		
		stack.push(1);
		stack.push(3);
		stack.push(null);
		System.out.println(stack);
		System.out.println(stack.pop());
		stack.clear();
		System.out.println(stack);

	}
	
	public Stack() {
		clear();		
	}
	
	public void push(T elem) {
		Node<T> node = new Node<>(elem);
		
		node.next = topElem;
		size++;
		topElem = node;
	}
	
	public T pop() {
		T elem = peek();
		
		topElem = topElem.next;
		size--;
		
		return elem;
	}
	
	public T peek() {
		if (isEmpty()) throw new IllegalStateException();
		
		return topElem.value;
	}
	
	public void clear() {
		topElem = null;
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
		if (isEmpty()) return "[Empty stack]";
		
		String ret = "Top -> [";
		Node<T> n = topElem;
		while (n.next != null) {
			ret += n.value + ", ";
			n = n.next;
		}
		ret += n.value + "]";
		
		return ret;
	}

	class Node<T1> {
		private T1 value;
		private Node<T1> next;
		
		public Node(T1 value) {
			this.value = value;
		}
	}
}

