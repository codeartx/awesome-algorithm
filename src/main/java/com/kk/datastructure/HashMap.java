package com.kk.datastructure;

import org.apache.commons.codec.digest.MurmurHash3;

import java.util.LinkedList;

/**
 * 哈希表条目
 */
class Entry {
    String key;
    Object val;

    public Entry(String key, Object val) {
        this.key = key;
        this.val = val;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key='" + key + '\'' +
                ", val=" + val +
                '}';
    }
}

/**
 * 哈希表
 */
public class HashMap {
    int capacity = 16;
    int size = 0;
    LinkedList<Entry>[] data = new LinkedList[capacity];

    /**
     * 存入
     */
    public void put(String key, Object val) {
        // 查找对应的链表
        LinkedList<Entry> entriesList = findListForKey(key);

        int pos = posForKey(entriesList, key);

        if (pos == -1) { // 不存在，插入
            entriesList.add(new Entry(key, val));
            size++;
        } else { // 存在，更新val
            entriesList.get(pos).val = val;
        }
    }

    private LinkedList<Entry> findListForKey(String key) {
        int index = indexForKey(key);
        return listForIndex(index);
    }

    /**
     * 查询key
     */
    public Object get(String key) {
        // 查找对应的链表
        LinkedList<Entry> entriesList = findListForKey(key);

        int pos = posForKey(entriesList, key);

        if (pos == -1) {
            return null;
        } else {
            return entriesList.get(pos).val;
        }
    }

    /**
     * 移除key,返回被移除掉的entry
     */
    public Entry remove(String key) {
        LinkedList<Entry> entriesList = findListForKey(key);

        int pos = posForKey(entriesList, key);

        if (pos == -1) {
            return null;
        } else {
            Entry toRmEntry = entriesList.get(pos);
            entriesList.remove(pos);
            size--;
            return toRmEntry;
        }
    }

    /**
     * 获取某位的链表，如果没有则新建
     */
    LinkedList<Entry> listForIndex(int index) {
        LinkedList<Entry> linkedList = data[index];

        if (null == linkedList) {
            linkedList = new LinkedList<>();
            data[index] = linkedList;
        }

        return linkedList;
    }

    /**
     * 查询链表中是否存在指定key的节点
     */
    int posForKey(LinkedList<Entry> entryLinkedList, String key) {
        int pos = -1;

        for (int i = 0; i < entryLinkedList.size(); i++) {
            if (entryLinkedList.get(i).key.equals(key)) {
                pos = i;
            }
        }

        return pos;
    }

    /**
     * 计算指定key的索引
     */
    int indexForKey(String key) {
        return Math.abs(MurmurHash3.hash32(key)) % 16;
    }
}
