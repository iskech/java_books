package com.iskech.thread.basic;

import java.awt.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author ：liujx
 * @date ：Created in 2020/5/8 9:38
 * @description：wait,notify,notifyall的使用
 * @modified By：
 * @version: V1.0
 */
class PrintQueue {
    private SingleLinkQueue<PrintJob> queue = new SingleLinkQueue<PrintJob>();

    public synchronized void add(PrintJob j) {
        queue.add(j);
        //唤醒所有正在线程等待的线程
        notifyAll(); // Tell waiters: print job added

    }

    public synchronized PrintJob remove()

            throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
            //线程等待，等待时会将同步锁释放，线程等待为原子操作，
        } // Wait for a print job

        System.out.println("进入移除逻辑");
        return queue.remove();
    }

    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    PrintJob remove = printQueue.remove();
                    System.out.println("移除的打印job:" + remove.toString());
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override public void run() {
                printQueue.add(new PrintJob() {
                    @Override public Graphics getGraphics() {
                        return null;
                    }

                    @Override public Dimension getPageDimension() {
                        return null;
                    }

                    @Override public int getPageResolution() {
                        return 0;
                    }

                    @Override public boolean lastPageFirst() {
                        return false;
                    }

                    @Override public void end() {

                    }
                });
                countDownLatch.countDown();
            }
        }).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("打印队列是否为空：" + printQueue.queue.isEmpty());
    }
}