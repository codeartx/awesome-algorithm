package com.kk.sort.outer;

import com.kk.util.FileUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * K路外排序
 */
public class KOuterSort {
    int k = 3;

    //    int heapSize=100*1000;

    public void sort(String original) throws IOException {
        long countLines = FileUtil.countLines(original);
        // 置换-选择拆分成大小不一的顺串文件
        ReplaceSelectionSort repSelSorter = new ReplaceSelectionSort((int) (countLines / 5));

        List<String> seqfilesList = new ArrayList<>();
        repSelSorter.splitToSeqfile(original, seqfilesList);

        // 归并初始顺串
        merge(seqfilesList);
    }

    void merge(List<String> seqsList) throws IOException {
        int len = seqsList.size();

        if (len == 0) return; // 合并结束

        if (len == 1) { // 最后一个有序文件
            Files.copy(Paths.get(seqsList.get(0)), Paths.get("data/result-ordered"));
            return;
        }

        List<String> mergesList = new ArrayList<>();

        for (int i = 0; i < len; i += k) {// 每k个分一组
            // 打开每个要合并的分支文件
            List<BufferedReader> readerList = openReadersForBranch(seqsList, len, i);

            if (readerList.size() == 0) break;

            // 初始化败者树
            LoserTree loserTree = initLoserTreeFromReaders(readerList);

            // 不断的读取最小分支的下一个值更新败者树，并将最小值写入输出文件
            mergeByLoserTree(seqsList, mergesList, readerList, loserTree);
        }

        // 递归处理新合并的文件
        merge(mergesList);
    }

    /**
     * 通过最小树不断的合并每个分支文件
     */
    private void mergeByLoserTree(List<String> seqsList, List<String> mergesList, List<BufferedReader> readerList,
                                  LoserTree loserTree) throws IOException {
        String mergeFile = nextMergeFile(seqsList.get(0));
        mergesList.add(mergeFile);
        PrintWriter mergeWriter = new PrintWriter(mergeFile);

        while (loserTree.hashNext()) { // 还有下个元素，说明
            int minBranch = loserTree.minBranch();
            int min = loserTree.peek();

            // 写入输出
            mergeWriter.println(min);

            int nextVal = Integer.MAX_VALUE;

            String line = readerList.get(minBranch).readLine();

            if (null != line) {
                nextVal = Integer.parseInt(line);
            }

            loserTree.pop(nextVal);
        }

        FileUtil.closeWriter(mergeWriter);
    }

    /**
     * 初始化败者树
     */
    private LoserTree initLoserTreeFromReaders(List<BufferedReader> readerList) throws IOException {
        int[] branches = new int[readerList.size()];

        for (int j = 0; j < readerList.size(); j++) {
            branches[j] = Integer.parseInt(readerList.get(j).readLine());
        }

        LoserTree loserTree = new LoserTree();
        loserTree.create(branches);

        return loserTree;
    }

    /**
     * 准备合并文件
     */
    String nextMergeFile(String seqOrMerge) {
        String seqFlag = "-seq-";
        String mergeFlag = "-merge-";

        String[] parts;
        Integer order = 0;

        if (seqOrMerge.contains(seqFlag)) { // 初始顺串 ori-seq-0 -> ori-merge-0
            parts = seqOrMerge.split(seqFlag);
            order = Integer.parseInt(parts[1]);
            return parts[0] + mergeFlag + order;
        } else { // 中间合并文件 ori-merge-0 -> ori-merge-0-0
            parts = seqOrMerge.split(mergeFlag);
            order = Integer.parseInt(parts[parts.length - 1]);
            return seqOrMerge + "-" + order;
        }
    }

    /**
     * 打开每个要合并的分支文件
     */
    private List<BufferedReader> openReadersForBranch(List<String> seqsList, int len, int i) {
        List<BufferedReader> readerList = new ArrayList<>();

        for (int j = 0; j < i + k; j++) {
            if (j > len - 1) continue;

            try {
                readerList.add(FileUtil.bufferedReader(seqsList.get(j)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return readerList;
    }
}
