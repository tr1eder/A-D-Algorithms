package search;

public class Search_BinarySearch {
	private static int[] searchArray;

	public static void main(String[] args) {
		
		int[] arr = new int[] {1,3,5,8,9,12,14};
		
		int pos = bin_searchInsertionPoint(arr, 1);
		int pos2 = bin_search(arr, 9);
		
		System.out.println(pos + " " + pos2);

	}
	
	/** Takes a sorted Integer Array and a Search Key b 
	 * Returns the position of b in the Array, -1 if b not in the Array */
	public static int bin_search(int[] arr, int b) {
		searchArray = arr;
		int pos = search(b, 0, searchArray.length-1);
		
		if (0<=pos && pos<searchArray.length && searchArray[pos] == b) return pos;
		else return -1;
		
	}
	
	/** Takes a sorted Array and Key b, returns the position where b has to be inserted */
	public static int bin_searchInsertionPoint(int[] arr, int b) {
		searchArray = arr;
		return search(b,0,searchArray.length-1);
	}
	
	/** As bin_searchInsertionPoint, but searches in a specified part of the Array */
	public static int bin_searchInsertionInRange(int[] arr, int b, int start, int end) {
		searchArray = arr;
		return search(b,start, end);
	}
	
	private static int search(int key, int start, int end) {
		if (end-start < 1) {
			if (key<=searchArray[start]) return start;
			else return end+1;
		}
		
		int middle = (start+end) / 2;
		
		if (key > searchArray[middle]) {
			return search(key, middle+1, end);
		}
		else if (key == searchArray[middle]) {
			return middle;
		}
		else {
			return search(key, start, middle-1);
		}
	}
}
