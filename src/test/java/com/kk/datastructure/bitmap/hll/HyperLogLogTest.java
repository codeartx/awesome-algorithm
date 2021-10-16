package com.kk.datastructure.bitmap.hll;

import org.junit.Test;

public class HyperLogLogTest {
    @Test
    public void test() {
        HyperLogLog hyperLogLog = new HyperLogLog();

        hyperLogLog.add("1");
        hyperLogLog.add("2");
        hyperLogLog.add("3");
        hyperLogLog.add("c");
        hyperLogLog.add("c");
        hyperLogLog.add("d");

        System.out.println("-->" + hyperLogLog.cardinality());
    }
}
