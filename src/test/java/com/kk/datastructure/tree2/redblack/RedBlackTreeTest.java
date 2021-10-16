package com.kk.datastructure.tree2.redblack;

import com.kk.util.RedBlackTreePrinter;
import org.junit.Before;
import org.junit.Test;

/**
 * 红黑树
 */
public class RedBlackTreeTest {
    RedBlackTree redBlackTree = new RedBlackTree();

    @Before
    public void before() {
        System.out.println("Before-->");
    }

    @Test
    public void putTest() {
        redBlackTree.put("zz", "zz");
        redBlackTree.put("ww", "ww");
        redBlackTree.put("ll", "ll");
        redBlackTree.put("hh", "hh");
        redBlackTree.put("kk", "kk");
        redBlackTree.put("mm", "mm");
        redBlackTree.put("aa", "bb");
        redBlackTree.put("pp", "pp");
//        redBlackTree.put("xx", "xx");
        redBlackTree.put("nn", "nn");

        RedBlackTreePrinter.printNode(redBlackTree.root);
    }

    @Test
    public void findNodeTest() {
        putTest();

        System.out.println("find(lisi)-->" + redBlackTree.findNearNode("lisi").val);
        System.out.println("find(gg)-->" + redBlackTree.findNearNode("gg").val);
        System.out.println("find(xx)-->" + redBlackTree.findNearNode("xx").val);
    }

    @Test
    public void getTest() {
        putTest();

        System.out.println("get(lisi)-->" + redBlackTree.get("lisi"));
        System.out.println("get(kk)-->" + redBlackTree.get("kk"));
        System.out.println("get(gg)-->" + redBlackTree.get("gg"));
    }

    @Test
    public void leftRotateTest() {
        putTest();

        redBlackTree.root.left.left = redBlackTree.leftRotate(redBlackTree.root.left.left);

        System.out.println("左旋lisi后-->");
        RedBlackTreePrinter.printNode(redBlackTree.root);
    }

    @Test
    public void rightRotateTest() {
        putTest();

        redBlackTree.root.left.left = redBlackTree.rightRotate(redBlackTree.root.left.left);

        System.out.println("右旋lisi后-->");
        RedBlackTreePrinter.printNode(redBlackTree.root);
    }

    @Test
    public void flipColorsTest() {
        putTest();

        redBlackTree.flipColors(redBlackTree.root.left.left);

        System.out.println("颜色翻转-->");
        RedBlackTreePrinter.printNode(redBlackTree.root);
    }

    @Test
    public void minNodeTest() {
        putTest();

        System.out.println("minNode-->" + redBlackTree.minNode(redBlackTree.root));
    }

    @Test
    public void succeedNodeTest() {
        putTest();

        System.out.println("succeedNode-->" + redBlackTree.succeedNode(redBlackTree.root.left));
    }

    @Test
    public void deleteChildTest() {
        putTest();

        DeletePoint deletePoint = redBlackTree.deleteEndChild(redBlackTree.root.left, redBlackTree.root.left.left);

        RedBlackTreePrinter.printNode(redBlackTree.root);
    }

    @Test
    public void deleteTest() {
        putTest();

        redBlackTree.delete("pp");
//        redBlackTree.delete("aa");
//        redBlackTree.delete("ww");

        System.out.println("delete-->\n");

        RedBlackTreePrinter.printNode(redBlackTree.root);
    }
}
