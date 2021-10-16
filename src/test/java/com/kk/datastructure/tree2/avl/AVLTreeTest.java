package com.kk.datastructure.tree2.avl;

import com.kk.util.AVLPrinter;
import org.junit.Before;
import org.junit.Test;

/**
 * 二叉搜索树
 */
public class AVLTreeTest {
    AVLTree avl = new AVLTree();

    @Before
    public void before() {
        putTest();
        avl.refreshDepth();

        System.out.println("Before-->");
        AVLPrinter.printNode(avl.root);

        System.out.println();
    }

    //    @After
    public void after() {
        System.out.println();
        System.out.println("After-->");

        AVLPrinter.printNode(avl.root);
    }

    @Test
    public void putTest() {
        avl.put("zhaoliu", "zhaoliu");
        avl.put("wangwu", "wangwu");
        avl.put("lisi", "lisi");

        avl.put("huang", "huang");
        avl.put("kk", "kk");
        avl.put("mm", "mm");
    }

    @Test
    public void deleteMinTest() {
        avl.deleteMin();
        AVLPrinter.printNode(avl.root);
        avl.deleteMin();
        AVLPrinter.printNode(avl.root);
        avl.deleteMin();

        after();
    }

    @Test
    public void sizeTest() {
        System.out.println("size()-->" + avl.size());
    }

    @Test
    public void getTest() {
        System.out.println("get(wangwu)-->" + avl.get("wangwu"));
    }

    @Test
    public void minNodeTest() {
        AVLNode minNode = avl.minNode(avl.root);

        System.out.println("minNode-->");
        AVLPrinter.printNode(minNode);
    }

    @Test
    public void deleteTest() {
        avl.delete("lisi");
        System.out.println("delete(lisi)-->");

        after();
    }

    @Test
    public void refreshDepthTest() {
        int depth = avl.refreshDepth();
        System.out.println("depth-->" + depth);
    }

    @Test
    public void isUnbanlanceTest() {
        avl.refreshDepth();
        after();

        System.out.println("-->" + avl.isUnbanlance(avl.root));
    }

    @Test
    public void leftRotateTest() {
        System.out.println("leftRotate-->lisi");
        // 旋转后连接到父层
        avl.root.left.left = avl.leftRotate(avl.root.left.left);
        avl.refreshDepth();

        after();
    }

    @Test
    public void rightRotateTest() {
        System.out.println("rightRotate-->lisi");
        // 旋转后连接到父层
        avl.root.left.left = avl.rightRotate(avl.root.left.left);
        avl.refreshDepth();

        after();
    }

    @Test
    public void isToLeftTest() {
        System.out.println("isToLeft-->" + avl.isToLeft(avl.root));
    }

    @Test
    public void isToRightTest() {
        System.out.println("isToRight-->" + avl.isToRight(avl.root));
    }

    @Test
    public void isLL() {
        System.out.println("isLL root-->" + avl.isLL(avl.root));
        System.out.println("isLL lisi-->" + avl.isLL(avl.root.left.left));
    }

    @Test
    public void balanceTest() {
        System.out.println("balance-->");

        avl.root = avl.balance(avl.root);

        after();
    }
}
