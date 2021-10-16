package com.kk.datastructure.tree2.redblack;

/**
 * 红黑树
 */
public class RedBlackTree {
    public RedBlackNode root;
    public int size;

    /**
     * 存入
     */
    public void put(String key, Object val) {
        if (null == key) return;

        if (size == 0) {
            root = new RedBlackNode(key, val, RedBlackNode.COLOR_BLACK);
            size++;
        }

        // 遍历的游标
        RedBlackNode insertPoint = findNearNode(key); // 对应节点或父节点

        RedBlackNode inserteNode = null;

        int cmp = insertPoint.key.compareTo(key);

        if (cmp == 0) { // 对应节点，更新val
            insertPoint.val = val;
        } else if (cmp > 0) {
            inserteNode = insertToLeft(key, val, insertPoint);
        } else if (cmp < 0) {
            inserteNode = insertToRight(key, val, insertPoint);
        }

        if (null != inserteNode) { // 说明新插入了节点，需要平衡
            root = balancePut(inserteNode);
        }
    }

    /**
     * 获取某val
     */
    public Object get(String key) {
        if (size == 0) {
            return null;
        }

        // 找到临近点
        RedBlackNode node = findNode(key);

        return node == null ? null : node.val;
    }

    /**
     * 查找某key节点,找到的话，返回该节点，否则返回最后遍历到的父节点（用于判断插入）
     */
    public RedBlackNode findNearNode(String key) {
        if (size == 0) {
            return null;
        }

        // 遍历的游标
        RedBlackNode cursor = root;
        int cmp;

        while (true) {
            cmp = cursor.key.compareTo(key);

            if (cmp > 0) { // 偏小，往左遍历
                if (null == cursor.left) { // 无左值，遍历到最左端,新建节点插入到左端
                    return cursor;
                } else {
                    cursor = cursor.left;
                    continue;
                }
            } else if (cmp < 0) { // 偏大，往右比较
                if (null == cursor.right) {
                    // 无右值，遍历到最右端
                    return cursor;
                } else {
                    cursor = cursor.right;
                    continue;
                }
            } else if (cmp == 0) { // key节点，更新值，无需平衡
                return cursor;
            }
        }
    }

    public RedBlackNode findNode(String key) {
        if (size == 0) {
            return null;
        }

        RedBlackNode foundNode = findNearNode(key);

        if (foundNode.key.compareTo(key) == 0) {
            return foundNode;
        } else {
            return null;
        }
    }

    /**
     * 是否是对应节点
     */
    private boolean isNodeOfKey(RedBlackNode node, String key) {
        return node.key.compareTo(key) == 0;
    }

    /**
     * 是否是左节点
     */
    private boolean isLeftNode(RedBlackNode parent, RedBlackNode child) {
        return parent.key.compareTo(child.key) > 0;
    }

    /**
     * 删除某个key
     */
    public void delete(String key) {
        if (size == 0) return;

        RedBlackNode foundNode = findNode(key);

        if (null == foundNode) return;

        // 判断孩子数
        RedBlackNode parent = foundNode.parent;
        RedBlackNode left = foundNode.left;
        RedBlackNode right = foundNode.right;

        DeletePoint deletePoint;

        if (null != left && null != right) { // 双子 -> 删除后继节点
            RedBlackNode succeedNode = succeedNode(foundNode);
            foundNode.key = succeedNode.key;
            foundNode.val = succeedNode.val;

            deletePoint = deleteEndChild(succeedNode.parent, succeedNode);
        } else { // 非双子,删除，将孩子（null）提升
            deletePoint = deleteEndChild(parent, foundNode);
        }

        size--;

        if (size == 1) {
            root.color = RedBlackNode.COLOR_BLACK;
        }

        if (size > 1) {
            balanceDelete(deletePoint);
        }
    }

    /**
     * 删除一个末尾子节点
     */
    public DeletePoint deleteEndChild(RedBlackNode parent, RedBlackNode child) {
        if (null == parent) {
            return null;
        }

        if (isLeftNode(parent, child)) {
            parent.left = firstNonullChild(child);

            if (child.right != null) {
                child.right.parent = parent;
            }

            child.parent = null;
            child.right = null;

            return new DeletePoint(parent, child, child.right, true);
        } else {
            parent.right = firstNonullChild(child);

            if (child.left != null) {
                child.left.parent = parent;
            }

            child.parent = null;
            child.left = null;

            return new DeletePoint(parent, child, child.left, false);
        }
    }

