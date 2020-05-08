package com.iskech.thread.chapter1;

import net.jcip.annotations.ThreadSafe;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ：liujx
 * @date ：Created in 2020/4/26 16:10
 * @description：使用atomicLong避免 下，出；了，吗，是，。出
 * @modified By：
 * @version: V1.0
 */
@ThreadSafe @WebServlet(name = "atomicServlet", urlPatterns = "/atomicServlet") public class AtomicCountingFactorizer
        extends GenericServlet implements Servlet {
    private final AtomicLong count = new AtomicLong(0);

    @Override public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        encodeIntoResponse(resp, factors);
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
        resp.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter writer = resp.getWriter();
            //线程安全情况下请求的返回值应该为上一次请求返回值+1但若在偶数请求时又发起奇数请求则请求的返回值不一定为上一次请求的值+1
            //count线程不安全，

            writer.println("计算后的值：" + factors[0] + "线程名称：" + Thread.currentThread().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    BigInteger extractFromRequest(ServletRequest req) {
        //该方法不是原子性的  需要先获取value值，后获取count值，再count+1操作，后将count新的值写入，再读取count值后将vaLue+count
        //这里使用休眠放大多线程同时访问方法不是原子性的效果。线程1还没执行完成该方法，另外一个线程就已经同时执行完成该方法。并修改了count值造成线程1计算结果不如预期。
        String value = req.getParameter("value");
        count.addAndGet(1L);
        try {
            if (Integer.valueOf(value) % 2 == 0) {
                Thread.sleep(5000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (Thread.activeCount()>1){
            Thread.yield();
        }
        return new BigInteger(value).add(new BigInteger(String.valueOf(count)));
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}
