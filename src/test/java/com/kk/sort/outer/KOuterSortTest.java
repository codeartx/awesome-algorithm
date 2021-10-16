package com.kk.sort.outer;

import com.kk.util.FileUtils;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class KOuterSortTest {
    String original = "data/original.dat";

    KOuterSort kOuterSort = new KOuterSort();

    @Test
    public void genTestData() throws FileNotFoundException {
        Random random = new Random();
        PrintWriter writer = new PrintWriter(original);

        for (int i = 0; i < 100 * 1000; i++) {
            writer.println(random.nextInt(Integer.MAX_VALUE));
        }
    }

    @Test
    public void nextMergeFileTest() throws IOException {
        System.out.println("-->" + kOuterSort.nextMergeFile("ori-seq-0"));
        System.out.println("-->" + kOuterSort.nextMergeFile("ori-merge-0"));
    }

    @Test
    public void sortTest() throws IOException {
        FileUtils.cleanData();

        kOuterSort.sort(original);
    }
}
