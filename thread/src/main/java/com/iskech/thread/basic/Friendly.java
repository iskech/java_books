package com.iskech.thread.basic;

/**
 * @author ：liujx
 * @date ：Created in 2020/5/9 15:50
 * @description：模拟死锁
 * @modified By：
 * @version: V1.0
 */
public class Friendly {
    private Friendly partner;
    private String name;
    private boolean doYield;

    public Friendly(String name, boolean doYield) {
        this.name = name;
        this.doYield = doYield;
    }

    public synchronized void hug() {
        System.out.println(
                Thread.currentThread().getName() + ":" + this.name + ".hug()" + "trying invoke," + partner.name
                        + ".hugBack()");
        if (doYield) {
            Thread.yield();
        }
        //被拥抱后拥抱对方
        partner.hugBack();
    }

    public synchronized void hugBack() {
        System.out.println(Thread.currentThread().getName() + ":" + this.name + ".hugBack()");
    }

    public Friendly becomeFriends(Friendly friendly) {
        this.partner = friendly;
        return friendly;
    }

    public static void main(String[] args) {

        Friendly iskech = new Friendly("iskech", true);
        Friendly alice = new Friendly("alice", false);
        iskech.becomeFriends(alice);
        alice.becomeFriends(iskech);

        Thread thread1 = new Thread(new Runnable() {
            @Override public void run() {
                //拥抱alice
                iskech.hug();
            }
        }, "iskech_thread");

        Thread thread2 = new Thread(new Runnable() {
            @Override public void run() {
                //拥抱iskech
                alice.hug();
            }
        }, "alice_thread");
        /*thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.setPriority(Thread.MAX_PRIORITY);*/
        thread1.start();
        thread2.start();

        /**
         * iskech_thread 进入hug持有iskech锁，然后alice_thead进入hug持有alice锁，此时线程都调hugBack,都需要对方的锁，导致死锁。
         */
    }

}
