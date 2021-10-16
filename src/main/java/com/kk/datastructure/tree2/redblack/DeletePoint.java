package com.kk.datastructure.tree2.redblack;

/**
 * 删除点
 */
public class DeletePoint {
    public RedBlackNode parent;
    public RedBlackNode del;
    public RedBlackNode son;
    public boolean isLeft;

    public DeletePoint(RedBlackNode parent, RedBlackNode del, RedBlackNode son, boolean isLeft) {
        this.parent = parent;
        this.del = del;
        this.son = son;
        this.isLeft = isLeft;
    }
}
