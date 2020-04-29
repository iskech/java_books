package com.iskech.thread.chapter1;

/**
 * @author ：liujx
 * @date ：Created in 2020/4/29 14:11
 * @description：锁的可重入demo
 * @modified By：
 * @version: V1.0
 */
public class Widget {

    public  synchronized void test(){
        System.out.println("join widget .......");
    }
}
