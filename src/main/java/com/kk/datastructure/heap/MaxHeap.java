package com.kk.datastructure.heap;

/**
 * 最大堆
 */
public class MaxHeap {
    int capacity = 10;

    // 以存入的节点数
    int size = 0;

    // 数据数组
    public int[] data = new int[capacity];

    public void add(int val) {
        if (size == capacity) {
            throw new RuntimeException("堆已满");
        }

        size++;
        data[size - 1] = val;

        // 上浮调整堆
        siftUp(size - 1);
    }

    /**
     * 弹出顶部最大值
     */
    public int pop() {
        if (size == 0) {
            return 0;
        }

        int max = data[0];
        // 用尾部替换堆顶
        data[0] = data[size - 1];
        data[size - 1] = 0;

        size--;

        // 向下调整堆
        siftDown(0);

        return max;
    }

    /**
     * 更新根节点最大值
     */
    public void replace(int val) {
        if (size == 0) {
            return;
        }

        // 更新
        data[0] = val;
        siftDown(0);
    }

    /**
     * 堆化
     */
    public void heapify(int[] arr) {
        if (arr.length > capacity) {
            throw new IndexOutOfBoundsException();
        }

        data = arr;
        size = arr.length;

        // 对每个节点进行
        for (int i = 0; i < size; i++) {
            siftUp(i);
        }
    }

    /**
     * 父节点索引
     */
    private int parent(int childId) {
        int parent = (childId - 1) / 2;
        return parent >= 0 ? parent : -1; // 小于0，说明无父节点，当前节点是根节点
    }

    /**
     * 左子
     */
    private int leftChild(int parent) {
        int left = 2 * parent + 1;
        return left < size ? left : -1; // 越界说明不存在
    }

    /**
     * 左子
     */
    private int rightChild(int parent) {
        int right = 2 * parent + 2;
        return right < size ? right : -1; // 越界说明不存在
    }

    /**
     * 上浮：当前节点大于父节点，和父节点交换，递归向上直至不满足条件
     */
    private void siftUp(int i) {
        int parent = parent(i);

        if (parent == -1) { // 超过根节点
            return;
        }

        if (data[parent] >= data[i]) { // 父节点大于等于子节点,插入到争取的位置
            return;
        }

        // 父节点小于子节点，交换
        swap(parent, i);

        // 递归向上处理
        siftUp(parent);
    }

    /**
     * 下沉：当前节点小于某个子节点，找到较大的子节点并交换，递归向下直至满足条件
     */
    private void siftDown(int i) {
        int leftChild = leftChild(i);
        int rightChild = rightChild(i);

        if (leftChild == -1 && rightChild == -1) { // 没有左右子，直接退出
            return;
        }

        int largerChild = leftChild; // 假定左子较大

        if (rightChild != -1 && data[rightChild] > data[leftChild]) {
            largerChild = rightChild;
        }

        if (data[largerChild] > data[i]) {
            swap(largerChild, i);

            // 向下递归
            siftDown(largerChild);
        }
    }

    /**
     * 交换
     */
    private void swap(int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }
}
