package com.iskech.thread.basic;

import java.util.concurrent.CountDownLatch;

/**
 * @author ：liujx
 * @date ：Created in 2020/5/7 14:00
 * @description：银行账户
 * @modified By：
 * @version: V1.0
 */
public class BankAccount {
    private long account;
    private long balance;

    public BankAccount(long balance) {
        this.balance = balance;
    }

    public synchronized long getBalance() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.balance;
    }

    public synchronized void deposit(long amount) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            this.balance += amount;
        }

    }

    public static void main(String[] args) {
        for (int i = 1; i <= 20; i++) {
            BankAccount bankAccount = new BankAccount(0);
            CountDownLatch countDownLatch = new CountDownLatch(500);
            for (int index = 1; index <= 500; index++) {
                new Thread(new Runnable() {
                    @Override public void run() {
                        bankAccount.deposit(100);
                        countDownLatch.countDown();

                    }
                }).start();
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(bankAccount.getBalance() + "当前活跃线程：" + Thread.activeCount());
        }

        assert false;

    }
}
