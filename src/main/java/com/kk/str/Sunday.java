package com.kk.str;

/**
 * Sunday字符串匹配算法
 */
public class Sunday {
    static int find(String text, String pattern) {
        int txtLen = text.length(), patLen = pattern.length();

        int i = 0, j = 0;  // 分别记录text索引，pattern索引

        int startForPat = 0; // 记录模板串相对于目标串的起始位置
        int nextI;  // 记录模板串尾部在目标串的下一个字符索引

        while (i < txtLen && j < patLen) {
            // 向右逐个比较，直到不相等
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                continue;
            }

            nextI = startForPat + patLen;

            if (nextI >= txtLen) return -1;

            // 查找下一个字符在模板串的位置
            int idOfNext = idForNext(pattern, text.charAt(nextI));

            // 移动
            i += patLen - idOfNext;
            startForPat = i;

            j = 0;

            if (startForPat + patLen > txtLen) return -1;  // 如果匹配长度超过主串，匹配失败
        }

        return i <= txtLen ? startForPat : -1;
    }

    /**
     * 查找下一个字符在模板串的位置
     */
    static int idForNext(String pattern, char next) {
        int patLen = pattern.length();
        int i;

        for (i = patLen - 1; i >= 0 && next != pattern.charAt(i); i--) ;

        return i;
    }
}
