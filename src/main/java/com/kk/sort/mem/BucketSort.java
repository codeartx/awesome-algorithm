package com.kk.sort.mem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序
 * 分组入桶、桶内排序、按序回收
 */
public class BucketSort extends SortSupport {
    /**
     * 本例按照[min,mid],(mid,max]分2组
     */
    public static void bucketSort(int[] arr) {
        int min = min(arr);
        int max = max(arr);
        int mid = min + max / 2;

        Object[] buckets = new Object[2];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<Integer>();
        }

        // 入桶
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];

            if (num <= mid) { // [min,mid] 0号组
                ((List<Integer>) buckets[0]).add(num);
            } else {  // (mid,max] 1号组
                ((List<Integer>) buckets[1]).add(num);
            }
        }

        // 每组排序
        for (int i = 0; i < buckets.length; i++) {
            // 内部适用归并或快速排序
            Collections.sort((List<Integer>) buckets[i]);
        }

        // 按序回收所有桶
        int arrCursor = 0;

        for (int i = 0; i < buckets.length; i++) {
            List<Integer> sortedNums = (List<Integer>) buckets[i];

            for (Integer num : sortedNums) {
                arr[arrCursor] = num;
                arrCursor++;
            }
        }
    }
}
