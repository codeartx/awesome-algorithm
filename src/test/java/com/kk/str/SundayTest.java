package com.kk.str;

import org.junit.Test;

public class SundayTest {
    String text="substring searching";
    String pattern="search";

    @Test
    public void findTest(){
        System.out.println("-->" + Sunday.find(text,pattern));
    }
}
