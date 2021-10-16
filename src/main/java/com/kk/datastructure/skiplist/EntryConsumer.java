package com.kk.datastructure.skiplist;

/**
 * 条目消费者
 */
public interface EntryConsumer {
    boolean item(int level, int pos, SkipListEntry entry);
}
