package com.kk.datastructure.bitmap.roaring;

import org.junit.Test;


public class RoaringBitmapTest {
    RoaringBitmap roaringBitmap = new RoaringBitmap();

    @Test
    public void commonTest() {
        roaringBitmap.add(1);
        roaringBitmap.add(88);
        roaringBitmap.add(250);
        roaringBitmap.add(1000);

        System.out.println("contains 88-->" + roaringBitmap.contains(88));
        System.out.println("contains 250-->" + roaringBitmap.contains(250));
        System.out.println("contains 999-->" + roaringBitmap.contains(999));

        roaringBitmap.remove(88);
        System.out.println("\nafter remove 88-->" + roaringBitmap.contains(88));
    }
}
