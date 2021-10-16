package com.kk.sort.outer;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import static com.kk.util.FileUtil.*;


/**
 * 三元最小堆实现置换选择排序
 */
public class ReplaceSelectionSort {
    //    int heapSize = 100 * 1000;
    int heapSize = 3;

    // java优先级队列内部默认最小堆
    PriorityQueue<Integer> minHeap;

    public ReplaceSelectionSort(int heapSize) {
        this.heapSize = heapSize;
        minHeap = new PriorityQueue(heapSize);
    }

    public ReplaceSelectionSort() {
        minHeap = new PriorityQueue(heapSize);
    }

    /**
     * 分割大文件成初始顺串
     */
    public void splitToSeqfile(String original, List<String> seqfiles) {
        long count = countLines(original);

        if (count == 0) return;

        BufferedReader oriReader = null;

        String waitFile = null;
        int waitCount = 0;
        PrintWriter waitWriter = null;

        PrintWriter seqWriter = null;

        try {
            oriReader = bufferedReader(original);

            // 建立初始堆
            for (int i = 0; i < heapSize; i++) {
                String line = oriReader.readLine();

                if (null == line) break;

                minHeap.add(Integer.parseInt(line));
            }

            // 将最小值写入顺串文件
            String seqfile = nextSeqFile(original);

            if (null != seqfiles) {
                seqfiles.add(seqfile);
            }

            seqWriter = new PrintWriter(seqfile);

            Integer min = minHeap.poll();
            seqWriter.println(min);

            // 读取剩余文件，判断是入堆还是写入等待文件
            while (true) {
                String line = oriReader.readLine();

                if (null == line) break; // 文件末尾，退出

                int num = Integer.parseInt(line);

                if (num < min) { // 小于最小值，进入下一轮等待
                    if (null == waitFile) {
                        waitFile = nextWaitFile(original);
                        waitWriter = new PrintWriter(waitFile);
                    }

                    waitCount++;
                    waitWriter.println(num);
                } else { // 大于等于最小值，入堆
                    minHeap.add(num);
                    // 新最小值写入seq序列
                    min = minHeap.poll();
                    seqWriter.println(min);
                }
            }

            // 将堆剩余元素刷新到seq文件
            PrintWriter finalSeqWriter = seqWriter;

            while (true) {
                Integer num = minHeap.poll();

                if (null == num) break;

                finalSeqWriter.println(num);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeReader(oriReader);
            closeWriter(seqWriter);
            closeWriter(waitWriter);
        }

        if (waitCount != 0) { // 有等待下轮的
            splitToSeqfile(waitFile, seqfiles);
        }
    }

    /**
     * 准备等待文件
     */
    String nextWaitFile(String original) {
        return prepareSuffixedFile(original, "-wait-");
    }

    /**
     * 准备下一个序列文件
     */
    String nextSeqFile(String original) {
        return prepareSuffixedFile(original, "-seq-");
    }

    String prepareSuffixedFile(String original, String suffix) {
        String flag = "-wait-";

        if (!original.contains(flag)) {
            return original + suffix + 0;
        }

        String[] parts = original.split(flag);
        String name = parts[0];
        int seq = Integer.parseInt(parts[1]) + 1;

        return name + suffix + seq;
    }
}
