package com.kk.datastructure.tree2.huffman;

import java.util.PriorityQueue;


/**
 * Huffman树
 */
public class HuffmanTree {
    int size = 0;
    Node root;

    // 记录所有的叶子节点，无需再遍历得到
    PriorityQueue<Node> leafs = new PriorityQueue<>();

    /**
     * 存入多个节点
     */
    public void put(Node... nodes) {
        // 入队列排序
        PriorityQueue<Node> queue = new PriorityQueue<>(nodes.length);

        if (null != root) {
            queue.add(root);
        }

        for (Node node : nodes) {
            queue.add(node);
            leafs.add(node);
            size++;
        }

        // 不断取出最小的2个值组合一个父节点，直到队列为空
        while (queue.size() != 1) {
            Node first = queue.poll();
            Node second = queue.poll();

            // 合并得到一个父节点
            createParent(first, second);

            queue.add(root);
        }
    }

    private void createParent(Node first, Node second) {
        root = new Node(first.weight + second.weight);
        root.left = first;
        root.right = second;

        first.parent = root;
        second.parent = root;
    }

    /**
     * 计算最短路径长度
     */
    int wpl() {
        int wpl = 0;

        for (Node leaf : leafs) {
            int depth = depthForLeaf(leaf);
            wpl += leaf.weight * depth;
        }

        return wpl;
    }

    /**
     * 获取一个叶子的深度
     */
    int depthForLeaf(Node node) {
        int depath = 0;

        while (node.parent != null) { // 不断向上判断父节点
            depath++;
            node = node.parent;
        }

        return depath;
    }
}
