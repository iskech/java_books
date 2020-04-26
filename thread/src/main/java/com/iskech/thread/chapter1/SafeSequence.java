package com.iskech.thread.chapter1;

/**
 * @author ：liujx
 * @date ：Created in 2020/4/26 11:15
 * @description：线程安全示例(synchronized)
 * @modified By：
 * @version: V1.0
 */
public class SafeSequence {
  private static Integer value = 0;

  public static synchronized Integer getValue() {
    ++value;
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return SafeSequence.value;
  }

  public static void main(String[] args) {
     for (int index = 1; index <= 50; index++) {
      System.out.println(SafeSequence.getValue());
    }
    SafeSequence.value = 0;
    System.err.println("=====================分界线================");
    for (int index = 1; index <= 50; index++) {
      CustomRunnable customRunnable = new CustomRunnable();
      new Thread(customRunnable).start();
    }
  }

  private static class CustomRunnable implements Runnable {
    public void run() {
      System.out.println(SafeSequence.getValue());
    }
  }
}
