package com.kk.sort.outer;

public class LoserTree {
    int n;
    // 失败者完全二叉树数组
    int[] losers;

    // 分支数组
    int[] branches;

    public void create(int[] branches) {
        this.branches = branches;
        n = branches.length;
        losers = new int[n];

        // 假定最后一个元素作为初始的最小值
        int idForMin = idForMin();

        for (int i = 0; i < n; i++) {
            losers[i] = idForMin;
        }

        // 从尾部逐个向上调整
        for (int i = n - 1; i >= 0; i--) {
            adjustUp(i);
        }
    }

    /**
     * 找到最小值的id
     */
    int idForMin() {
        int min = branches[0];
        int id = 0;

        for (int i = 1; i < n; i++) {
            if (branches[i] < min) {
                min = branches[i];
                id = i;
            }
        }

        return id;
    }

    /**
     * 获取当前lose节点的父节点
     */
    int parent(int loserId) {
        return loserId / 2;
    }

    /**
     * 获取分支对应的loser
     */
    int loserForBranch(int branchId) {
        return (n + branchId) / 2;
    }

    /**
     * 从某个分支向上调整
     */
    void adjustUp(int minBranchId) {
        int parentLoser = loserForBranch(minBranchId);

        while (parentLoser > 0) { // 尚未到达根节点
            if (branches[minBranchId] > branches[losers[parentLoser]]) {  // 比父节点大 ——> 失败 --> 失败者记录到父节点，胜者向上比赛
                int tmp = losers[parentLoser];
                losers[parentLoser] = minBranchId;
                minBranchId = tmp;
            }

            parentLoser = parent(parentLoser);
        }

        // 将最终的胜者(最小值)
        losers[0] = minBranchId;
    }

    /**
     * 查看顶部最小值
     */
    public int peek() {
        return branches[losers[0]];
    }

    /**
     * 查看顶部最小值所在的分支id
     */
    public int minBranch() {
        return losers[0];
    }

    /**
     * 弹出最小值，并从最小分支读取下一个元素
     */
    public int pop(int updateVal) {
        int min = peek();
        int minBranch = losers[0];

        // 更新最小值
        branches[minBranch] = updateVal;
        // 调整
        adjustUp(minBranch);

        return min;
    }

    public int pop() {
        return pop(Integer.MAX_VALUE);
    }

    /**
     * 是否有下一个
     * 当最小值不是 Integer.MAX_VALUE 的时候，说明还有
     */
    public boolean hashNext() {
        return peek() != Integer.MAX_VALUE;
    }

    /**
     * 下一个
     */
    public int next() {
        return pop();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LoserTree[");

        for (int i = 0; i < losers.length; i++) {
            builder.append((i == 0 ? "" : ",") + losers[i]);
        }

        builder.append("]");
        return builder.toString();
    }
}
