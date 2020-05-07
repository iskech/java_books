package com.iskech.thread.basic;

public class PrintServer {

    public PrintServer() {

        new Thread(new CustomRunable()).start();
    }

    /**
     * @author ：liujx
     * @date ：Created in 2020/5/7 11:11
     * @description：内部类定义runable实现，将run方法作用域隐藏
     * @modified By：
     * @version: V1.0
     */
    private class CustomRunable implements Runnable {
        @Override public void run() {
            System.out.println(Thread.currentThread().toString());
        }
    }

    public static void main(String[] args) {
        PrintServer printServer = new PrintServer();

    }

}

