package com.kk.datastructure.bitmap.roaring.container;

/**
 * 容器基类
 */
public abstract class Container  {
    int cardinality;

    public boolean isEmpty() {
        return cardinality == 0;
    }

    public abstract Container add(char x);

    public abstract Container remove(char x);

    public abstract boolean contains(char x);

    public int getCardinality() {
        return cardinality;
    }
}
