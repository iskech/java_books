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

/**
 * @author ：liujx
 * @date ：Created in 2020/4/26 16:05
 * @description：无状态Servlet 示例
 * @modified By：
 * @version: V1.0
 */
@ThreadSafe @WebServlet(name = "statelessServlet", urlPatterns = "/statelessServlet") public class StatelessFactorizer
        extends GenericServlet implements Servlet {

    @Override public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        encodeIntoResponse(resp, factors);
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
        resp.setContentType("text/html;charset=UTF-8");
        try {
            PrintWriter writer = resp.getWriter();
            //每一个servlet请求都是一个新的线程无状态servlet其数据仅存在线程局部变量中
            //无论请求多少次其请求后的计算值都应该为请求的值+1，无状态servlet其为线程安全的类
            writer.println("计算后的值：" + factors[0] + "线程名称：" + Thread.currentThread().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    BigInteger extractFromRequest(ServletRequest req) {
        String value = req.getParameter("value");
        return new BigInteger(value).add(new BigInteger("1"));
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}
