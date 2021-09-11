package com.example.spring;

import com.example.spring.provider.service.GoHomeImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations = {"classpath:application.xml"})
public class ApplicationTests {

    @Test
    public void contextLoads() {
        //手动创建 applicationContext
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        GoHomeImpl goHome = context.getBean(GoHomeImpl.class);
        System.out.println(goHome.getClass());
    }

    @Test
    public void customer() {
        //首先进行BeanDefinition资源文件的定位，封装为Source的子类
        ClassPathResource res = new ClassPathResource("application.xml");
        //创建基本的IOC容器
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
       //创建文件读取器，并进行回调设置。
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        //资源加载解析
        reader.loadBeanDefinitions(res);
        //https://www.jianshu.com/p/5c781f264467
        //https://zhuanlan.zhihu.com/p/29344811
        System.out.println(factory);
    }

}
