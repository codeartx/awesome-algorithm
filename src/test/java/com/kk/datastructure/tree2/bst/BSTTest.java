package com.kk.datastructure.tree2.bst;

import com.kk.util.BSTPrinter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 二叉搜索树
 */
public class BSTTest {
    BST bst = new BST();

    @Before
    public void before() {
        putTest();
        System.out.println("Before-->");
        BSTPrinter.printNode(bst.root);
        System.out.println();
    }

    @After
    public void after() {
        System.out.println();
        System.out.println("After-->");

        BSTPrinter.printNode(bst.root);
    }

    @Test
    public void putTest() {
        bst.put("zhaoliu", "zhaoliu");
        bst.put("wangwu", "wangwu");
        bst.put("lisi", "lisi");

        bst.put("huang", "huang");
        bst.put("kk", "kk");
        bst.put("mm", "mm");
    }

    @Test
    public void deleteMinTest() {
        bst.deleteMin();
        BSTPrinter.printNode(bst.root);
        bst.deleteMin();
        BSTPrinter.printNode(bst.root);
        bst.deleteMin();
    }

    @Test
    public void sizeTest() {
        System.out.println("size()-->" + bst.size());
    }

    @Test
    public void getTest() {
        System.out.println("get(wangwu)-->" + bst.get("wangwu"));
    }

    @Test
    public void minNodeTest() {
        BSTNode minNode = bst.minNode(bst.root);

        System.out.println("minNode-->");
        BSTPrinter.printNode(minNode);
    }

    @Test
    public void deleteTest() {
        bst.delete("lisi");
        System.out.println("delete(lisi)-->");
    }

    @Test
    public void inorderTraverseTest(){
        System.out.println("inorderTraverseTest-->" + bst);
    }
}
