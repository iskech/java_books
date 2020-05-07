package com.iskech.thread.basic;

/**
 * @author ：liujx
 * @date ：Created in 2020/5/7 11:28
 * @description：
 * @modified By：
 * @version: V1.0
 */
public class AbastractRunableImpl extends AbastractRunable {
    public static void main(String[] args) {
        Thread thread = new Thread(new AbastractRunableImpl());

    }
}
