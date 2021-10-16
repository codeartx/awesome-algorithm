package com.kk.util;

import com.kk.datastructure.tree2.bst.BSTNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BSTPrinter {

    public static <T extends Comparable<?>> void printNode(BSTNode root) {
        int maxLevel = BSTPrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printNodeInternal(List<BSTNode> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BSTPrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BSTPrinter.printWhitespaces(firstSpaces);

        List<BSTNode> newNodes = new ArrayList<>();
        for (BSTNode node : nodes) {
            if (node != null) {
                System.out.print(node.val);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BSTPrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BSTPrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BSTPrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    BSTPrinter.printWhitespaces(1);

                BSTPrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    BSTPrinter.printWhitespaces(1);

                BSTPrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(BSTNode node) {
        if (node == null)
            return 0;

        return Math.max(BSTPrinter.maxLevel(node.left), BSTPrinter.maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}