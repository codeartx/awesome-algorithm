package com.kk.datastructure.bitmap.roaring.container;

import com.kk.datastructure.bitmap.roaring.util.Util;

import java.util.Arrays;

public class ArrayContainer extends Container {
    private static int DEFAULT_INIT_SIZE = 4;
    // 存储阈值，>4096的时候，转换为bitmap容器
    static int DEFAULT_MAX_SIZE = 4096;

    char[] data;
    int cardinality = 0;

    public ArrayContainer() {
        this(DEFAULT_INIT_SIZE);
    }

    public ArrayContainer(int capacity) {
        data = new char[capacity];
    }

    void fromBitmapContainer(BitmapContainer bitmapContainer) {
        this.cardinality = bitmapContainer.cardinality;
        bitmapContainer.fillArray(data);
    }

    @Override
    public Container add(char val) {
        if (isToEnd(val)) { // 追加到末尾
            if (handleAddCommon()) return toBitmapContainer().add(val);

            data[cardinality++] = val;
        } else { // 在中间插入
            int loc = Util.binarySearch(data, 0, cardinality, val);

            // 找到了，不必重复加入
            if (loc >= 0) return this;

            // 小于0，未找到（返回后继元素的下标）才加入
            if (handleAddCommon()) return toBitmapContainer().add(val);

            // 将大于x的元素右移
            int succeedPos = -loc;
            System.arraycopy(data, succeedPos - 1,
                    data, succeedPos, cardinality - succeedPos + 1);
            // 插入合适的位置
            data[succeedPos - 1] = val;
            ++cardinality;
        }

        return this;
    }

    private boolean isToEnd(char x) { // 新元素大于当前存在的最大值
        return cardinality == 0 ||
                (cardinality > 0 && (x) > (data[cardinality - 1]));
    }

    private boolean handleAddCommon() {
        // 超过容量转换为bitmap容器
        if (cardinality >= DEFAULT_MAX_SIZE) {
            return true;
        }

        if (cardinality >= this.data.length) {
            increaseCapacity();
        }

        return false;
    }


    public BitmapContainer toBitmapContainer() {
        BitmapContainer bc = new BitmapContainer();
        bc.fromArrayContainer(this);
        return bc;
    }


    private void increaseCapacity() {
        int newCapacity = (this.data.length == 0) ? DEFAULT_INIT_SIZE : this.data.length * 2;

        if (newCapacity > ArrayContainer.DEFAULT_MAX_SIZE) {
            newCapacity = ArrayContainer.DEFAULT_MAX_SIZE;
        }

        // 扩容
        this.data = Arrays.copyOf(this.data, newCapacity);
    }

    @Override
    public boolean contains(char val) {
        return Util.binarySearch(data, 0, cardinality, val) >= 0;
    }

    @Override
    public Container remove(char x) {
        int loc = Util.binarySearch(data, 0, cardinality, x);
        if (loc >= 0) {
            removeAtIndex(loc);
        }
        return this;
    }

    void removeAtIndex(int loc) {
        System.arraycopy(data, loc + 1, data, loc, cardinality - loc - 1);
        --cardinality;
    }
}
