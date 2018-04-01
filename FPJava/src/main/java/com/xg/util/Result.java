package com.xg.util;

/**
 * @author xg.zhao   2018 04 01 21:28
 */
public interface Result<T> {

    void bind(Effect<T> success, Effect<T> failure);

    static  <T> Result<T> success(T t){
        return new Success(t);
    }

    static  <T> Result<T> failure(T t){
        return new Failure(t);
    }

    class Success<T> implements Result<T>{
        T t;

        public Success(T t) {
            this.t = t;
        }

        @Override
        public void bind(Effect<T> success, Effect<T> failure) {
            success.apply(t);
        }
    }
    class Failure<T> implements Result<T>{
        private final T t;

        public Failure(T t) {
            this.t = t;
        }

        @Override
        public void bind(Effect<T> success, Effect<T> failure) {
            failure.apply(t);
        }
    }

}
