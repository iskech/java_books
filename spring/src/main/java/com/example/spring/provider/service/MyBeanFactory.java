package com.example.spring.provider.service;

public class MyBeanFactory {
    public GoHomeController createInstance(GoHomeImpl goHome) {
        GoHomeController goHomeController = new GoHomeController();
        goHomeController.setGo(goHome);
        return goHomeController;
    }
}
