package sort;
import java.util.Arrays;

public class Sort_HeapSort {
	private static int[] toSort;

	public static void main(String[] args) {

		toSort = new int[] {10,80,7,2,4,70,85,8,3,1,4};
		// heap would be: 85,10,8,80,3,7,1,2,4,70
		sort(toSort);
		
		System.out.println(Arrays.toString(toSort));

	}
	
	private static void restoreMaxHeap(int pos) {
		restoreMaxHeap(pos,0,toSort.length-1);
	}
	
	private static void restoreMaxHeap(int pos, int from, int to) {
		// child-nodes at 2pos+1, 2pos+2
		
		int otherPos = 2*pos+1;
		if (otherPos > to) return; // out of range
		
		if (2*pos+2 <= to && toSort[2*pos+2] > toSort[otherPos]) otherPos = 2*pos+2;
		
		if (toSort[otherPos] <= toSort[pos]) return; // no change needed
		
		
		// change
		int temp = toSort[pos];
		toSort[pos] = toSort[otherPos];
		toSort[otherPos] = temp;
		
		restoreMaxHeap(otherPos, from, to);
	}
	
	public static void sort(int[] arr) {
		toSort = arr;
		
		for (int i=toSort.length/2; i>=0; i--) {
			restoreMaxHeap(i);
		}
		
		for (int i=toSort.length-1; i>=0; i--) {
			
//			System.out.println(Arrays.toString(toSort));
			
			int tempMax = toSort[0];
			toSort[0] = toSort[i];
			toSort[i] = tempMax;
			
			restoreMaxHeap(0, 0, i-1);
		}
		
		
	}

}
