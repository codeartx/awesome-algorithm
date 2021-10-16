package com.kk.datastructure.bitmap.bloom;

import com.kk.datastructure.bitmap.simple.BitSet;
import org.apache.commons.codec.digest.MurmurHash3;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class BloomFilter {
    // 数据规模，代表位图数组的大小
    int size;

    // 填充因子
    int inflate = 20;

    // 存储位图
    BitSet bitSet;

    // hash offsets,配合hash64实现hash函数列表，避免多次计算
    Set<Integer> hashOffsets;
    int hashNums = 4;

    /**
     * @param n 数据规模
     */
    public BloomFilter(int n) {
        this.size = n * 20;

        bitSet = new BitSet(size);

        initHashs();
    }

    /**
     * 初始化hash列表
     */
    private void initHashs() {
        hashOffsets = new HashSet<>();

        Random random = new Random();

        while (true) {
            if (hashOffsets.size() >= hashNums) return;

            int offset = random.nextInt(20) + 12;

            if (!hashOffsets.contains(offset)) {
                hashOffsets.add(offset);
            }
        }
    }

    private void hashs(String key, Consumer<Integer> consumer) {
        int hash = MurmurHash3.hash32(key.getBytes());

        hashOffsets.forEach(range -> {
            int rangedHash = hash >>> (32 - range);

            if (null != consumer) {
                consumer.accept(rangedHash);
            }
        });
    }

    public void add(String key) {
        hashs(key, pos -> {
            bitSet.set(pos % size, true);
        });
    }

    public boolean notExists(String key) {
        AtomicBoolean existed = new AtomicBoolean(true);

        hashs(key, pos -> {
            existed.set(bitSet.get(pos % size) && existed.get());
        });

        return !existed.get();
    }
}
