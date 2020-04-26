package com.iskech.thread.chapter1;

/**
 * @author ：liujx
 * @date ：Created in 2020/4/26 11:15
 * @description：线程不安全示例
 * @modified By：
 * @version: V1.0
 */
public class UnsafeSequence {
  private static Integer value = 0;

  public static Integer getValue() {
    ++value;
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return UnsafeSequence.value;
  }

  public static void main(String[] args) {
     for (int index = 1; index <= 50; index++) {
      System.out.println(UnsafeSequence.getValue());
    }
    UnsafeSequence.value = 0;
    System.err.println("=====================分界线================");
    for (int index = 1; index <= 50; index++) {
      CustomRunnable customRunnable = new CustomRunnable();
      new Thread(customRunnable).start();
    }
  }

  private static class CustomRunnable implements Runnable {
    public void run() {
      System.out.println(UnsafeSequence.getValue());
    }
  }
}
