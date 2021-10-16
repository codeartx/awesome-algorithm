package com.kk.sort.outer;

import com.kk.util.FileUtils;
import org.junit.Test;

import java.io.IOException;

public class ReplaceSelectionSortTest {
    String original = "data/original.dat";
    ReplaceSelectionSort sorter = new ReplaceSelectionSort();

    @Test
    public void splitToSeqfileTest() throws IOException {
        FileUtils.cleanData();

        sorter.splitToSeqfile(original, null);
    }
}
