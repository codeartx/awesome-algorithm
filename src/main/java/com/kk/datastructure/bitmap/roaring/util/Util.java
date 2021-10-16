package com.kk.datastructure.bitmap.roaring.util;

public class Util {
    /**
     在[begin，end）范围内的数组中查找值k。 如果找到该值，则返回其索引。
     如果不是，则返回-（i + 1），其中i是要插入值的索引。 假定该数组包含排序的值，其中短裤被解释为无符号整数。
     *
     * @param begin first index (inclusive)
     * @param end last index (exclusive)
     * @param k value we search for
     * @return count
     */
    public static int binarySearch(char[] arr, int begin, int end,
                                   char k) {
        if ((end > 0) && ((arr[end - 1]) < (int) (k))) {
            return -end - 1;
        }

        int low = begin;
        int high = end - 1;

        while (low <= high) {
            int middleIndex = (low + high) >>> 1;
            int middleValue = (arr[middleIndex]);

            if (middleValue < (int) (k)) {
                low = middleIndex + 1;
            } else if (middleValue > (int) (k)) {
                high = middleIndex - 1;
            } else {
                return middleIndex;
            }
        }

        // 最后未搜到的话，返回后继位置
        return -(low + 1);
    }

    public static char highBits(int x) {
        return (char) (x >>> 16);
    }

    public static char lowBits(int x) {
        return (char) x;
    }
}
