package com.iskech.thread.chapter1;

/**
 * @author ：liujx
 * @date ：Created in 2020/4/29 14:12
 * @description：锁的可重入demo
 * @modified By：
 * @version: V1.0
 */
public class LoggingWidget extends Widget {
    @Override public synchronized void test() {
        System.out.println(toString()+"join LoggingWidget ......");
        super.test();

        System.out.println("join LoggingWidget end......");
    }

    public static void main(String[] args) {
        /**
         *当线程进入子类test开始获取logginWidget的锁super.test()调用基类test方法但该方法synchronized锁住了需要longgingWidget
         * 锁但在当前其锁又被持有，如果没有重入锁则会造成死锁。重入锁：当一个线程尝试获取其已经持有的锁则请求给予通过。锁的粒度为线程
         *
         */
        new LoggingWidget().test();
    }
}
