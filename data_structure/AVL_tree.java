package data_structure;

import java.util.*;
import java.util.Map.Entry;

import data_structure.AVLTree.Value;
import theGUI.Window;
// import gui.Window;

public class AVL_tree {

	public static void main(String[] args) {
		AVLTree avl = new AVLTree(50, true);
		DrawTree draw = new DrawTree(avl);
		draw.setAnimateTime(300);

		// int[] demonstrateSingleRot = new int[] {30, 20};
		// Insert fixInsert1 = new FixedInsert(demonstrateSingleRot);
		// draw.show(fixInsert1);

		// Demonstrate double rotation
		// draw.show(new FixedInsert(new int[] {42, 70, 60, 80, 66}));

		// int[] demonstrateDoubleRot = new int[] {60, 40, 55, 90, 39, 41, 54, 56, 89, 91, 58};
		// Insert fixInsert2 = new FixedInsert(demonstrateDoubleRot);
		// draw.show(fixInsert2);
		
		int[] demonstrateDoubleRot = new int[] {20, 60, 10, 30, 40, 35, 45, 80, 48, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		Insert fixInsert = new FixedInsert(demonstrateDoubleRot);
		draw.show(fixInsert);
		
		
		
		// Insert toInsert = new RandomInsert(1000);
		// draw.show(toInsert);
		
		
		
		
		
		// int[] testing = new int[] {10, 20};
		// new FixedInsert(testing);
	}

}

interface Insert {
	public Integer getNext();
}

class RandomInsert implements Insert {
	private final int max;
	private final Random rand;
	
	public RandomInsert(int max) {
		this.max = max;
		this.rand = new Random();
	}
	
	public Integer getNext() {
		return rand.nextInt(max);
	}
}

class FixedInsert implements Insert {
	private final int[] arr;
	private int i;
	
	public FixedInsert(int[] arr) {
		this.arr = arr;
		this.i = 0;
	}
	
	public Integer getNext() {
		if (i>=arr.length) return null;
		
		return arr[i++];
	}
}


class DrawTree {
	Window window;
	
	private DrawPointNode[][] nodes;
	
	private static final int width = 600;
	private static final int height = 450;
	private static final int size = height/8;
	private static final int maxDepth = 7;
	private int animateTime = 3000; // ms
	
	private AVLTree avl;
	
	private Map<Value, DrawPointNode> oldPoints = new HashMap<>();
	private Map<Value, DrawPointNode> newPoints = new HashMap<>();
	
	public DrawTree(AVLTree avl) {
		this.avl = avl;
		
//		window = new Window("AVL Tree", (int)(width*1.5), (int)(height*1.5));
		window = new Window("AVL Tree", width, height);
		
		createPointNodes();
	}
	
	public void setAnimateTime(int ms) {animateTime=ms;}
	
	private void createPointNodes() {
		nodes = new DrawPointNode[maxDepth][];
		for (int i=0; i<maxDepth; i++) {
			nodes[i] = new DrawPointNode[(int)Math.pow(2,i)];
		}
		DrawPointNode root = new DrawPointNode(0, 0);
		nodes[0][0] = root;
		createPointNodes(root, 1, 0);
	}
	
	private void createPointNodes(DrawPointNode parent, int depth, int pos) {
		if (depth >= maxDepth) return;
		DrawPointNode lChild = new DrawPointNode(depth, pos);
		DrawPointNode rChild = new DrawPointNode(depth, pos+1);
		nodes[depth][pos] = lChild;
		nodes[depth][pos+1] = rChild;
		
		lChild.parent = parent;
		rChild.parent = parent;
		
		createPointNodes(lChild, depth+1, pos*2);
		createPointNodes(rChild, depth+1, (pos+1)*2);
		
	}
	
	void show(Insert inserter) {
		window.open();
		
		long time = System.currentTimeMillis();
		
		try {
			fillPointMap(newPoints);
		} catch (AVLException e) {
			System.out.println(e.getMessage());
		}
		
		while(window.isOpen()) {
			
			try {
				if (System.currentTimeMillis()-time > animateTime) {
					time = System.currentTimeMillis();

					Integer nextValue = inserter.getNext();
					if (nextValue==null) {
						// break;
						throw new AVLException("End of input reached");
					}
					avl.insert(nextValue);
					
					oldPoints.clear();
					oldPoints.putAll(newPoints);
					
					
					newPoints.clear();
					fillPointMap(newPoints);
					
				}
				
				drawAVL((System.currentTimeMillis()-time) * 1.5 / animateTime);
				
				window.refreshAndClear(50);
			}
			catch (AVLException e) {
				System.out.println(e.getMessage());
				if (e.getMessage().equals("AVL Exception: End of input reached")) break;
			}
		}

		while (window.isOpen()) {
			// hold window open 
		}
	}
	
