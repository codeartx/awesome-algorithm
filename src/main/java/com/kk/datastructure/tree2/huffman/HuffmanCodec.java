package com.kk.datastructure.tree2.huffman;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 霍夫曼编解码器
 */
public class HuffmanCodec extends HuffmanTree {
    /**
     * 编码一个字符串，自动生成字典表，并根据字典表编码
     */
    public String encode(String plain) {
        List<String> keywordsList = tokenize(plain);

        Node[] nodes = new Node[keywordsList.size()];
        int i = 0;

        for (String keyword : keywordsList) {
            nodes[i] = new Node(keyword, StringUtils.countMatches(plain, keyword));
            i++;
        }

        put(nodes);

        Map<String, String> codesMap = codesMap();

        StringBuilder codesBuilder = new StringBuilder();

        for (char ch : plain.toCharArray()) {
            codesBuilder.append(codesMap.get(char2Str(ch)));
        }

        return codesBuilder.toString();
    }

    private String char2Str(char ch) {
        return new String(new char[]{ch});
    }

    /**
     * 分词并去重
     */
    private List<String> tokenize(String plain) {
        char[] keywords = plain.toCharArray();
        List<String> keywordsList = new ArrayList<>();

        for (char key : keywords) {
            String keyword = char2Str(key);

            if (keywordsList.indexOf(keyword) == -1) {
                keywordsList.add(keyword);
            }
        }

        return keywordsList;
    }

    /**
     * 编码  { "keyword":"001"}
     */
    Map<String, String> codesMap() {
        Map<String, String> codesMap = new HashMap<>();

        for (Node leaf : leafs) {

            String code = "";
            Node node = leaf;

            while (node.parent != null) {
                Node parent = node.parent;

                if (parent.left == node) { // 左节点 --> 0
                    code += "0";
                } else { // 右节点  --> 1
                    code += "1";
                }

                node = parent;
            }

            codesMap.put(leaf.key, reverseStr(code));
        }

        return codesMap;
    }

    /**
     * 翻转字符串
     */
    String reverseStr(String str) {

        int len = str.length();
        char[] reversed = new char[len];

        for (int i = 0; i < len; i++) {
            reversed[i] = str.charAt(len - 1 - i);
        }

        return new String(reversed);
    }
}
