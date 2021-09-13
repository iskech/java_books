package com.example.spring.provider.test;

import com.example.spring.provider.service.GoHomeController;

import com.example.spring.provider.spring1_5.beans.factory.xml.XmlBeanFactory;
import com.example.spring.provider.spring1_5.core.io.ClassPathResource;

public class TestXmlBeanFactory {

    public static void main(String[] args) {
        ClassPathResource classPathResource = new ClassPathResource("application.xml");
        XmlBeanFactory     context = new XmlBeanFactory(classPathResource);
        GoHomeController goHome = (GoHomeController) context.getBean("goHomeController",GoHomeController.class);
        System.out.println(goHome.getClass());
        goHome.goHome();
    }
}