	private void fillPointMap(Map<Value, DrawPointNode> points) throws AVLException {
		Value root = avl.getRoot();
		fillPointMap(points, root, 0,0);
	}
	
	private void fillPointMap(Map<Value, DrawPointNode> points, Value v, int depth, int pos) throws AVLException {
		if (depth >= maxDepth) throw new AVLException("Tree capacity is full");
		points.put(v, nodes[depth][pos]);
		
		try {
			if (v.getLChild() != null) fillPointMap(points, v.getLChild(), depth+1, pos*2);
			if (v.getRChild() != null) fillPointMap(points, v.getRChild(), depth+1, pos*2+1);
		} catch (AVLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void drawAVL(double percentage) {
		
		for (Entry<Value, DrawPointNode> entry : newPoints.entrySet()) {
			Value value = entry.getKey();
			DrawPointNode to = entry.getValue();
			
			// Animation over or this is a new point
			if (percentage > 1 || !oldPoints.containsKey(value)) {
				to.drawNode(value, percentage);
			}
			
			// exists before and after the AVLTree operation
			else {
				
				DrawPointNode from = oldPoints.get(value);
				
				if (from == to) {
					from.drawNode(value);
				} else {
					from.drawNode(value, to, percentage);
				}
			}
		}
	}
	
	
	class DrawPointNode {
		
		private final int x;
		private final int y;
		private final int radius;
		
		private DrawPointNode parent;
		
		
		public DrawPointNode(int height, int pos) {
			int posY = -20;
			for (int i=0; i<=height; i++) posY += (int) (size*Math.pow(0.9,i)) + 20;
			y = posY;
			
			int anzOnHeight = (int)Math.pow(2,height);
			x = (int) ((pos+0.5) * width/anzOnHeight);
			
			radius = (int)(size*Math.pow(0.87,height*2)/1.3);
		}
		
		void drawNode(Value v, DrawPointNode toNode, double percent) {
			double myX = (1-percent)*x+percent*toNode.x;
			double myY = (1-percent)*y+percent*toNode.y;
			double myRadius = (1-percent)*radius+percent*toNode.radius;
			
			drawAVLNode(myX, myY, myRadius, v);
		}
		
		void drawNode(Value value, double percent) {
			if (percent > 1) {
				drawNode(value);
			}
			else if (percent > 0.25) {
				
				percent = (percent-0.25)/3*4;
				
				drawAVLNode(value,percent);
			}
		}
		
		void drawNode(Value value) {
			drawAVLLine();
			drawAVLNode(value);
		}
		
		
		
		
		private void drawAVLLine() {
			if (parent != null) {
				
				int distX = parent.x - this.x;
				int distY = parent.y - this.y;
				
				int dist = (int) Math.sqrt(distX*distX + distY*distY);
				
				int x1 = this.x + distX * this.radius / dist;
				int y1 = this.y + distY * this.radius / dist;
				
				int x2 = parent.x - distX * (parent.radius+1) / dist;
				int y2 = parent.y - distY * parent.radius / dist;
				
				window.setColor(0,0,0);
				window.drawLine(x1, y1, x2, y2);
			}
		}
		
		
		
		private void drawAVLNode(Value value) {
			drawAVLNode(x, y, radius, value);
		}
		private void drawAVLNode(Value value, double percent) {
			drawAVLNode(x,y,radius,value,percent);
		}
		private void drawAVLNode(double x, double y, double radius, Value value) {
			drawAVLNode(x,y,radius,value, 1);
		}
		private void drawAVLNode(double x, double y, double radius, Value value, double percent) {
			drawAVLNode(x,y,radius,""+value.getVal(),"b: "+value.getBal(), percent);
		}
		
		private void drawAVLNode(double x, double y, double radius, String text, String bal, double p) {
			window.setColor(calc(0,p),calc(0,p),calc(0,p));
			window.drawCircle(x, y, radius);
			
			window.setColor(calc(220,p), calc(255,p), calc(245,p));
			window.fillCircle(x, y, radius);
			
			window.setColor(calc(0,p),calc(0,p),calc(0,p));
			window.setBold(true);
			window.setFontSize((int)radius);
			window.drawStringCentered(text, x, y+radius/3);
			
			window.setBold(false);
			window.setFontSize((int) (radius/2.5));
			window.drawString(bal, x+radius*0.72, y-radius*0.72);
		}
		
		private int calc(int value, double percent) {
			return (int) (value+(255-value)*(1-percent));
		}
	}
}

class AVLTree {
	
	private Value root;
	private boolean withRebalancing;
	
	public AVLTree(int value) {
		this(value, true);
	}
	public AVLTree(int value, boolean rebalance) {
		root = new Value(value, null);
		withRebalancing = rebalance;
	}
	
	public void insert(int value) throws AVLException {
		root.insert(value);
	}
	
	public Value getRoot() {return root;}
	
	
	class Value {
		
		private Value lChild;
		private Value rChild;
		private Value parent;
		
		private int value;
		private int bal;
		
		public Value(int value, Value parent) {
			this.value = value;
			this.parent = parent;
		}
		
		public Value getLChild() {return lChild;}
		public Value getRChild() {return rChild;}
		public int getVal() {return value;}
		public int getBal() {return bal;}
		
		/** returns 1 iff this is a right child, else -1 */
		private int lrChild() {
			if (parent.lChild==this) return -1;
			else return 1;
		}
		
		private void upin(int adjust) {
			bal += adjust;
			
			/** Continue upin **/
			if (bal == 1 || bal == -1) {
				if (parent != null) parent.upin(lrChild());
				return;
			}
			
			/** Nothing **/
			if (bal == 0 || !withRebalancing) return;
			
			/** ROTATION **/
			
			// Case Right-Rotation
			if (bal == -2 && lChild.bal == -1) {
				Value v = this;
				Value u = lChild;
				
				v.bal = 0;
				u.bal = 0;
				
				if (parent != null) {
					if (lrChild()==-1) parent.lChild = u;
					else parent.rChild = u;
					u.parent = parent;
				}
				else {
					u.parent = null;
					root = u;
				}
				
				
				v.lChild = u.rChild;
				if (v.lChild != null) v.lChild.parent = v;
				
				u.rChild = v;
				v.parent = u;
				return;
			}
			
			// Case Left-Rotation
			if (bal == 2 && rChild.bal == 1) {
				Value v = this;
				Value u = rChild;
				
				v.bal = 0;
				u.bal = 0;
				
				u.parent = parent;
				if (parent != null) {
					if (lrChild()==-1) parent.lChild = u;
					else parent.rChild = u;
				}
				else {
					root = u;
				}
				
				v.rChild = u.lChild;
				if (v.rChild != null) v.rChild.parent = v;
				
				u.lChild = v;
				v.parent = u;
				
				return;
			}
			
			// Case Left-Right-Rotation
			if (bal == -2 && lChild.bal == 1) {
				Value v = this;
				Value u = lChild;
				Value w = lChild.rChild;
				
				v.bal = (w.bal == -1) ? 1 : 0;
				u.bal = (w.bal == 1) ? -1 : 0;
				w.bal = 0;
				
				u.rChild = w.lChild;
				if (w.lChild != null) w.lChild.parent = u;
				
				v.lChild = w.rChild;
				if (w.rChild != null) w.rChild.parent = v;
				
				w.parent = parent;
				if (parent != null) {
					if (lrChild()==-1) parent.lChild = w;
					else parent.rChild = w;
				}
				else {
					root = w;
				}
				
				u.parent = w;
				w.lChild = u;
				
				v.parent = w;
				w.rChild = v;
				
				return;
			}
			
			// Case Right-Left-Rotation
			if (bal == 2 && rChild.bal == -1) {
				Value v = this;
				Value u = rChild;
				Value w = rChild.lChild;
				
				v.bal = (w.bal == 1) ? -1 : 0;
				u.bal = (w.bal == -1) ? 1 : 0;
				w.bal = 0;
				
				u.lChild = w.rChild;
				if (w.rChild != null) w.rChild.parent = u;
				
				v.rChild = w.lChild;
				if (w.lChild != null) w.lChild.parent = v;
				
				w.parent = parent;
				if (parent != null) {
					if (lrChild()==-1) parent.lChild = w;
					else parent.rChild = w;
				}
				else {
					root = w;
				}
				
				u.parent = w;
				w.rChild = u;
				
				v.parent = w;
				w.lChild = v;
				
				return;
			}
			
			
			
			System.out.println("unknown rotation!");
		}
		
		private void insert(int val) throws AVLException {
			if (val == value) throw new AVLException("No equal values allowed");
			
			if (val < value) {
				if (lChild == null) {
					lChild = new Value(val, this);
					upin(lChild.lrChild());
				}
				else lChild.insert(val);
			}
			else {
				if (rChild == null) {
					rChild = new Value(val, this);
					upin(rChild.lrChild());
				}
				else rChild.insert(val);
			}
		}
	}
}




class AVLException extends Exception {
	private static final long serialVersionUID = 1003015093985231520L;

	public AVLException(String message) {
		super("AVL Exception: " + message);
	}
}


