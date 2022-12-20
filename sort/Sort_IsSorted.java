package sort;

public class Sort_IsSorted {

	public static void main(String[] args) {


		int[] arr = new int[] {1,3,5,9,9,12,14};
		
		System.out.println(isSortedAsc(arr));
		

	}
	
	public static boolean isSortedAsc(int[] arr) {
		for (int i=0;i<arr.length-1;i++) {
			if (arr[i]>arr[i+1]) return false;
		}
		
		return true;
	}
	
	public static boolean isSortedAscStrict(int[] arr) {
		for (int i=0;i<arr.length-1;i++) {
			if (arr[i]>=arr[i+1]) return false;
		}
		
		return true;
	}
	
	public static boolean isSortedDsc(int[] arr) {
		for (int i=0;i<arr.length-1;i++) {
			if (arr[i]<arr[i+1]) return false;
		}
		
		return true;
	}
	
	public static boolean isSortedDscStrict(int[] arr) {
		for (int i=0;i<arr.length-1;i++) {
			if (arr[i]<=arr[i+1]) return false;
		}
		
		return true;
	}
	

}
