package com.xg.fpjava;

import java.math.BigInteger;

/**
 * @author xg.zhao   2018 04 16 0:38
 */
public class Function2 {

    private static BigInteger fib_ (BigInteger acc1, BigInteger acc2, BigInteger x){
        if (x.equals(BigInteger.ZERO)){
            return BigInteger.ZERO;
        } else if (x.equals(BigInteger.ONE)){
            return acc1.add(acc2);
        }else {
            return fib_(acc2,acc1.add(acc2),x.subtract(BigInteger.ONE));
        }
    }
    public static BigInteger fib(int x){
        return fib_(BigInteger.ONE,BigInteger.ZERO,BigInteger.valueOf(x));
    }

    public static TailCall<BigInteger> fib2_(BigInteger acc1, BigInteger acc2, BigInteger x){
        if (x.equals(BigInteger.ZERO)){
            return TailCall.ret(BigInteger.ZERO);
        } else if (x.equals(BigInteger.ONE)){
            return TailCall.ret(acc1.add(acc2));
        }else {
            return TailCall.sus(() -> fib2_(acc2,acc1.add(acc2),x.subtract(BigInteger.ONE)));
        }
    }

    public static BigInteger fib2(int x){
        return fib2_(BigInteger.ONE,BigInteger.ZERO,BigInteger.valueOf(x)).eval();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(fib2(10000));
//        System.out.println(fib(10000));
        long end = System.currentTimeMillis();
        System.out.println("run time" + (end - start));
    }
}
