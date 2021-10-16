package com.kk.datastructure.tree2.avl;

/**
 * 平衡二叉搜索树节点
 */
public class AVLNode {
    public String key;
    public Object val;

    public AVLNode left;
    public AVLNode right;

    // 当前节点深度：表示到最长分支的距离
    public int depth;

    public AVLNode(String key) {
        this.key = key;
    }

    public AVLNode(String key, Object val) {
        this.key = key;
        this.val = val;
    }
}
