package com.kk.sort.mem;

/**
 * 希尔排序
 * 4层循环
 * gap     控制步长
 * i       控制子序列左边界
 * j       插入排序外循环控制排序边界
 * k       插入排序内循环向前比较、交换
 */
public class ShellSort extends SortSupport {
    public static void shellSort(int[] arr) {
        int len = arr.length;

        for (int gap = len / 2; gap > 0; gap /= 2) { // 控制递减间隙
            for (int i = 0; i < gap; i++) { // 控制每个子序列的起始位置

                /**
                 * 对子序列进行普通的插入排序
                 */
                for (int j = i; j < len; j += gap) { // 控制为排序的范围
                    for (int k = j; k >= gap; k -= gap) { // 向前比较、交换
                        if (arr[k] > arr[k - gap]) {  // 违反规则、需要交换
                            swap(arr, k, k - gap);

                            continue; // 继续交换
                        }
                        break;
                    }
                }
            }
        }
    }
}