    /**
     * 获取节点的第一个非空节点
     */
    private RedBlackNode firstNonullChild(RedBlackNode node) {
        if (null != node.left) {
            return node.left;
        }

        if (null != node.right) {
            return node.right;
        }

        return null;
    }

    /**
     * 查找一个节点的后继节点
     */
    public RedBlackNode succeedNode(RedBlackNode node) {
        if (null == node) {
            return null;
        }

        return minNode(node.right);
    }

    /**
     * 查找一个子树的最小值节点
     */
    public RedBlackNode minNode(RedBlackNode root) {
        if (null == root) {
            return null;
        }

        // 不断向左遍历，直到没有左孩子
        while (null != root.left) {
            root = root.left;
        }

        return root;
    }

    /**
     * 插入到某节点的左端
     */
    private RedBlackNode insertToLeft(String key, Object val, RedBlackNode node) {
        RedBlackNode inserteNode = new RedBlackNode(key, val);
        node.left = inserteNode;
        inserteNode.parent = node;
        size++;
        return inserteNode;
    }

    /**
     * 插入到某节点的左端
     */
    private RedBlackNode insertToRight(String key, Object val, RedBlackNode node) {
        RedBlackNode inserteNode = new RedBlackNode(key, val);
        node.right = inserteNode;
        inserteNode.parent = node;
        size++;
        return inserteNode;
    }

    /**
     * 平衡插入
     */
    private RedBlackNode balancePut(RedBlackNode insertedPoint) {
        if (insertedPoint == root) {
            root.color = RedBlackNode.COLOR_BLACK;
            return insertedPoint;
        }

        RedBlackNode parent = insertedPoint.parent;
        RedBlackNode grandpa = parent.parent;

        if (parent.isBlack() || grandpa == null) {  // 父黑，不需要平衡
            return root;
        }

        boolean isLeftSide = parent.key.compareTo(insertedPoint.key) > 0;

        boolean isParentLeftSide = grandpa.key.compareTo(parent.key) > 0; // 父节点是否在祖父的左侧
        RedBlackNode uncle = isParentLeftSide ? grandpa.right : grandpa.left;

        if (null != uncle && uncle.isRed()) { // 父红，叔红 --> 颜色翻转
            flipColors(grandpa);
            return balancePut(grandpa); // 向上递归处理
        } else { // 父红，叔黑 -> 根据失衡类型，选择对应的旋转策略
            RedBlackNode greatPa = grandpa != null ? grandpa.parent : null; //曾祖父

            if (isParentLeftSide && isLeftSide) { // 左-左 -> 右旋
                linkToParent(greatPa, rightRotate(grandpa));
            }

            if (isParentLeftSide && !isLeftSide) { // 左-右 -> 左旋 -> 左-左-> 右旋
                linkToParent(grandpa, leftRotate(parent));
                linkToParent(greatPa, rightRotate(grandpa));
            }

            if (!isParentLeftSide && !isLeftSide) { // 右-右 -> 左旋
                linkToParent(greatPa, leftRotate(grandpa));
            }

            if (!isParentLeftSide && isLeftSide) { // 右-左 -> 右旋 -> 右-右-> 左旋
                linkToParent(grandpa, rightRotate(parent));
                linkToParent(greatPa, leftRotate(grandpa));
            }
        }

        return root;
    }

