package sort;
import java.util.Arrays;

import search.Search_BinarySearch;

public class Sort_InsertionSort {

	public static void main(String[] args) {
		int[] arr = new int[] {5,2,1,6,2,7,2,4};
		sort(arr);
		System.out.println(Arrays.toString(arr));

	}
	
	public static void sort(int[] arr) {
	
		for (int i=1; i<arr.length; i++) {
//			System.out.println(Arrays.toString(arr));
			int elem = arr[i];
			int posToPut = Search_BinarySearch.bin_searchInsertionInRange(arr, elem, 0, i-1);
			
			for (int j=i-1; j>=posToPut; j--) {
				arr[j+1] = arr[j];
			}
			arr[posToPut] = elem;
			
		}
	}

}
