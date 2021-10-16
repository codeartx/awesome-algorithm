package com.kk.datastructure.bitmap.roaring;

import com.kk.datastructure.bitmap.roaring.container.Container;
import com.kk.datastructure.bitmap.roaring.util.Util;

import java.util.Arrays;

public class ContainersArray {
    static final int INITIAL_CAPACITY = 4;

    // 高位索引，char占位2个字节，即16位
    char[] highBits;
    // 索引对应的容器
    Container[] containers;

    int size = 0;

    protected ContainersArray() {
        this.highBits = new char[INITIAL_CAPACITY];
        this.containers = new Container[INITIAL_CAPACITY];
    }

    void insertAt(int i, char key, Container value) {
        extend(1);

        // i位的元素后移后插入i位
        System.arraycopy(highBits, i, highBits, i + 1, size - i);
        highBits[i] = key;
        System.arraycopy(containers, i, containers, i + 1, size - i);
        containers[i] = value;

        size++;
    }

    int getId(char index) {
        if ((size == 0) || (highBits[size - 1] == index)) {
            return size - 1;
        }

        return this.binarySearch(0, size, index);
    }

    private int binarySearch(int begin, int end, char key) {
        return Util.binarySearch(highBits, begin, end, key);
    }

    protected Container getContainerAtIndex(int i) {
        return this.containers[i];
    }

    void setContainerAtIndex(int i, Container c) {
        this.containers[i] = c;
    }

    /**
     * 扩展数组
     */
    void extend(int neddSpace) {
        if (this.size + neddSpace > this.highBits.length) {
            int newCapacity = 2 * (this.size + neddSpace);

            this.highBits = Arrays.copyOf(this.highBits, newCapacity);
            this.containers = Arrays.copyOf(this.containers, newCapacity);
        }
    }

    protected Container getContainer(char index) {
        int i = this.binarySearch(0, size, index);

        if (i < 0) return null;

        return this.containers[i];
    }

    void removeAtIndex(int i) {
        System.arraycopy(highBits, i + 1, highBits, i, size - i - 1);
        highBits[size - 1] = 0;
        System.arraycopy(containers, i + 1, containers, i, size - i - 1);
        containers[size - 1] = null;
        size--;
    }
}
