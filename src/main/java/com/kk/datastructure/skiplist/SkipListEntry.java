package com.kk.datastructure.skiplist;

/**
 * 跳表条目
 */
public class SkipListEntry {
    // 数据
    public String key;
    public Integer value;

    // 上下左右指针
    public SkipListEntry up;
    public SkipListEntry down;

    public SkipListEntry left;
    public SkipListEntry right;

    // 负无穷、正无穷作为每层的头、尾节点
    public static final String INFINITE_NEGATIVE = "-oo";
    public static final String INFINITE_POSITIVE = "+oo";

    public SkipListEntry(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
