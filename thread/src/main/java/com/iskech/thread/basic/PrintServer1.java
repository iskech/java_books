package com.iskech.thread.basic;

/**
 * @author ：liujx
 * @date ：Created in 2020/5/7 11:56
 * @description：使用线程id作为线程标识防止run方法被其他线程主动调用
 * @modified By：
 * @version: V1.0
 */
public class PrintServer1 implements Runnable {
    private long id = -1;

    public PrintServer1() {
        Thread thread = new Thread(this);
        thread.start();
        this.id = thread.getId();
    }

    @Override public void run() {
        long id = Thread.currentThread().getId();
        if (id != this.id) {
            throw new RuntimeException("请勿直接调用run方法");
        }

        System.out.println("printing .... " + id);
    }

    public static void main(String[] args) {
        new PrintServer1();
        //new PrintServer1().run();
    }
}
