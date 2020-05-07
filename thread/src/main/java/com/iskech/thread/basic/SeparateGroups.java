package com.iskech.thread.basic;

import java.util.concurrent.CountDownLatch;

/**
 * @author ：liujx
 * @date ：Created in 2020/5/7 15:25
 * @description：多组代码块分组使用锁
 * @modified By：
 * @version: V1.0
 */
public class SeparateGroups {
    private int aVal = 1;
    private int bVal = 2;

    private final Object lockA = new Object();
    private final Object lockB = new Object();

    public void setVal() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lockA) {
            this.aVal += 1;
        }

    }

    public synchronized void setaVal(double aVal) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lockA) {
            this.aVal += aVal;
        }

    }

    public int getbVal() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bVal;
    }

    public void setbVal(int bVal) {
        this.bVal = bVal + 2;
    }

    public static void main(String[] args) {
        int countDown = 5000;
        for (int count = 1; count <= 10; count++) {
            CountDownLatch countDownLatch = new CountDownLatch(countDown);
            SeparateGroups separateGroups = new SeparateGroups();
            for (int index = 1; index <= countDown; index++) {
                new Thread(new Runnable() {
                    @Override public void run() {
                        separateGroups.setaVal(0.1);
                        separateGroups.setVal();
                        // separateGroups.setbVal(separateGroups.getbVal());
                        countDownLatch.countDown();
                    }
                }).start();
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("a value:" + separateGroups.aVal + "当前线程活跃数：" + Thread.activeCount());
            //   System.out.println("b value:" + separateGroups.getbVal());

        }

    }
}
