package com.kk.datastructure.skiplist;

import java.util.Random;

/**
 * 跳表
 */
public class SkipList {
    // 最高层的头尾节点
    public SkipListEntry head;
    public SkipListEntry tail;

    // 高度
    public int height;
    // 0层总节点数
    public int size;

    public Random random;    // Coin toss

    public SkipList() {
        // 创建一个 -oo 和一个 +oo 对象
        head = new SkipListEntry(SkipListEntry.INFINITE_NEGATIVE, null);
        tail = new SkipListEntry(SkipListEntry.INFINITE_POSITIVE, null);

        // 将 -oo 和 +oo 相互连接
        head.right = tail;
        tail.left = head;

        size = 0;
        height = 0;
        random = new Random();
    }

    /**
     * 查找指定key的节点，存在则返回0层该节点，不存在则返回0层前驱节点
     */
    private SkipListEntry findEntry(String key) {
        SkipListEntry entryCoursor; // 游标

        // 从head头节点开始查找
        entryCoursor = head;

        while (true) {
            // 该层从左向右查找，直到右节点的key值大于要查找的key值
            while (entryCoursor.right.key != SkipListEntry.INFINITE_POSITIVE
                    && entryCoursor.right.key.compareTo(key) <= 0) { // 小于该key的话一直往右查找
                entryCoursor = entryCoursor.right;
            }

            // 该层找到了前驱节点，如果有更低层的节点，则向低层移动
            if (entryCoursor.down != null) {
                entryCoursor = entryCoursor.down;
            } else {
                break; // 已经是最下层，退出查找循环
            }
        }

        // 最下层找到符合的节点或前驱节点，满足 entryCoursor<= key
        return entryCoursor;
    }

    /**
     * 获取指定的value
     */
    public Integer get(String key) {
        SkipListEntry foundEntry = findEntry(key);

        if (foundEntry.key.equals(key)) { // key相等，表示是对应节点
            return foundEntry.value;
        } else { // 不相等说明只查找到了前驱节点
            return null;
        }
    }

    /**
     * 添加kv
     */
    public Integer put(String key, Integer value) {
        // 找到最底层的对应节点或前驱节点
        SkipListEntry prev = findEntry(key);

        // 如果跳跃表中存在含有key值的节点，则进行value的修改操作即可完成
        if (prev.key.equals(key)) {
            Integer oldValue = prev.value;
            // 修改成新值
            prev.value = value;
            return oldValue;
        }

        // 如果跳跃表中不存在含有key值的节点，则进行新增操作，此时0层已插入结束
        SkipListEntry toPutEntry = insertByClockwise(key, value, prev);

        // 向上跳跃的高度
        int jumpHeight = 0;

        // 再使用随机数决定是否要向更高level攀升
        while (random.nextDouble() < 0.5) {
            // 如果新元素的级别已经达到跳跃表的最大高度，则新建空白层
            if (jumpHeight >= height) {
                addEmptyLevel();
            }

            // 从该层前驱节点向左查询和上层关联的前驱节点
            prev = jumpUp(prev);

            // 创建将插入该层的索引节点，不含value
            SkipListEntry indexEntry = insertByClockwise(key, null, prev);

            // 上下层关联（索引节点与下层索引或值节点关联）
            indexEntry.down = toPutEntry;
            toPutEntry.up = indexEntry;

            toPutEntry = indexEntry;

            jumpHeight = jumpHeight + 1;
        }

        size = size + 1;

        // 返回null，没有旧节点的value值
        return null;
    }

    /**
     * 从该层前驱节点向左查询和上层关联的前驱节点
     */
    private SkipListEntry jumpUp(SkipListEntry prev) {
        while (prev.up == null) {
            prev = prev.left;
        }

        prev = prev.up;
        return prev;
    }

    /**
     * 顺时针关联插入法
     */
    private SkipListEntry insertByClockwise(String key, Integer value, SkipListEntry prev) {
        SkipListEntry toPutEntry = new SkipListEntry(key, value);

        // 后继节点
        SkipListEntry suceed = prev.right;
        // 指向后继
        toPutEntry.right = suceed;
        // 后继指向该节点
        suceed.left = toPutEntry;
        // 指向前驱
        toPutEntry.left = prev;
        // 前驱指向该节点
        prev.right = toPutEntry;

        return toPutEntry;
    }

    /**
     * 新值空白层
     */
    private void addEmptyLevel() {
        SkipListEntry newHead, newTail;

        newHead = new SkipListEntry(SkipListEntry.INFINITE_NEGATIVE, null);
        newTail = new SkipListEntry(SkipListEntry.INFINITE_POSITIVE, null);

        newHead.right = newTail;
        newTail.left = newHead;

        // 上下层关联
        newHead.down = head;
        newTail.down = tail;
        head.up = newHead;
        tail.up = newTail;

        // 更新最高层头尾
        head = newHead;
        tail = newTail;

        height = height + 1;
    }

    /**
     * 剔除某个key
     */
    public Integer remove(String key) {
        SkipListEntry found;

        found = findEntry(key);

        if (!found.key.equals(key)) { // key不匹配，说明是前驱节点，没有改key的节点
            return null;
        }

        Integer oldValue = found.value;

        while (found != null) {
            // 对应的上传索引节点
            SkipListEntry upIndex = found.up;

            // 前驱和后继直接关联
            SkipListEntry prev = found.left;
            SkipListEntry succeed = found.right;
            prev.right = succeed;
            succeed.left = prev;

            found = upIndex;
        }

        return oldValue;
    }

    /**
     * 从上、往下遍历每个层的所有节点
     */
    public void foreach(EntryConsumer consumer) {
        if (size == 0) {
            return;
        }

        SkipListEntry levelHead = head;
        SkipListEntry cursor = levelHead;

        for (int level = height; level >= 0; level--) {  // 外层控制遍历的层级，从上往下层遍历
            int pos = 0;

            while (cursor.right != null) {
                if (!isEndpoint(cursor)) {
                    if (null != consumer && !consumer.item(level, pos, cursor)) {
                        return;
                    }

                    pos++;
                }

                cursor = cursor.right;
            }

            // 记录下层头节点
            cursor = levelHead = levelHead.down;
        }
    }

    /**
     * 判断一个节点是否是终端节点（头、尾）节点
     */
    private boolean isEndpoint(SkipListEntry entry) {
        return entry.key.equals(SkipListEntry.INFINITE_NEGATIVE) || entry.key.equals(SkipListEntry.INFINITE_POSITIVE);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        final int[] lastLevel = {height};

        foreach((EntryConsumer) (level, pos, entry) -> {
            if (level != lastLevel[0]) {
                lastLevel[0] = level;
                builder.append("\n");
            }

            builder.append("-->" + entry.key);

            return true;
        });

        return builder.toString();
    }
}