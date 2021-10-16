package com.kk.datastructure.tree2.huffman;

import com.kk.util.HuffmanPrinter;
import org.junit.Test;

public class HuffmanTreeTest {
    HuffmanTree huffmanTree = new HuffmanTree();

    @Test
    public void putTest() {
        huffmanTree.put(
                new Node("2", 2),
                new Node("3", 3),
                new Node("6", 6),
                new Node("8", 8),
                new Node("9", 9)
        );

        System.out.println("huffman tree-->");
        HuffmanPrinter.printNode(huffmanTree.root);
    }

    @Test
    public void wplTest() {
        putTest();

        System.out.println("wpl-->" + huffmanTree.wpl());
    }
}
