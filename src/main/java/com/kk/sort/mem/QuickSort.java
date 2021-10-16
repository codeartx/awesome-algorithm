package com.kk.sort.mem;

/**
 * 快速排序
 */
public class QuickSort extends SortSupport {
    // 快速排序
    public static void quickSort(int[] array, int start, int end) {
        if (start >= end) {
            return;
        }

        int basePosition = partition(array, start, end);

        //递归处理左端
        quickSort(array, start, basePosition - 1);
        // 递归处理右端
        quickSort(array, basePosition + 1, end);
    }

    /**
     * 大小分区，基数归位
     * 将小值移至左端小值区、大值移至右侧大值区，最终将基数归位
     */
    public static int partition(int[] array, int start, int end) {
        int base = array[start];

        while (start < end) {
            // 右侧向左（<---）找到较小值
            while (start < end && array[end] >= base)
                end--;

            // 将找到的较小值填入左端坑中
            array[start] = array[end];

            // 左侧向右(-->)寻找较大值
            while (start < end && array[start] <= base)
                start++;

            // 较大值填入右端坑中
            array[end] = array[start];
        }

        array[end] = base;

        return start;
    }
}
