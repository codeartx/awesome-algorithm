package com.kk.str;

public class BoyerMoore {
    static int find(String target, String temp) {
        char[] pat = temp.toCharArray();
        char[] str = target.toCharArray();
        int n = str.length;
        int m = pat.length;

        // 计算坏字符表和好后缀表
        int[] badChars = preBadCharsTable(temp);
        int[] goodSuffixes = preGoodSuffixes(temp, suffixes(temp));

        // 搜索
        int j, i = -1;
        while (i <= n - m) {
            // 从尾部逐个向前匹配，当对应位置相等的时候一直向前匹配
            for (j = m - 1; j >= 0 && pat[j] == str[j + i]; --j) ;

            // 出现失配点
            if (j < 0) {
                return i;
            } else { // 取较大的位移
                i += Math.max(badChars[j], goodSuffixes[j]);
            }
        }

        return i;
    }

    /**
     * 预生成坏字符串表
     */
    static int[] preBadCharsTable(String template) {
        int len = template.length();

        int[] badCharsTable = new int[len];
        badCharsTable[0] = 1;

        for (int i = 1; i < len; i++) {  // 遍历每一位
            char ch = template.charAt(i);

            int matchId = -1;

            // 向前遍历、比较
            for (int j = i - 1; j >= 0; j--) {
                if (ch == template.charAt(j)) {
                    matchId = j;
                    break;
                }
            }

            badCharsTable[i] = i - matchId;
        }

        return badCharsTable;
    }

    /**
     * 计算后缀
     * suffixes[i]的值为后缀串pat[0,i]与模式串pat的最长公共后缀长度
     */
    static int[] suffixes(String template) {
        char[] pat = template.toCharArray();

        int len = pat.length;

        int[] suffixes = new int[len];

        for (int i = len - 1; i >= 0; --i) { // 从后往前、减小后缀长度
            int j = i;

            while (j >= 0) {  // 公共后缀，继续向前查
                int correspondingPos = len - 1 - i + j; // pat对应的尾部位置

                if (pat[j] == pat[correspondingPos]) { // 相等的话则继续向前
                    --j;
                } else {
                    break;
                }
            }

            suffixes[i] = i - j;
        }

        return suffixes;
    }

    /**
     * 计算好后缀跳转表
     */
    static int[] preGoodSuffixes(String template, int[] suffixes) {
        char[] pat = template.toCharArray();

        int len = pat.length;
        int[] goodSuffixTable = new int[len];

        // 初始假设没有子串或前缀与好后缀一致,全部设为模板串长度
        for (int i = 0; i < len; ++i)
            goodSuffixTable[i] = len;

        // 有子串与好后缀一致
        for (int i = 0; i < len - 1; ++i) {// 从后往前遍历后缀数组
            int mismatchPoint = len - 1 - suffixes[i];
            goodSuffixTable[mismatchPoint] = len - 1 - i;  // 跳到子串位置
        }

        // 有前缀与好后缀一致
        for (int i = len - 1; i >= 0; --i) { // 向前遍历每一个失配点
            if (suffixes[i] == i + 1) { // 后缀也是前缀
                for (int j = 0; j < len - 1 - i; ++j) { // 从前往后遍历前缀直到最长后缀
                    if (goodSuffixTable[j] == len) // 如果子串匹配已经符合，则不再考虑前缀匹配
                        goodSuffixTable[j] = len - 1 - i;
                }
            }
        }

        return goodSuffixTable;
    }
}