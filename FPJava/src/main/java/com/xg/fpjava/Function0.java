package com.xg.fpjava;

import com.xg.util.Tuple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author xg.zhao   2018 03 29 7:12 function example from 《Functional Programming in Java》
 */
public class Function0 {

    static Function<Double, Integer> f = i -> (int) (i * 2);
    static Function<Integer, Long> l = i -> i * 10L;
    static Function<Long, Double> d = l -> l + 2.0;

    //------------------------------------------------------------------------------------------
    static Function<Integer, Integer> compose(final Function<Integer, Integer> f1, Function<Integer, Integer> f2) {
        return new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return f1.apply(f2.apply(integer));
            }
        };
    }

    //-------------------------------------------------------------------------------------------

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

    static Function<Integer, Integer> square() {
        return x -> x * x;
    }

    static Function<Integer, Integer> triple() {
        return x -> x * 3;
    }

    static Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>> compose() {
        return x -> y -> z -> x.apply(y.apply(z));
    }

    static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherCompose() {
        return x -> y -> z -> x.apply(y.apply(z));
    }

    static <T, U, V> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> higherAndThen() {
        return x -> y -> z -> y.apply(x.apply(z));
    }

    /**
     * study currying function
     */
    static <A, B, C> Function<B, C> partialA(A a, Function<A, Function<B, C>> f) {
        return f.apply(a);
    }

    static <A, B, C> Function<A, C> partialB(B b, Function<A, Function<B, C>> f) {
        return a -> f.apply(a).apply(b);
    }

    static <A, B, C> Function<A, Function<B, C>> curry(Function<Tuple<A, B>, C> f) {
        return a -> b -> f.apply(new Tuple<A, B>(a, b));
    }

    static <T, U, V> Function<U, Function<T, V>> reverseArgs(Function<T, Function<U, V>> f) {
        return u -> t -> f.apply(t).apply(u);
    }

    static Function<Integer, Integer> factorial() {
        return i -> i == 0 ? 1 : factorial().apply(i - 1);
    }

    public static Integer fold(List<Integer> list, Integer identity, Function<Integer, Function<Integer, Integer>> fun) {
        int result = identity;
        for (Integer i : list) {
            result = fun.apply(result).apply(i);
        }
        return result;
    }

    public static <T> List<T> append(List<T> list, T t) {
        List<T> ts = copy(list);
        ts.add(t);
        return Collections.unmodifiableList(ts);
    }

    public static <T> List<T> tail(List<T> list) {
        if (list.size() == 0) {
            throw new IllegalStateException("tail of empty list");
        }
        List<T> workList = copy(list);
        workList.remove(0);
        return Collections.unmodifiableList(workList);
    }

    public static <T> T head(List<T> list) {
        if (list.size() == 0) {
            throw new IllegalStateException("head of empty list");
        }
        return list.get(0);
    }

    private static <T> List<T> copy(List<T> ts) {
        return new ArrayList<>(ts);
    }

    public static <T, U> U foldRight(List<T> list, U identity, Function<T, Function<U, U>> fun) {
        return list.isEmpty() ? identity : fun.apply(head(list)).apply(foldRight(tail(list), identity, fun));
    }

    public static <T, U> U foldLeft(List<T> ts, U identity, Function<U, Function<T, U>> f) {
        U result = identity;
        for (T t : ts) {
            result = f.apply(result).apply(t);
        }
        return result;
    }

    public static <T> List<T> resverse(List<T> list) {
        return foldLeft(list, list(), x -> y -> foldLeft(x, list(y), a -> b -> append(a, b)));
    }

    public static <T> List<T> list() {
        return Collections.emptyList();
    }

    public static <T> List<T> list(T t) {
        return Collections.singletonList(t);
    }

    public static <T> List<T> list(List<T> ts) {
        return Collections.unmodifiableList(new ArrayList<>(ts));
    }

    @SafeVarargs
    public static <T> List<T> list(T... t) {
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(t, t.length)));
    }

    //-------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        Integer apply = getAddFun().apply(100).apply(30);
        Integer apply1 = getAddFun1().apply(100).apply(40);
        System.out.println(apply);
        System.out.println(apply1);
        Function<Integer, Integer> apply2 = compose().apply(triple()).apply(square());
        System.out.println(apply2.apply(33));
        Integer apply3 = Function0.<Integer, Integer, Integer>higherCompose().apply(triple()).apply(square()).apply(12);
        Integer apply4 = Function0.<Integer, Integer, Integer>higherAndThen().apply(triple()).apply(square()).apply(12);
        System.out.println(apply3);
        System.out.println(apply4);
        Integer apply5 = Function0.<Long, Double, Integer>higherAndThen().apply(d).apply(f).apply(1L);
        Integer apply6 = Function0.<Long, Double, Integer>higherCompose().apply(f).apply(d).apply(1L);
        System.out.println(apply5);
        System.out.println(apply6);
        Double apply7 = Function0.<String, Integer, Double>higherAndThen().apply(s -> s.length()).apply(integer -> integer + 2.0)
                .apply("hello world!!!");
        System.out.println(apply7);

    }

    void testMethod() {
        double a = 0.3;
        Function<Double, Double> f = e -> e + a;
        //        local variable modify cite will cause compile fails
        //        a = 2.3;
    }
}
