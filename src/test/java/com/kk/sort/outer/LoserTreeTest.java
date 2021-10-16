package com.kk.sort.outer;

import org.junit.Test;

public class LoserTreeTest {
    LoserTree loserTree = new LoserTree();

    @Test
    public void createTest() {
        int[] branches = {10, 9, 20, 6, 12};
        loserTree.create(branches);

        System.out.println(loserTree);
    }

    @Test
    public void peekTest() {
        createTest();

        System.out.println("-->" + loserTree.peek());
    }

    @Test
    public void popTest() {
        createTest();

        System.out.println("pop-->" + loserTree.pop(15));
        System.out.println(loserTree);

        for (int i = 0; i < 6; i++) {
            System.out.print(i == 0 ? "" : "," + loserTree.pop());
        }
    }
}
