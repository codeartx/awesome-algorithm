package com.kk.datastructure.bitmap.hll;

import org.apache.commons.codec.digest.MurmurHash3;

public class HyperLogLog {
    // 存放每桶最大值的桶
    int m = 64;
    int[] buckets = new int[m];

    public void add(String key) {
        int hash32 = Math.abs(MurmurHash3.hash32(key));

        int index = hash32 & 63;
        int val = hash32 >> m;
        int first1Pos = first1Pos(val);

        int lastMaxPos = buckets[index];

        if (first1Pos > lastMaxPos) {
            buckets[index] = first1Pos;
        }
    }

    private int first1Pos(int val) {
        String valBits = Integer.toBinaryString(val);

        int len = valBits.length();

        for (int i = len - 1; i > 0; i--) {
            if (valBits.charAt(i) == '1') {
                return len - i;
            }
        }

        return -1;
    }

    public long cardinality() {
        double sum = 0;

        double zeros = 0.0;

        for (int j = 0; j < m; j++) {
            int val = buckets[j];

            sum += 1.0 / (1 << val);

            if (val == 0) {
                zeros++;
            }
        }

        double estimate = adjustConst() * (1 / sum);

        // 对0校正
        if (estimate <= (5.0 / 2.0) * m) {
            return Math.round(linearCounting(m, zeros));
        } else {
            return Math.round(estimate);
        }
    }


    /**
     * 修正常数
     */
    private double adjustConst() {
        return 0.709 * m * m;
    }

    /**
     * 线性计数
     */
    private double linearCounting(int m, double zeroCounnt) {
        return m * Math.log(m / zeroCounnt);
    }
}
