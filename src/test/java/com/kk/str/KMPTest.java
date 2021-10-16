package com.kk.str;

import com.kk.util.PrintHelper;
import org.junit.Test;

public class KMPTest {
    String target = "cababacababc";
    String temp = "ababc";

    @Test
    public void calJumpTableTest() {
        System.out.println("-->");

        PrintHelper.printArr(KMP.calJumpTable(temp));
    }

    @Test
    public void findTest() {
        KMP.find(target, temp);
    }
}
