package com.kk.datastructure.bitmap.roaring;

import com.kk.datastructure.bitmap.roaring.container.ArrayContainer;
import com.kk.datastructure.bitmap.roaring.container.Container;
import com.kk.datastructure.bitmap.roaring.util.Util;

public class RoaringBitmap {
    ContainersArray containersArr = new ContainersArray();

    public void add(int val) {
        char index = Util.highBits(val);
        int indexId = containersArr.getId(index);

        if (indexId >= 0) { // 找到了高位索引，插入到旧的容器中
            insertToOldContainer(indexId, val);
        } else {
            indexId = -indexId - 1;  // 插入到新的容器
            insertToNewContainer(index, indexId, val);
        }
    }

    /**
     * 插入到新建的容器
     */
    private Container insertToNewContainer(char curContainerIndex, int curContainerId, int val) {
        ArrayContainer arrayContainer = new ArrayContainer();
        Container curContainer = arrayContainer.add(Util.lowBits(val));

        containersArr.insertAt(curContainerId, curContainerIndex, curContainer);
        return curContainer;
    }

    /**
     * 插入到旧的容器
     */
    private Container insertToOldContainer(int curContainerId, int val) {
        Container curContainer = containersArr.getContainerAtIndex(curContainerId);
        Container newContainer = curContainer.add(Util.lowBits(val));

        if (newContainer != curContainer) {
            containersArr.setContainerAtIndex(curContainerId, newContainer);
            curContainer = newContainer;
        }

        return curContainer;
    }

    public boolean contains(int x) {
        char index = Util.highBits(x);
        Container container = containersArr.getContainer(index);
        return container != null && container.contains(Util.lowBits(x));
    }


    public void remove(int x) {
        char index = Util.highBits(x);
        int indexId = containersArr.getId(index);

        if (indexId < 0) return;

        containersArr.setContainerAtIndex(indexId, containersArr.getContainerAtIndex(indexId).remove(Util.lowBits(x)));
        if (containersArr.getContainerAtIndex(indexId).isEmpty()) {
            containersArr.removeAtIndex(indexId);
        }
    }
}
