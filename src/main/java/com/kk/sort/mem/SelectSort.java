package com.kk.sort.mem;

/**
 * 选择（极值）排序
 */
public class SelectSort extends SortSupport {
    public static void selectSort(int[] arr) {
        int len = arr.length;

        for (int i = 0; i < len; i++) { // 外循环控制排序边界
            int minValId=i;

            for (int j = i+1; j < len; j++) { // 内循环在未排序范围内向右选择最小值
                if (arr[minValId]>arr[j]){
                    minValId=j;
                }
            }

            // 将选择出来的最小值交换到以排序边界i
            swap(arr,i,minValId);
        }
    }
}
