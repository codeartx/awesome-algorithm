package com.kk.datastructure.tree2.bst;

import java.util.ArrayList;

/**
 * 二叉搜索树
 */
public class BST {
    // 根节点
    BSTNode root;

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
    private BSTNode put(BSTNode root, String key, Object val) {
        if (root == null) { // 遍历到叶子节点，创建新的叶子，插入到上个节点
            return new BSTNode(key, val);
        }

        int cmp = root.key.compareTo(key);

        if (cmp == 0) { // 相等，更新value
            root.val = val;
        } else if (cmp > 0) { // 插入到左子树，将插入完成后左子树关联到左连接上
            root.left = put(root.left, key, val);
        } else {
            root.right = put(root.right, key, val); // 向右子树插入，完成后将新的右子树连接到
        }

        return root;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    /**
     * 删除最小值节点
     */
    private BSTNode deleteMin(BSTNode root) {
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

    private BSTNode delete(BSTNode root, String key) {
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
            BSTNode succeed = minNode(root.right);
            // 拷贝后继值
            root.val = succeed.val;
            // 删除后继节点
            root.right = deleteMin(root.right);
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
    private Object get(BSTNode node, String key) {
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
    public BSTNode minNode(BSTNode root) {
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

    protected void inorderTraverse(BSTNode root, ArrayList<Object> valuesList) {
        if (root == null) {
            return;
        }

        // 先递归将左子树遍历好
        inorderTraverse(root.left, valuesList);
        // 中间访问根节点
        valuesList.add(root.val);
        // 后递归将右子树遍历好
        inorderTraverse(root.right, valuesList);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("BST:[");

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
