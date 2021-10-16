package com.kk.datastructure;

/**
 * 节点结构
 */
class Node {
    Object val; // 节点值
    Node next; // 下一个节点的指针

    public Node(Object val) {
        this.val = val;
    }
}

/**
 * 遍历节点时候的消费者
 */
interface NodeConsumer {
    boolean onNode(Node node, int id);
}

/**
 * 单向链表
 */
public class SingleLinkedList {
    // 头、尾节点
    Node head, tail;

    // 大小
    int size;

    /**
     * 尾部插入新节点
     */
    public SingleLinkedList add(Object val) {
        Node node = new Node(val);

        if (size == 0) { // 没有元素
            head = tail = node;
            size = 1;
            return this;
        }

        // 有元素，肯定有尾部节点，直接连在后侧
        tail.next = node;
        tail = node;

        size++;

        return this;
    }

    /**
     * 在指定位置插入
     */
    public SingleLinkedList insert(int i, Object val) {
        checkBounds(i);

        if (i == size - 1) { // 尾部插入==尾部追加
            return add(val);
        }

        Node nodeI = new Node(val);

        foreach((node, id) -> {
            if (id == i - 1) { // 找到要插入的位置
                Node curNodeI = node.next; // 当前的i节点

                node.next = nodeI; // 指向新的i节点
                nodeI.next = curNodeI; // 新旧i节点关联
                size++;

                return false;
            }

            return true;
        });

        return this;
    }

    /**
     * 删除指定位置的节点
     */
    public void delete(int id) {
        checkBounds(id);

        foreach((node, pos) -> {
            if (pos == id - 1) {
                Node prev = node;
                Node succeed = node.next.next;

                // 前驱指向后继
                prev.next = succeed;
                size--;
                return false;
            }

            return true;
        });
    }

    /**
     * 获取指定位置的值
     */
    public Object get(int id) {
        checkBounds(id);

        final Object[] vals = new Object[1];

        foreach(new NodeConsumer() {
            @Override
            public boolean onNode(Node node, int pos) {
                if (pos == id) {
                    vals[0] = node.val;
                    return false;
                } else {
                    return true;
                }

            }
        });

        return vals[0];
    }

    /**
     * 检查是否越界
     */
    private void checkBounds(int id) {
        if (id < 0 || id >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * 遍历每个节点
     */
    public void foreach(NodeConsumer nodeConsumer) {
        if (size == 0) return;

        Node node = head;

        int cursor = 0; // 遍历游标

        if (null != nodeConsumer) {
            if (!nodeConsumer.onNode(node, cursor)) {
                return;
            }
        }

        while (node.next != null) {
            cursor++;

            node = node.next; // 下一个节点

            if (null != nodeConsumer) {
                if (!nodeConsumer.onNode(node, cursor)) {
                    return;
                }
            }
        }
    }

    /**
     * 查询第一次出现的节点
     */
    public Node find(Object val){
        final Node[] foundNode = new Node[1];

        foreach((node, id) -> {
            if (node.val==val){ // 首次找到、不必再遍历后续节点
                foundNode[0] =node;
                return false;
            }

            return true;
        });

        return foundNode[0];
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SingleLinkedList[");

        foreach((node, id) -> {
            if (id == 0) {
                builder.append(node.val);
            } else {
                builder.append("-->" + node.val);
            }

            return true;
        });

        builder.append("]");
        return builder.toString();
    }
}
