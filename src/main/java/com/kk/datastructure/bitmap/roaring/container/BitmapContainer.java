package com.kk.datastructure.bitmap.roaring.container;

import static java.lang.Long.numberOfTrailingZeros;

public class BitmapContainer extends Container {
    protected static int MAX_CAPACITY = 1 << 16;

    long[] data;
    int cardinality;

    public BitmapContainer() {
        this.cardinality = 0;
        this.data = new long[MAX_CAPACITY / 64];
    }

    void fromArrayContainer(ArrayContainer arrayContainer) {
        this.cardinality = arrayContainer.cardinality;

        for (int i = 0; i < arrayContainer.cardinality; ++i) {
            char val = arrayContainer.data[i];
            data[(val) / 64] |= (1L << val);
        }
    }

    @Override
    public Container add(char val) {
        // 高位索引
        int idx = val >>> 6;
        data[idx] = data[idx] | (1 << val);
        ++cardinality;
        return this;
    }

    @Override
    public boolean contains(char val) {
        return (data[val >>> 6] & (1L << val)) != 0;
    }

    @Override
    public Container remove(char i) {
        int index = i >>> 6;
        long item = data[index];
        long mask = 1L << i;

        cardinality--;
        data[index] = item & ~mask;

        // 判断是否需要降级为array容器
        if (cardinality == ArrayContainer.DEFAULT_MAX_SIZE) {// this is
            this.toArrayContainer();
        }

        return this;
    }

    ArrayContainer toArrayContainer() {
        ArrayContainer arrayContainer = new ArrayContainer(cardinality);
        arrayContainer.fromBitmapContainer(this);

        if (arrayContainer.getCardinality() != cardinality) {
            throw new RuntimeException("Internal error.");
        }

        return arrayContainer;
    }

    void fillArray(char[] array) {
        int pos = 0;
        int base = 0;
        for (int k = 0; k < data.length; ++k) {
            long bitset = data[k];
            while (bitset != 0) {
                array[pos++] = (char) (base + numberOfTrailingZeros(bitset));
                bitset &= (bitset - 1);
            }
            base += 64;
        }
    }

}
