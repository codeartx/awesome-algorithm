package com.kk.datastructure.tree2.avl;

import java.util.ArrayList;

/**
 * 平衡二叉搜索树
 */
public class AVLTree {
    // 根节点
    AVLNode root;

    int size;

    /**
     * 添加新的节点
     */
    public void put(String key, Object val) {
        root = put(root, key, val);
        size++;
    }

    /**
     * 递归尝试插入到子树的叶子节点，插入完成后将新的左子树或右子树返回给父调用
     */
    private AVLNode put(AVLNode root, String key, Object val) {
        if (root == null) { // 遍历到叶子节点，创建新的叶子，插入到上个节点
            return new AVLNode(key, val);
        }

        int cmp = root.key.compareTo(key);

        if (cmp == 0) { // 相等，更新value
            root.val = val;
        } else if (cmp > 0) { // 插入到左子树，将插入完成后左子树关联到左连接上
            root.left = put(root.left, key, val);
        } else {
            root.right = put(root.right, key, val); // 向右子树插入，完成后将新的右子树连接到
        }

        // 插入成功、回到父调用、准备连接到父层的时候做平衡处理
        refreshDepth(root); // 该子树深度

        if (isUnbanlance(root)) { // 失衡 --> 调整平衡
            root = balance(root); // 返回平衡后的新树根
        }

        return root;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    /**
     * 删除最小值节点
     */
    private AVLNode deleteMin(AVLNode root) {
        if (root == null) {
            return null;
        }

        if (root.left == null) { // 无左子树，已经是最小节点
            return root.right; // 将最小节点的右节点返回给父节点
        }

        root.left = deleteMin(root.left); // 删除左子树的最小节点，成功后返回连接到当前节点的左侧
        return root;
    }

    /**
     * 删除任意节点
     * 递归找到要删除的节点，
     * 用后继节点替换删除节点
     * 删除后继节点
     */
    public void delete(String key) {
        root = delete(root, key);
        size--;
    }

    private AVLNode delete(AVLNode root, String key) {
        if (root == null) {
            return null;
        }

        int cmp = root.key.compareTo(key);

        if (cmp > 0) {
            root.left = delete(root.left, key); // 递归到左子树删除
        } else if (cmp < 0) {
            root.right = delete(root.right, key); // 递归到右子树删除
        } else if (cmp == 0) {
            // 无左子,直接将右子树拼接到父节点
            if (root.left == null) {
                return root.right;
            }

            // 无右子，直接将左子树连接到父节点
            if (root.right == null) {
                return root.left;
            }

            // 双子节点
            // 找到后继节点
            AVLNode succeed = minNode(root.right);
            // 拷贝后继值
            root.val = succeed.val;
            // 删除后继节点
            root.right = deleteMin(root.right);
        }

        refreshDepth(root); // 该子树深度

        if (isUnbanlance(root)) { // 失衡 --> 调整平衡
            root = balance(root); // 返回平衡后的新树根
        }

        // 返回最后的根节点
        return root;
    }

    /**
     * 大小
     */
    public int size() {
        return size;
    }

    /**
     * 获取某个键值
     */
    public Object get(String key) {
        return get(root, key);
    }

    /**
     * 通过递归不断查找左右子树，最终将查到的结果层层返回给父调用
     */
    private Object get(AVLNode node, String key) {
        if (node == null) { // 查找不到，返回null
            return null;
        }

        // 和当前结点比较
        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            return get(node.left, key);  // 递归在左子树查找
        } else if (cmp > 0) { // 递归在右子树查找
            return get(node.right, key);
        } else {
            return node.val; // 查找命中返回值，不再递归
        }
    }

    /**
     * 查找某棵树的节点
     */
    public AVLNode minNode(AVLNode root) {
        if (root == null) { // 最后未找到
            return null;
        }

        if (root.left == null) { // 无左子，自己就是最小
            return root;
        } else { // 有左子，向左子树查，结果返回返回给父调用
            return minNode(root.left);
        }
    }

    /**
     * 中序遍历 左子树 -> 根 -> 右子树
     */
    protected ArrayList<Object> inorderTraverse() {
        ArrayList<Object> valuesList = new ArrayList<>();

        inorderTraverse(root, valuesList);

        return valuesList;
    }

