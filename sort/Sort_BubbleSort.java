package sort;
import java.util.Arrays;

public class Sort_BubbleSort {

	public static void main(String[] args) {

		int[] arr = new int[] {5,2,1,6,2,7,2,4};
		sort(arr);
		System.out.println(Arrays.toString(arr));

	}
	
	public static void sort(int[] arr) {
		
		for (int i=0; i<arr.length-1; i++) {
//			System.out.println(Arrays.toString(arr));
			for (int j=0; j<arr.length-i-1; j++) {
				if (arr[j] > arr[j+1]) {
					int temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
				}
			}
			
		}
		
	}

}
