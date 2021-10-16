package com.kk.sort.mem;

public class InsertSort extends SortSupport {
    public static void insertSort(int[] arr) {
        int len = arr.length;

        for (int i = 1; i < len; i++) {// 外层循环控制有序边界，[0,i-1]有序
            for (int j = i; j >= 1; j--) { // 内循环向前逐渐比较、交换
                if (arr[j] > arr[j - 1]) { // 违反规则、往前交换
                    swap(arr, j, j - 1);
                    continue;
                }

                // 交换插入到正确的位置，结束本次循环
                break;
            }
        }
    }
}