    /**
     * 删除后平衡
     */
    private void balanceDelete(DeletePoint deletePoint) {
        RedBlackNode parent = deletePoint.parent;
        RedBlackNode del = deletePoint.del;
        RedBlackNode son = deletePoint.son;

        if (del != null && del.isRed()) { // 删除红节点，不需要平衡
            return;
        }

        if (son != null && son.isRed()) { // 父黑，子红 -> 子变黑就好
            son.toBlack();
            return;
        }

        RedBlackNode brother = deletePoint.isLeft ? parent.right : parent.left;

        if (parent.isRed() && isBrotherFamilyBlack(brother)) { // 父红、兄弟家族为黑->父、兄交换颜色
            swapColor(parent, brother);
            return;
        }

        if (deletePoint.isLeft) {
            if (brother.isRed()) { // 兄红 -> 以父左旋
                linkToParent(parent.parent, leftRotate(parent));
                balanceDelete(new DeletePoint(parent, del, null, true));
                return;
            }

            RedBlackNode broRight = brother.right;

            if (broRight != null && broRight.isRed()) { // 兄右红
                broRight.toBlack(); // 变黑，补充黑缺失
                linkToParent(parent.parent, leftRotate(parent));
                return;
            }

            RedBlackNode broLeft = brother.left;

            if (broLeft != null && broLeft.isRed()) { // 兄左红
                linkToParent(parent, rightRotate(brother)); // -> 以兄右旋

                brother.toBlack(); // 黑色
                linkToParent(parent.parent, leftRotate(parent)); // 父左旋
                return;
            }

            handleFullBlack(parent, brother);
        } else {  // 右侧
            if (brother.isRed()) { // 兄红 -> 以父右旋
                linkToParent(parent.parent, rightRotate(parent));
                balanceDelete(new DeletePoint(parent, del, null, false));
                return;
            }

            RedBlackNode broLeft = brother.left;

            if (broLeft != null && broLeft.isRed()) { // 兄左红
                broLeft.toBlack(); // 变黑，补充黑缺失
                linkToParent(parent.parent, rightRotate(parent));
                return;
            }

            RedBlackNode broRight = brother.right;

            if (broRight != null && broRight.isRed()) { // 兄右红
                linkToParent(parent, leftRotate(brother)); // -> 以兄左旋
                brother.toBlack(); // 黑色
                linkToParent(parent.parent, rightRotate(parent)); // 父右旋
                return;
            }

            // 兄节点全黑的时候，向上迭代
            handleFullBlack(parent, brother);
        }
    }

    /**
     * 兄节点全黑的时候，向上迭代
     */
    private void handleFullBlack(RedBlackNode parent, RedBlackNode brother) {
        if (isBrotherFamilyBlack(brother)) { // 全黑
            brother.toRed();
            balanceDelete(new DeletePoint(parent.parent, parent, null, isLeftNode(parent.parent, parent)));
        }
    }

    /**
     * 判断兄弟一家是否全黑
     */
    public boolean isBrotherFamilyBlack(RedBlackNode brother) {
        if (null == brother) {
            return false;
        }

        RedBlackNode left = brother.left;
        RedBlackNode right = brother.right;

        if ((null != left && left.isRed()) || (null != right && right.isRed())) { // 左子或右子为红
            return false;
        }

        return true;
    }

    /**
     * 连接到父节点，自动根据大小判断左右
     */
    public void linkToParent(RedBlackNode parent, RedBlackNode child) {
        if (null == parent) {
            root = child;
            root.parent = null;
            return;
        }

        boolean isLarger = parent.key.compareTo(child.key) > 0;

        if (isLarger) {
            parent.left = child;
        } else {
            parent.right = child;
        }

        child.parent = parent;
//        root = parent;
    }

    /**
     * 左旋、变色
     */
    public RedBlackNode leftRotate(RedBlackNode parent) {
        RedBlackNode right = parent.right;
        RedBlackNode rightLeft = right.left;

        // 父子交换
        right.left = parent;
        parent.parent = right;

        // 接收左子
        parent.right = rightLeft;
        if (rightLeft != null) {
            rightLeft.parent = parent;
        }

        // 交换颜色
        swapColor(parent, right);

        return right;// 返回新的父节点
    }

    /**
     * `
     * 右旋、变色
     */
    public RedBlackNode rightRotate(RedBlackNode parent) {
        RedBlackNode left = parent.left;
        RedBlackNode leftRight = left.right;

        // 父子交换
        left.right = parent;
        parent.parent = left;

        // 接收左子
        parent.left = leftRight;
        if (leftRight != null) {
            leftRight.parent = parent;
        }

        // 交换颜色
        swapColor(parent, left);

        return left;// 返回新的父节点
    }

    /**
     * 颜色翻转
     */
    public void flipColors(RedBlackNode parent) {
        int parentColor = parent.color;
        int childColor = parent.left.color;

        parent.left.color = parentColor;
        parent.right.color = parentColor;

        parent.color = childColor;
    }

    private void swapColor(RedBlackNode parent, RedBlackNode child) {
        int tmp = parent.color;
        parent.color = child.color;
        child.color = tmp;
    }

}

