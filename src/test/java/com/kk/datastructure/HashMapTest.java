package com.kk.datastructure;

import org.junit.Test;

public class HashMapTest {
    @Test
    public void test() {
        HashMap hashMap = new HashMap();

        hashMap.put("lisi", "lisi");
        hashMap.put("wangwu", "wangwu");
        hashMap.put("zhaoliu", "zhaoliu");
        hashMap.put("zhangsan", "zhangsan");

        System.out.println("get(wangwu)-->" + hashMap.get("wangwu"));

        System.out.println("remove(wangwu)-->" + hashMap.remove("wangwu"));
        System.out.println("get(wangwu)-->" + hashMap.get("wangwu"));
    }
}
