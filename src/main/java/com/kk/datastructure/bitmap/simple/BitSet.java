package com.kk.datastructure.bitmap.simple;

public class BitSet {
    public static final int ADDRESS_BITS_PER_LONG = 6; // 每个long的地址数

    // long数组存放数据
    private long[] data;

    /**
     * 构造方法,传入预期的最大pos.
     */
    public BitSet(int size) {
        this.data = new long[size >> ADDRESS_BITS_PER_LONG];
    }

    /**
     * 获取指定pos的布尔值
     */
    public boolean get(int pos) {
        return (data[idxFromPos(pos)] & (1 << offset(pos))) != 0;
    }

    /**
     * 将对应位置的值设置为传入的bool值
     */
    public void set(int pos, boolean isTrue) {
        int idx = idxFromPos(pos);

        if (isTrue) {
            data[idx] |= 1 << offset(pos); // 对应的offset设置为1
        } else {
            data[idx] &= ~(1 << offset(pos)); // 对应的offset设置为0
        }
    }


    /**
     * 从pos推导对应的索引
     */
    private int idxFromPos(int pos) {
        return pos >> ADDRESS_BITS_PER_LONG;
    }

    /**
     * 偏移
     */
    public int offset(int pos) {
        return pos % 64;
    }

    public static void main(String[] args) {
        BitSet bitSet = new BitSet(1000);
        bitSet.set(10, true);
        bitSet.set(100, true);

        System.out.println(bitSet.get(9));
        System.out.println(bitSet.get(18));
        System.out.println(bitSet.get(100));

    }
}