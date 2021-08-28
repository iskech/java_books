package com.iskech.thread.chapter1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：liujx
 * @date ：Created in 2020/4/27 15:27
 * @description：原子类型模拟并发计算测试2
 * @modified By：
 * @version: V1.0
 */
public class AtomicCountingTest {
    private final static AtomicInteger atomicInteger=new AtomicInteger(0);

    public void increment(){
        AtomicCountingTest.atomicInteger.incrementAndGet();


    }

}
