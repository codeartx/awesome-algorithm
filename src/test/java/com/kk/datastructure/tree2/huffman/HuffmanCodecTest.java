package com.kk.datastructure.tree2.huffman;

import com.kk.util.HuffmanPrinter;
import org.junit.Test;

public class HuffmanCodecTest {
    HuffmanCodec huffmanCodec = new HuffmanCodec();

    String plain = "we will we will r u";

    @Test
    public void putTest() {
        huffmanCodec.put(
                new Node("2", 2),
                new Node("3", 3),
                new Node("6", 6),
                new Node("8", 8),
                new Node("9", 9)
        );

        System.out.println("huffman tree-->");
        HuffmanPrinter.printNode(huffmanCodec.root);
    }

    @Test
    public void encodeTest() {
        System.out.println("encodes-->" + huffmanCodec.encode(plain));
        HuffmanPrinter.printNode(huffmanCodec.root);
    }

    @Test
    public void codesMapTest() {
        putTest();

        System.out.println("-->" + huffmanCodec.codesMap());
        ;
    }
}
