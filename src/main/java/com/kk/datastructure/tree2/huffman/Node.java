package com.kk.datastructure.tree2.huffman;

public class Node implements Comparable<Node> {
    public String key;
    public int weight; // 权重，或者单词出现的频率

    // 指针
    public  Node parent;
    public Node left;
    public Node right;


    public Node(int weight) {
        this.weight = weight;
    }

    public Node(String key, int weight) {
        this.key = key;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        return weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key='" + key + '\'' +
                ", weight=" + weight +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}