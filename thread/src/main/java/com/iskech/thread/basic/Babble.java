package com.iskech.thread.basic;

import java.util.concurrent.CountDownLatch;

/**
 * @author ：liujx
 * @date ：Created in 2020/5/9 14:50
 * @description：yield使用
 * @modified By：
 * @version: V1.0
 */
public class Babble implements Runnable {
    private boolean doYield;
    private int howOften;
    private String word;
    CountDownLatch countDownLatch;

    private Object lock = new Object();

    public Babble(boolean doYield, int howOften, String word, CountDownLatch countDownLatch) {
        this.doYield = doYield;
        this.howOften = howOften;
        this.word = word;
        this.countDownLatch = countDownLatch;
    }

    public Babble() {
    }

    @Override public void run() {
        for (int index = 1; index <= this.howOften; index++) {
            System.out.println(this.word);
            if (this.doYield) {
                //线程让步
                Thread.yield();
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.setCountDownLatch(getCountDownLatch());
    }

    public CountDownLatch getCountDownLatch() {
        synchronized (lock) {
            return countDownLatch;
        }
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        synchronized (lock) {
            this.countDownLatch.countDown();
        }

    }

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(new Babble(false, 2, "不让步1", countDownLatch), "线程1").start();
        new Thread(new Babble(false, 2, "不让步2", countDownLatch), "线程2").start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("分割线==============================");

        CountDownLatch countDownLatch2 = new CountDownLatch(2);
        new Thread(new Babble(true, 2, "让步1",countDownLatch2),"线程3").start();
        new Thread(new Babble(true, 2, "让步2",countDownLatch2),"线程4").start();

    }
}
