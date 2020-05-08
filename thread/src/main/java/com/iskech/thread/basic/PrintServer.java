package com.iskech.thread.basic;

import java.awt.*;

public class PrintServer implements Runnable {

    private long printServerThreadId;
    private final PrintQueue requests = new PrintQueue();

    public PrintServer() {
        Thread thread = new Thread(this);
        thread.start();
        printServerThreadId = thread.getId();
    }

    public void print(PrintJob job) {
        requests.add(job);
    }

    @Override public void run() {
        if (printServerThreadId != Thread.currentThread().getId()) {
            return; // only allow this thread id to execute run
        }

        while (true) {
            try {
                //printQueQue队列的移除方法判断队列是否为空若为空则线程等待避免了当打印队列为空时反复调用其remove方法
                realPrint(requests.remove());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void realPrint(PrintJob job) {
        System.out.println("Printing: " + job);
    }

    public static void main(String[] args) {
        PrintServer printServer = new PrintServer();
        printServer.requests.add(new PrintJob() {
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
        printServer.requests.add(new PrintJob() {
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

    }
}