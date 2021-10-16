package com.kk.util;

public class PrintHelper {

    public static void printArr(int[] arr, int start, int end) {
        for (int i = start; i < end + 1; i++) {
            System.out.print(arr[i]);
            System.out.print("\t");
        }
    }

    public static void printArr(int[] arr) {
        for (int num : arr) {
            System.out.print(num);
            System.out.print("\t");
        }

        System.out.println();
    }
}
