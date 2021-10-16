package com.kk.datastructure.tree2.bst;

/**
 * 二叉搜索树节点
 */
public class BSTNode {
    public String key;
    public Object val;

    public BSTNode left;
    public BSTNode right;

    public BSTNode(String key) {
        this.key = key;
    }

    public BSTNode(String key, Object val) {
        this.key = key;
        this.val = val;
    }
}
