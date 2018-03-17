package com.xg.unit0316;

/**
 * @author xg.zhao
 * @date 2018 03 16 6:25
 */
public class App {

    public static int add(int a, int b) {
        while (b > 0) {
            a++;
            b--;
        }
        return a;
    }

    public static int mult(int a, int b) {
        return a * b;
    }

    public static void main(String[] args) {
        int a = 10;
        int b = 10;
        int add = add(a, b);
        int mult = add(mult(a, b), mult(a, b));
        System.out.println(add);
        System.out.println(mult);
    }

}
