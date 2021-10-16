package com.kk.sort;

import com.kk.sort.mem.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SortTest {
    int[] nums = {8, 3, 6, 7, 1, 9};

    @Before
    public void printBeforeSort() {
        System.out.println(" nums to sort-->" + arrJoin(nums));
    }

    @After
    public void printAfterSort() {
        System.out.println("sorted-->" + arrJoin(nums));
    }

    private String arrJoin(int[] arr) {
        StringBuffer builder = new StringBuffer();
        for (Object s : arr) {
            builder.append(s + ",");
        }

        return builder.toString();
    }

    @Test
    public void bubbleSortTest() {
        BubbleSort.bubbleSort(nums);
    }

    @Test
    public void selectSortTest() {
        SelectSort.selectSort(nums);
    }

    @Test
    public void insertSortTest() {
        InsertSort.insertSort(nums);
    }

    @Test
    public void shellSortTest() {
        ShellSort.shellSort(nums);
    }

    @Test
    public void mergeSortTest() {
        MergeSort.sort(nums);
    }

    @Test
    public void quickSortTest() {
        QuickSort.quickSort(nums, 0, nums.length - 1);
    }

    @Test
    public void countSortTest() {
        CountSort.countSort(nums);
    }

    @Test
    public void radixSortTest() {
        RadixSort.radixSort(nums);
    }

    @Test
    public void bucketSortTest() {
        BucketSort.bucketSort(nums);
    }
}
