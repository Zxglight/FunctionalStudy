package com.xg.fpjava;

import com.xg.fpjava.TailCall.Return;
import com.xg.fpjava.TailCall.Suspend;
import com.xg.util.Supplier;
import java.util.function.Function;

/**
 * @author xg.zhao   2018 04 15 23:49
 */
public abstract class TailCall<T> {

    abstract public TailCall<T> resume();

    abstract public T eval();

    abstract public boolean isSuspend();

    public static <T> Return<T> ret(T t){
        return new Return<T>(t);
    }
    public static <T> Suspend<T> sus(Supplier<TailCall<T>> s){
        return new Suspend<T>(s);
    }

    static Function<Integer,Function<Integer,Integer>> add = x -> y -> {
        class AddHelper{
            Function<Integer,Function<Integer,TailCall<Integer>>> addHelper = a -> b -> b == 0 ? ret(a):sus(() -> this.addHelper.apply(a+1).apply(b-1));
        }
        return new AddHelper().addHelper.apply(x).apply(y).eval();
    };


    public static class Return<T> extends TailCall<T> {

        private final T t;

        public Return(T t) {this.t = t;}

        @Override
        public TailCall<T> resume() {
            return null;
        }

        @Override
        public T eval() {
            return t;
        }

        @Override
        public boolean isSuspend() {
            return false;
        }
    }

    public static class Suspend<T> extends TailCall<T> {

        private final Supplier<TailCall<T>> resume;

        public Suspend(Supplier<TailCall<T>> resume) {this.resume = resume;}

        @Override
        public TailCall<T> resume() {
            return resume.get();
        }

        @Override
        public T eval() {
            TailCall<T> tSuspend = this;
            while (tSuspend.isSuspend()){
                tSuspend = tSuspend.resume();
            }
            return tSuspend.eval();
        }

        @Override
        public boolean isSuspend() {
            return true;
        }
    }

}

class Test {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        TailCall<Integer> add = add(3, 1000000000);
        while (add.isSuspend()) {
            add = add.resume();
        }
        System.out.println(TailCall.add.apply(3).apply(1000000000));
        long end = System.currentTimeMillis();
        System.out.println("run time" + (end - start));
    }

    static TailCall<Integer> add(int x, int y) {
        return y == 0 ? new Return<>(x) : new Suspend<>(() -> add(x + 1, y - 1));
    }
}
