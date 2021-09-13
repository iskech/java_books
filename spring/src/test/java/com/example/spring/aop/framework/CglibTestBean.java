/*
 * Created on 06-Oct-2004
 */
package com.example.spring.aop.framework;

/**
 * @author robh
 */
public class CglibTestBean {

    private String name;
    
    public CglibTestBean() {
        setName("Some Default");
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
