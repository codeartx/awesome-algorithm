package com.kk.datastructure.skiplist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SkipListTest {
    SkipList skipList = new SkipList();

    @Before
    public void printBefore() {
        System.out.println("before-->");
        System.out.println(skipList);

        putTest();
    }

    @After
    public void printAfter() {
        System.out.println("after-->");
        System.out.println(skipList);
    }

    @Test
    public void putTest() {
        skipList.put("10", 110);
        skipList.put("8", 18);
        skipList.put("6", 16);
        skipList.put("4", 14);
    }

    @Test
    public void getTest() {
        System.out.println("get(8)-->" + skipList.get("8"));
    }

    @Test
    public void removeTest() {
        skipList.remove("8");

        getTest();
    }
}
