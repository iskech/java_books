package com.iskech.thread.basic;

/**
 * @author ：liujx
 * @date ：Created in 2020/5/8 16:16
 * @description：消息通知打印demo1
 * @modified By：
 * @version: V1.0
 */
public class NotifyPrintDemo {
    private long beginTime = System.currentTimeMillis();
    private long excuseTime = 0;
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    /**
     * 线程1每秒打印一次信息，并通知线程
     * 线程2一直等待直到被唤醒并每隔15s执行打印消息
     * 线程3一直等待直到被唤醒并每隔7秒打印消息
     */

    public static void main(String[] args) {
        /**
         * 需要注意的是锁的问题，锁是对象的锁。并不是线程的,线程只不过是进入同步代码块后被加锁，至于加什么锁取决于同步代码块或者方法
         * wait,notify,notfiyAll最好显示调用
         */
        NotifyPrintDemo notifyPrintDemo = new NotifyPrintDemo();

        new Thread(new Runnable() {
            @Override public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ++notifyPrintDemo.excuseTime;
                    System.out.println(
                            Thread.currentThread().getName() + "每隔1秒执行目前已近执行：" + notifyPrintDemo.excuseTime + "s");
                    //通知线程
                    notifyPrintDemo.notifyThread();

                }
            }
        }, "thread1").start();

        new Thread(new Runnable() {
            @Override public void run() {
                while (true) {
                    notifyPrintDemo.waitLock1();
                    if (Math.floorMod(notifyPrintDemo.excuseTime, 15L) == 0) {
                        System.out.println(Thread.currentThread().getName() + "每隔15秒被通知执行");
                    }

                }
            }
        }, "thread2").start();

        new Thread(new Runnable() {
            @Override public void run() {
                while (true) {
                    notifyPrintDemo.waitLock2();
                    if (Math.floorMod(notifyPrintDemo.excuseTime, 7L) == 0) {
                        System.out.println(Thread.currentThread().getName() + "每隔7秒被通知执行");
                    }

                }
            }
        }, "thread3").start();

    }

    public void notifyThread() {
        synchronized (lock1) {
            lock1.notifyAll();
        }
        synchronized (lock2) {
            lock2.notifyAll();
        }
    }

    public void waitLock1() {
        synchronized (lock1) {
            try {
                lock1.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void waitLock2() {
        synchronized (lock2) {
            try {
                lock2.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
