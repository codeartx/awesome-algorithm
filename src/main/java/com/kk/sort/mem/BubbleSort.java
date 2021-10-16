package com.kk.sort.mem;

/**
 * 冒泡排序
 */
public class BubbleSort extends SortSupport {
    public static void bubbleSort(int[] arr) {
        int len = arr.length;

        for (int i = 0; i < len; i++) { // 外层控制最右侧边界，每次-`
            for (int j = 0; j < len - i - 1; j++) { // 内层通过比较、交换，将最值移至上述边界处
                // 比较响铃的2个值，大的移至右侧
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }
}
