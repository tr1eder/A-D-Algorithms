package sort;

// import static org.junit.Assert.*;
import java.io.IOException;
// import org.junit.jupiter.api.Test;

import java.util.*;

public class Sort_TEST {
	private Random rand = new Random();

	public static void main(String[] args) throws IOException {
		Sort_TEST s = new Sort_TEST();

		s.test_IsSorted();
		s.test_BubbleSort();
		s.test_SelectionSort();
		s.test_InsertionSort();
		s.test_HeapSort();
		s.test_MergeSort();
		s.test_QuickSort();
		s.test_JavaSort();
		s.test_RadixSort();
		s.test_RadixSortMSDInPlace();
		
	}
	
	// @Test
	public void test_IsSorted() throws IOException {
		int[][] myList = getLst(1000000);
		long start = System.nanoTime();
		assertTrue(Sort_IsSorted.isSortedAsc(myList[1]));
		assertFalse(Sort_IsSorted.isSortedDsc(myList[1]));
		long end = System.nanoTime();
		print(String.format("%-23s", "IsSorted:      (10^6)") + String.format("%5d", (end-start)/1000000) + " millis");
	}
	
	// @Test
	public void test_BubbleSort() throws IOException {
		int[][] myList = getLst(10000);
		long start = System.nanoTime();
		Sort_BubbleSort.sort(myList[0]);
		long end = System.nanoTime();
		assertArrayEquals(myList[1], myList[0]);
		print(String.format("%-23s", "BubbleSort:    (10^4)") + String.format("%5d", (end-start)/1000000) + " millis");
	}
	
	// @Test
	public void test_SelectionSort() throws IOException {
		int[][] myList = getLst(10000);
		long start = System.nanoTime();
		Sort_SelectionSort.sort(myList[0]);
		long end = System.nanoTime();
		assertArrayEquals(myList[1], myList[0]);
		print(String.format("%-23s", "SelectionSort: (10^4)") + String.format("%5d", (end-start)/1000000) + " millis");
	}
	
	// @Test
	public void test_InsertionSort() throws IOException {
		int[][] myList = getLst(10000);
		long start = System.nanoTime();
		Sort_InsertionSort.sort(myList[0]);
		long end = System.nanoTime();
		assertArrayEquals(myList[1], myList[0]);
		print(String.format("%-23s", "InsertionSort: (10^4)") + String.format("%5d", (end-start)/1000000) + " millis");
	}
	
	// @Test
	public void test_HeapSort() throws IOException {
		int[][] myList = getLst(1000000);
		long start = System.nanoTime();
		Sort_HeapSort.sort(myList[0]);
		long end = System.nanoTime();
		assertArrayEquals(myList[1], myList[0]);
		print(String.format("%-23s", "HeapSort:      (10^6)") + String.format("%5d", (end-start)/1000000) + " millis");
	}
	
	// @Test
	public void test_MergeSort() throws IOException {
		int[][] myList = getLst(1000000);
		long start = System.nanoTime();
		Sort_MergeSort.sort(myList[0]);
		long end = System.nanoTime();
		assertArrayEquals(myList[1], myList[0]);
		print(String.format("%-23s", "MergeSort:     (10^6)") + String.format("%5d", (end-start)/1000000) + " millis");
	}
	
	// @Test
	public void test_QuickSort() throws IOException {
		int[][] myList = getLst(1000000);
		long start = System.nanoTime();
		Sort_QuickSort.sort(myList[0]);
		long end = System.nanoTime();
		assertArrayEquals(myList[1], myList[0]);
		print(String.format("%-23s", "QuickSort:     (10^6)") + String.format("%5d", (end-start)/1000000) + " millis");
	}
	
	// @Test
	public void test_JavaSort() throws IOException {
		int[][] myList = getLst(1000000);
		long start = System.nanoTime();
		Arrays.sort(myList[0]);
		long end = System.nanoTime();
		assertArrayEquals(myList[1], myList[0]);
		print(String.format("%-23s", "JavaSort:      (10^6)") + String.format("%5d", (end-start)/1000000) + " millis");
	}
	
	// @Test
	public void test_RadixSort() throws IOException {
		int[][] myList = getLst(1000000);
		Integer[] IntList = new Integer[myList[0].length];
		int i=0;
		for (int elem : myList[0]) IntList[i++] = elem;
		
		long start = System.nanoTime();
		Sort_RadixSort.sort(IntList);
		long end = System.nanoTime();
		
		i=0;
		for (int elem : IntList) myList[0][i++] = elem;
		
		assertArrayEquals(myList[1], myList[0]);
		print(String.format("%-23s", "RadixSortLSD:  (10^6)") + String.format("%5d", (end-start)/1000000) + " millis");
	}
	
	// @Test
	public void test_RadixSortMSDInPlace() throws IOException {
		int[][] myList = getLst(1000000);
		
		long start = System.nanoTime();
		Sort_RadixSort.sort(myList[0]);
		long end = System.nanoTime();
		
		assertArrayEquals(myList[1], myList[0]);
		print(String.format("%-23s", "RadixSortMSD:  (10^6)") + String.format("%5d", (end-start)/1000000) + " millis");
	}
	
	
	private void assertTrue(boolean t) throws IOException {
		if (!t) throw new IOException("Assertion failed: value is false, should be true");
	}
	private void assertFalse(boolean f) throws IOException {
		if (f) throw new IOException("Assertion failed: value is true, should be false");
	}

	private void assertArrayEquals(int[] arr1, int[] arr2) throws IOException {
		if (!Arrays.equals(arr1, arr2)) throw new IOException("Assertion failed: Arrays not equal");
	}
	
	
	private int[][] getLst(int elems) {
		
		int[] lst = new int[elems];
		for (int i=0; i<elems; i++) {
//			lst[i] = rand.nextInt();
			lst[i] = rand.nextInt(elems/100);
		}
		
		int[] sorted = Arrays.copyOf(lst, elems);
		Arrays.sort(sorted);
		
		return new int[][] {lst, sorted};
	}
	
	private void print(String val) {System.out.println(val);}
}
