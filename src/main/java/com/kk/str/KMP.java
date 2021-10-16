package com.kk.str;

/**
 * KMP字符串匹配算法
 */
public class KMP {
    public static int find(String str, String temp) {
        return find(str.toCharArray(), temp.toCharArray());
    }

    public static int find(char[] str, char[] temp) {
        int n = str.length;

        int tempLen = temp.length;

        int[] jumpTable = calJumpTable(temp);

        int j = 0; //j表示当前模版串的待匹配位置

        for (int i = 0; i < n; ++i) {
            while (str[i] != temp[j]) { // 失配点
                j = jumpTable[j]; //不停的转移，直到可以匹配或者走到0

                if (j == 0) break;
            }

            // 调整失配点之后，向后移位逐个比较
            if (str[i] == temp[j]) j++;

            if (j == tempLen) return i - tempLen + 1;
        }

        // 未找到
        return -1;
    }

    static int[] calJumpTable(String temp) {
        return calJumpTable(temp.toCharArray());
    }

    /**
     * 计算失配-跳转表
     */
    static int[] calJumpTable(char[] temp) {
        int len = temp.length;
        int[] table = new int[len + 1];

        table[0] = table[1] = 0; // 边界

        for (int n = 1; n < len; ++n) {
            int jump = table[n];

            while (temp[n] != temp[jump]) {
                // jump 向前追溯
                jump = table[jump];

                if (jump == 0) { // 为0，表示已回退到指定点
                    break;
                }
            }

            // 递推
            table[n + 1] = temp[n] == temp[jump] ? jump + 1 : 0;
        }

        return table;
    }
}
