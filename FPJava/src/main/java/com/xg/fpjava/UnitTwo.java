package com.xg.fpjava;

import java.util.function.Function;

/**
 * @author xg.zhao   2018 03 29 7:12
 */
public class UnitTwo {

    //------------------------------------------------------------------------------------------
    static Function<Integer, Integer> compose(final Function<Integer, Integer> f1, Function<Integer, Integer> f2) {
        return new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return f1.apply(f2.apply(integer));
            }
        };
    }

    static Function<Integer, Integer> compose1(final Function<Integer, Integer> f1, Function<Integer, Integer> f2) {
        return integer -> f1.apply(f2.apply(integer));
    }

    //-------------------------------------------------------------------------------------------
    static Function<Integer, Function<Integer, Integer>> getAddFun() {
        return new Function<Integer, Function<Integer, Integer>>() {
            @Override
            public Function<Integer, Integer> apply(Integer pa1) {
                return new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer pa2) {
                        return pa1 + pa2;
                    }
                };
            }
        };
    }

    static Function<Integer, Function<Integer, Integer>> getAddFun1() {
        return pa1 -> pa2 -> pa1 + pa2;
    }

    //-------------------------------------------------------------------------------------------

    static Function<Integer, Integer> square() {
        return x -> x * x;
    }

    static Function<Integer, Integer> triple() {
        return x -> x * 3;
    }

    static Function<Function<Integer,Integer>,Function<Function<Integer,Integer>,Function<Integer,Integer>>> compose(){
        return x -> y -> z -> x.apply(y.apply(z));
    }

    //-------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        Integer apply = getAddFun().apply(100).apply(30);
        Integer apply1 = getAddFun1().apply(100).apply(40);
        System.out.println(apply);
        System.out.println(apply1);
        Function<Integer, Integer> apply2 = compose().apply(triple()).apply(square());
        System.out.println(apply2.apply(33));
    }
}
