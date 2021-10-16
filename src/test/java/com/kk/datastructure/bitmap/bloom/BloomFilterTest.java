package com.kk.datastructure.bitmap.bloom;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BloomFilterTest {
    @Test
    public void test() {
        BloomFilter bloomFilter = new BloomFilter(2000);

        // 产生1000个不同key
        Set<String> toPutSet = new HashSet<>();

        int sample = 2000;

        for (int i = 0; i < sample; i++) {
            toPutSet.add(UUID.randomUUID().toString());
        }

        // 存入布隆过滤器中
        toPutSet.forEach(key -> {
            bloomFilter.add(key);
        });

        // 测试一定不存在
        int total = 0;
        int ok = 0;

        for (int i = 0; i < sample; i++) {
            String key = UUID.randomUUID().toString();

            if (!toPutSet.contains(key)) {
                total++;

                if (bloomFilter.notExists(key)) {
                    ok++;
                }
            }
        }

        System.out.println(String.format("【不存在】成功率:total=%s,ok=%s,rate=%s", total, ok, 1.0 * ok / total * 100));
    }
}
