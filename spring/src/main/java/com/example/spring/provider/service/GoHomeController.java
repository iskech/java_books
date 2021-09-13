package com.example.spring.provider.service;

import javax.annotation.Resource;

public class GoHomeController {
    private GoHomeImpl go;

    public static GoHomeController createInstance() {
        return null;
    }

    public String goHome() {
        go.gohome();
        return "回到家中-0 0-";
    }

    public void setGo(GoHomeImpl go) {
       this.go = go;
    }
}
