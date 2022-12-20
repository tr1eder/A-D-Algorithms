package sort;
import java.util.Arrays;

public class Sort_MergeSort {
	private static int[] toSort;

	public static void main(String[] args) {
		
		toSort = new int[] {10,80,7,2,4,70,85,8,3,1,4};
		
		sort(toSort);
		System.out.println(Arrays.toString(toSort));

	}
	
	
	public static void sort(int[] arr) {
		toSort = arr;
		
		MergeSort(0,toSort.length-1);
		
	}
	
	private static void MergeSort(int left, int right) {
		
		if (left < right) {
			int middle = (left+right)/2;
			MergeSort(left,middle);
			MergeSort(middle+1,right);
			
			Merge(left,middle,right);
		}
	}
	
	private static void Merge(int left, int middle, int right) {
		int point1 = left;
		int point2 = middle+1;
		int[] B = new int[right-left+1];
		
		// choose from left and right
		int i = 0;
		while (point1<=middle && point2<=right) {
			if (toSort[point1] < toSort[point2]) {
				B[i++] = toSort[point1++];
			}
			else {
				B[i++] = toSort[point2++];
			}
		}
		// add the rest
		while (point1<=middle) {
			B[i++] = toSort[point1++];
		}
		while (point2<=right) {
			B[i++] = toSort[point2++];
		}
		
		// copy back to toSort
		i=0;
		for (int k=left; k<=right; k++) {
			toSort[k] = B[i++];
		}
	}

}
