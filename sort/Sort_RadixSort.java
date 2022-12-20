package sort;
import java.util.*;

public class Sort_RadixSort {
	
	private static List<Integer> toSort = new ArrayList<Integer>();
	private static int[] bitSort;

	public static void main(String[] args) {
		
		Integer[] arr = new Integer[] {1,34,6,14,4,236,23,12,56,4,56,2,43};
		int[] arr2 = new int[] {1,34,6,14,4,236,23,12,56,4,56,2,43};
		
		sort(arr);
		
		sort(arr2);
		
		System.out.println(Arrays.toString(arr));
		System.out.println(Arrays.toString(arr2));

	}
	
	/** Only positive Integers are allowed */
	public static void sort(Integer[] arr) {
		toSort.addAll(Arrays.asList(arr));
		RadixSortLST();
		
		
		for (int i=0; i<arr.length; i++) {
			arr[i] = toSort.get(i);
		}
	}
	
	/** Only positive ints are allowed */
	public static void sort(int[] arr) {
		bitSort = arr;
		RadixSortMSDInPlace();
		
	}
	
	private static void RadixSortLST() {
		int largestElem = -1;
		for (int i : toSort) if (i>largestElem) largestElem=i;
		int len = (""+largestElem).length();
		
		Bucket[] buckets = new Bucket[10];
		for (int i=0; i<10; i++) {
			buckets[i] = new Bucket();
		}
		
		for (int i=0; i<len; i++) {
			
			// fill buckets
			int faktor = (int)Math.pow(10,i);
			for (int el : toSort) {
				buckets[(el/faktor)%10].add(el);
			}
			
			toSort.clear();
			
			// write into toSort & empty buckets
			for (Bucket b : buckets) {
				
				toSort.addAll(b);
				b.clear();
			}
		}
	}
	
	private static void RadixSortMSDInPlace() {
		int largestElem = -1;
		for (int i : bitSort) if (i>largestElem) largestElem=i;
		int len = (int) (Math.log(largestElem)/Math.log(2));
		
		
		MSDRek(0, bitSort.length-1, len);
	}
	
	private static void MSDRek(int start, int end, int sortingBit) {
		if (end-start < 1 || sortingBit<0) return;
		int pointer1 = start;
		int pointer2 = end;
		
		while (pointer1<pointer2) {
			while (pointer1<=end && ((bitSort[pointer1]>>sortingBit)&1) == 0) pointer1++;
			while (pointer2>=start && ((bitSort[pointer2]>>sortingBit)&1) == 1) pointer2--;
			
			// change the positions
			if (pointer1<pointer2) {
				int temp = bitSort[pointer1];
				bitSort[pointer1] = bitSort[pointer2];
				bitSort[pointer2] = temp;
			}
		}
		
		
		MSDRek(start, pointer1-1, sortingBit-1);
		MSDRek(pointer1, end, sortingBit-1);
	}
	
	
	
}

class Bucket extends ArrayList<Integer> {
	private static final long serialVersionUID = 1L;
}
