package com.kk.sort.mem;

/**
 * 计数排序
 */
public class CountSort {
    public static void countSort(int[] arr) {
        // 找到极值
        int min = arr[0];
        int max = arr[0];

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }

            if (arr[i] < min) {
                min = arr[i];
            }
        }

        // 创建计数数组
        int[] countArr = new int[max - min + 1];

        // 遍历原数组计数
        for (int i = 0; i < arr.length; i++) {
            // 每出现一次+1
            countArr[arr[i] - min] = countArr[arr[i] - min] + 1;
        }

        // 遍历计数数组，按计数和偏移写回原数组
        int arrCoursour = 0;

        for (int i = 0; i < countArr.length; i++) {
            int count = countArr[i];

            if (count == 0) continue;

            int num = i + min;

            for (int j = 0; j < count; j++) {  // 计数几次、写回几次
                arr[arrCoursour] = num;
                arrCoursour++;
            }
        }
    }
}