    protected void inorderTraverse(AVLNode root, ArrayList<Object> valuesList) {
        if (root == null) {
            return;
        }

        // 先递归将左子树遍历好
        inorderTraverse(root.left, valuesList);
        // 中间访问根节点
        valuesList.add(root.key + "-" + root.depth);
        // 后递归将右子树遍历好
        inorderTraverse(root.right, valuesList);
    }

    /**
     * ---------------------  AVL树相关的api
     */


    /**
     * 刷新某科子树的深度
     */
    public int refreshDepth() {
        return refreshDepth(root);
    }

    public int refreshDepth(AVLNode root) {
        if (null == root) { // null节点的深度为-1
            return -1;
        }

        root.depth = Math.max(refreshDepth(root.left), refreshDepth(root.right)) + 1; // 较深子节点的深度值+1

        // 返回当前节点的深度
        return root.depth;
    }

    /**
     * 获取某节点的深度
     */
    public int depth(AVLNode node) {
        return node != null ? node.depth : -1;
    }

    /**
     * 判断当前节点是否是失衡点 在刷新当前子树的高度后比较node.left > node.right
     */
    public boolean isUnbanlance(AVLNode node) {
        return Math.abs(depth(node.left) - depth(node.right)) > 1;
    }

    /**
     * 左旋
     * 1、父子交换 -> 接收左子 -> 返回原右子作为新的父节点，连接到上层
     */
    public AVLNode leftRotate(AVLNode parent) {
        AVLNode right = parent.right;
        AVLNode rightLeft = right.left;

        // 父子交换
        right.left = parent;

        // 接收左子
        parent.right = rightLeft;

        // 返回新的父节点
        return right;
    }

    /**
     * 右旋
     * 1、父子交换 -> 接收左子 -> 返回原左子作为新的父节点，连接到上层
     */
    public AVLNode rightRotate(AVLNode parent) {
        AVLNode left = parent.left;
        AVLNode leftRight = left.right;

        // 父子交换
        left.right = parent;

        // 接收左子
        parent.left = leftRight;

        // 返回新的父节点
        return left;
    }

    /**
     * 判断是否是LL失衡类型
     */
    public boolean isLL(AVLNode unbalance) {
        return isToLeft(unbalance) && isToLeft(unbalance.left);
    }

    /**
     * 判断是否是RR失衡类型
     */
    public boolean isRR(AVLNode unbalance) {
        return isToRight(unbalance) && isToRight(unbalance.right);
    }

    /**
     * 判断是否是LR失衡类型
     */
    public boolean isLR(AVLNode unbalance) {
        return isToLeft(unbalance) && isToRight(unbalance.left);
    }

    /**
     * 判断是否是RL失衡类型
     */
    public boolean isRL(AVLNode unbalance) {
        return isToRight(unbalance) && isToLeft(unbalance.right);
    }

    /**
     * 最深树走向是否是往左
     */
    public boolean isToLeft(AVLNode node) {
        return depth(node.left) - depth(node.right) > 0;
    }

    /**
     * 最深树走向是否是往左
     */
    public boolean isToRight(AVLNode node) {
        return depth(node.right) - depth(node.left) > 0;
    }

    /**
     * 平衡，根据不同的失衡类型，应用对应的选择策略，返回平衡后的新的根节点
     *
     * @return
     */
    public AVLNode balance(AVLNode unbalance) {
        if (isLL(unbalance)) {  // 左左 -> 失衡点右旋
            return rightRotate(unbalance);
        }

        if (isRR(unbalance)) { // 右右 -> 失衡点左旋
            return leftRotate(unbalance);
        }

        if (isLR(unbalance)) { // 左右 -> 先深点1左旋（转换为左左）-> 以失衡点右旋
            // 旋转后新的父节点连接到失衡点的左侧
            unbalance.left = leftRotate(unbalance.left);
            return rightRotate(unbalance);
        }

        if (isRL(unbalance)) { // 右左 -> 先以深点1左旋（转换为右右） -> 以失衡点左旋
            unbalance.right = rightRotate(unbalance.right);
            return leftRotate(unbalance);
        }

        return unbalance;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("AVLTree:[");

        final int[] i = {0};
        inorderTraverse()
                .forEach(obj -> {
                    if (i[0] == 0) {
                        builder.append(obj);
                    } else {
                        builder.append("\t" + obj);
                    }

                    i[0]++;
                });

        builder.append("]");

        return builder.toString();
    }
}
