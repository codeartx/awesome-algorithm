package com.kk.sort.mem;

public class MergeSort extends SortSupport {

    public static void sort(int[] arr) {
        //在排序前，先建好一个长度等于原数组长度的临时数组，
        //避免递归中频繁开辟空间
        int[] temp = new int[arr.length];

        margeSort(arr, 0, arr.length - 1, temp);
    }

    public static void margeSort(int[] arr, int left, int right, int[] temp) {
        if (left >= right) { // 递分终止条件，左右边界重合
            return;
        }

        int mid = (left + right) / 2;

        // 左边归并排序，使得左子序列有序
        margeSort(arr, left, mid, temp);
        // 右边归并排序，使得右子序列有序
        margeSort(arr, mid + 1, right, temp);

        // 递归返回后、将两个有序子数组（tmp数组的2个有序区域[left,mid],[mid+1,right]）合并操作
        merge(arr, left, mid, right, temp);
    }

    /**
     * 合并左右2个有序的子序列
     *
     * <p>
     * i->
     * left   mid mid+1  right
     * j->
     */
    public static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left; //左序列指针
        int j = mid + 1; //右序列指针
        int t = 0; //临时数组指针

        while (i <= mid && j <= right) { // 遍历左、右2个有组子序列，找到最值的一侧不断右移，直到该层终点
            if (arr[i]>arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }

        while (i <= mid) { // 将左边剩余元素填充进temp中
            temp[t++] = arr[i++];
        }

        while (j <= right) {//将右序列剩余元素填充进temp中
            temp[t++] = arr[j++];
        }

        t = 0;
        // 将合并后结果copy到原数组中
        while (left <= right) {
            arr[left++] = temp[t++];
        }
    }
}
