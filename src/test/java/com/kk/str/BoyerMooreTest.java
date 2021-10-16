package com.kk.str;

import com.kk.util.PrintHelper;
import org.junit.Test;

public class BoyerMooreTest {
    String target="HERE IS A SIMPLE EXAMPLE";
    String template = "EXAMPLE";
    String template2 = "EXAMPXA";

    @Test
    public void findTest(){
        System.out.println("-->" + BoyerMoore.find(target,template));;
    }

    @Test
    public void preBadCharTableTest() {
        int[] badCharsTable = BoyerMoore.preBadCharsTable(template);
        System.out.println("-->");
        PrintHelper.printArr(badCharsTable);
    }

    @Test
    public void suffixesTest() {
        int[] suffixes = BoyerMoore.suffixes(template2);

        System.out.println("-->");
        PrintHelper.printArr(suffixes);
    }

    @Test
    public void preGoodSuffixesTest() {
        int[] suffixes = BoyerMoore.suffixes(template2);
        int[] goodSuffixes = BoyerMoore.preGoodSuffixes(template2, suffixes);

        System.out.println("-->");
        PrintHelper.printArr(goodSuffixes);
    }
}
