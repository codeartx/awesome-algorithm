package com.kk.datastructure.heap;

import com.kk.util.PrintHelper;
import org.junit.After;
import org.junit.Test;

public class MaxHeapTest {
    MaxHeap maxHeap = new MaxHeap();

    @After
    public void after() {
        System.out.println();
        System.out.println("after-->");
        PrintHelper.printArr(maxHeap.data);
    }

    @Test
    public void addTest() {
        maxHeap.add(2);
        maxHeap.add(7);
        maxHeap.add(10);
        maxHeap.add(0);
        maxHeap.add(-18);
        maxHeap.add(20);
        maxHeap.add(44);

        System.out.println("before-->");
        PrintHelper.printArr(maxHeap.data);
        System.out.println();
    }

    @Test
    public void popTest() {
        System.out.println("poping-->");

        for (int i = 0; i < maxHeap.data.length; i++) {
            System.out.print("\t" + maxHeap.pop());
        }
    }

    @Test
    public void replaceTest() {
        maxHeap.replace(15);
    }

    @Test
    public void heapifyTest() {
        int[] inordeNums = {4, 2, 8, 15, 7, 10};

        maxHeap.heapify(inordeNums);

        int size = maxHeap.size;

        for (int i = 0; i < size; i++) {
            System.out.print("\t" + maxHeap.pop());
            ;
        }
    }
}
