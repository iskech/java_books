package com.iskech.thread.chapter1;

/**
 * @author ：liujx
 * @date ：Created in 2020/4/26 17:13
 * @description：延迟初始化竞态竞争条件
 * @modified By：
 * @version: V1.0
 */
public class LazyInitRace {
    private static ExpensiveObject instance = null;

    public static ExpensiveObject getInstance() {
        if (instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(LazyInitRace.getInstance().toString());
        System.out.println(LazyInitRace.getInstance().toString());
    }
}

class ExpensiveObject {
}

