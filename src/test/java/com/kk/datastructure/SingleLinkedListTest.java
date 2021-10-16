package com.kk.datastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 单向链表
 */

public class SingleLinkedListTest {
    SingleLinkedList linkedList = new SingleLinkedList();

    @Before
    public void init() {
        linkedList.add(1).add(2).add(5).add(8).add(10);
        System.out.println("init-->" + linkedList);
    }

    @After
    public void after() {
        System.out.println("after-->" + linkedList);
    }

    @Test
    public void addTest() {
        linkedList.add(7).add(8);
    }

    @Test
    public void insertTest() {
        linkedList.insert(2, 8)
                  .insert(1, 10);
    }

    @Test
    public void deleteTest() {
        linkedList.delete(2);
    }

    @Test
    public void getTest() {
        System.out.println("get(2)-->" + linkedList.get(2));
    }

    @Test
    public void findTest(){
        System.out.println("find(5)-->" + linkedList.find(5).val);
    }
}
