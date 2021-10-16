package com.kk.util;

import com.kk.datastructure.tree2.avl.AVLNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AVLPrinter {

    public static <T extends Comparable<?>> void printNode(AVLNode root) {
        int maxLevel = AVLPrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printNodeInternal(List<AVLNode> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || AVLPrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        AVLPrinter.printWhitespaces(firstSpaces);

        List<AVLNode> newNodes = new ArrayList<>();
        for (AVLNode node : nodes) {
            if (node != null) {
                System.out.print(node.val+"-"+node.depth);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            AVLPrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                AVLPrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    AVLPrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    AVLPrinter.printWhitespaces(1);

                AVLPrinter.printWhitespaces((i + i - 1));

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    AVLPrinter.printWhitespaces(1);

                AVLPrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(AVLNode node) {
        if (node == null)
            return 0;

        return Math.max(AVLPrinter.maxLevel(node.left), AVLPrinter.maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}