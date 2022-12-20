package sort;
import java.util.Arrays;

public class Sort_QuickSort {
	private static int[] toSort;

	public static void main(String[] args) {

		int[] arr = new int[] {1,5,3,2,7,23,14,7,4,23576,37,2,34,25,43,23,753,14,66};
		
		sort(arr);
		
		System.out.println(Arrays.toString(arr));
		

	}
	
	public static void sort(int[] arr) {
		toSort = arr;
		QuickSort(0,toSort.length-1);
	}
	
	private static void QuickSort(int left, int right) {
		if (left < right) {
//			System.out.println(Arrays.toString(toSort));
			int myMiddle = Aufteilen(left,right);
			
			QuickSort(left, myMiddle-1);
			QuickSort(myMiddle+1,right);
			
		}
	}
	
	private static int Aufteilen(int left, int right) {
		int pivot = right; // could be randomized
		
		int pivotVal = toSort[pivot];
		
		int pointer1 = left;
		int pointer2 = right-1;
		
		while (pointer1<pointer2) {
			while (toSort[pointer1] < pivotVal) pointer1++;
			while (pointer2>=left && toSort[pointer2] >= pivotVal) pointer2--;
			
			if (pointer1<pointer2) {
				int temp = toSort[pointer1];
				toSort[pointer1] = toSort[pointer2];
				toSort[pointer2] = temp;
			}
		}
		
		toSort[pivot] = toSort[pointer1];
		toSort[pointer1] = pivotVal;
		
		
		
		return pointer1;
	}
	
//	private static int AufteilenRandom(int left, int right) {
//		int pivot = 
//	}

}
