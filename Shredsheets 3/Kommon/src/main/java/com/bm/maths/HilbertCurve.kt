package com.bm.maths

import com.badlogic.gdx.math.Vector2


class HilbertCurve ( ) {

//    //convert (x,y) to d
//    fun xy2d (n: Int, x: Int, y: Int) {
//        val rx = 0
//        val ry = 0
//        val s = 0
//        val d = 0
//
//        for (s=n/2; s>0; s/=2) {
//            rx = (x & s) > 0;
//            ry = (y & s) > 0;
//            d += s * s * ((3 * rx) ^ ry);
//            rotate(s, &x, &y, rx, ry);
//        }
//        return d;
//    }
//
//    fun nToXY(width: Int, height: Int, position: Int): Vector2 {
//        var rx: Int
//        var ry: Int
//        var s: Int
//        var t = position
//        var vector = Vec2Factory.get()
//
//    }


////convert d to (x,y)
//    void d2xy(int n, int d, int *x, int *y) {
//        int rx, ry, s, t=d;
//        *x = *y = 0;
//        for (s=1; s<n; s*=2) {
//            rx = 1 & (t/2);
//            ry = 1 & (t ^ rx);
//            rot(s, x, y, rx, ry);
//            *x += s * rx;
//            *y += s * ry;
//            t /= 4;
//        }
//    }
//
////rotate/flip a quadrant appropriately
//    void rot(int n, int *x, int *y, int rx, int ry) {
//        if (ry == 0) {
//            if (rx == 1) {
//                *x = n-1 - *x;
//                *y = n-1 - *y;
//            }
//
//            //Swap x and y
//            int t  = *x;
//            *x = *y;
//            *y = t;
//        }
//    }
//
//
//    fun rotate(n: Int, x: )

}