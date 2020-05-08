package com.iskech.thread.basic;

/**
 * @author ：liujx
 * @date ：Created in 2020/5/8 16:16
 * @description：消息通知打印demo1
 * @modified By：
 * @version: V1.0
 */
public class NotifyPrintDemo {
    private long beginTime;
    private long excuseTime = 0;
    private final Object lock1 = new Object();

    public NotifyPrintDemo() {
        this.beginTime = System.currentTimeMillis();
    }

    public void printTime() throws InterruptedException {
        while (true) {
            synchronized (lock1) {
                Thread.sleep(1000);
                ++this.excuseTime;
                System.out.println("每隔一秒计时一次当前执行时间：" + excuseTime);
                if (Math.floorMod(excuseTime, 15L) == 0) {
                    lock1.notifyAll();
                }
            }
        }
    }

    public void printNotify15() throws InterruptedException {
        while (excuseTime == 0 || Math.floorMod(excuseTime, 15L) != 0) {
            synchronized (lock1) {
                lock1.wait();
            }
        }
        System.out.println("每隔15打印消息");
        synchronized (lock1) {
            lock1.wait();
        }
    }

    public static void main(String[] args) {
        NotifyPrintDemo notifyPrintDemo = new NotifyPrintDemo();

        new Thread(new Runnable() {
            @Override public void run() {
                try {
                    notifyPrintDemo.printNotify15();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            notifyPrintDemo.printTime();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
