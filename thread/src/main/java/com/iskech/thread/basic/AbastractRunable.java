package com.iskech.thread.basic;

/**
 * @author ：liujx
 * @date ：Created in 2020/5/7 11:27
 * @description：抽象runable
 * @modified By：
 * @version: V1.0
 */
public abstract class AbastractRunable implements Runnable {
    static String name;

    @Override public void run() {

        System.out.println(AbastractRunable.name);
    }
}
