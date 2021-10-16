package com.kk.sort.mem;

import java.util.ArrayList;
import java.util.List;

/**
 * 基数排序
 */
public class RadixSort extends SortSupport {
    public static void radixSort(int[] arr) {
        // 找到最大值
        int max = max(arr);

        // max决定了最高基数（循环次数），从低基数到高基数排序，1,10,100...
        for (int base = 1; max / base > 0; base = base * 10) {
            // 构建基数桶
            Object[] buckets = createBuckets();

            // 分配：遍历原数组每个元素按pos位入桶
            for (int i = 0; i < arr.length; i++) {
                // 获取该位数字
                int posNum = (arr[i] / base) % 10;
                // 放入对应的桶中
                ((List<Integer>) buckets[posNum]).add(arr[i]);
            }

            // 回收：遍历桶，将每个桶内的元素按序放回arr
            int arrCursor = 0;

            for (int i = 0; i < buckets.length; i++) {
                List<Integer> nodesList = (List<Integer>) buckets[i];

                for (Integer num : nodesList) {
                    arr[arrCursor] = num;
                    arrCursor++;
                }
            }
        }
    }

    /**
     * 创建基数桶
     */
    private static Object[] createBuckets() {
        Object[] buckets = new Object[10];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<Integer>();
        }
        return buckets;
    }
}
