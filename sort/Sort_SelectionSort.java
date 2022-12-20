package sort;
import java.util.Arrays;

public class Sort_SelectionSort {

	public static void main(String[] args) {

		int[] arr = new int[] {5,2,1,6,2,7,2,4};
		sort(arr);
		System.out.println(Arrays.toString(arr));

	}
	
	public static void sort(int[] arr) {
		
		for (int i=0; i<arr.length-1; i++) {
//			System.out.println(Arrays.toString(arr));
			int minVal = arr[i];
			int minPos = i;
			for (int j=i+1; j<arr.length; j++) {
				if (arr[j] < minVal) {
					minVal = arr[j];
					minPos = j;
				}
			}
			
			arr[minPos] = arr[i];
			arr[i] = minVal;
		}
	}
}
