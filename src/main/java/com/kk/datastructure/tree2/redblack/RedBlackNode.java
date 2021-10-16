package com.kk.datastructure.tree2.redblack;

/**
 * 红黑树节点
 */
public class RedBlackNode {
    public String key;
    public Object val;

    public RedBlackNode parent;
    public RedBlackNode left;
    public RedBlackNode right;

    public static final int COLOR_BLACK = 0;
    public static final int COLOR_RED = 1;

    // 默认红色
    public int color = COLOR_RED;

    public RedBlackNode(String key, Object val, int color) {
        this.key = key;
        this.val = val;
        this.color = color;
    }

    public RedBlackNode(String key, int color) {
        this.key = key;
        this.color = color;
    }

    public RedBlackNode(String key, Object val) {
        this.key = key;
        this.val = val;
    }

    public boolean isRed() {
        return color == COLOR_RED;
    }

    public boolean isBlack() {
        return color == COLOR_BLACK;
    }

    public void toRed() {
        color = COLOR_RED;
    }

    public void toBlack() {
        color = COLOR_BLACK;
    }

    @Override
    public String toString() {
        return key + "->" + val;
    }
}
