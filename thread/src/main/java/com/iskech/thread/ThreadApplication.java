package com.iskech.thread;

import com.example.spring.provider.spring1_5.boot.SpringApplication;
import com.example.spring.provider.spring1_5.boot.autoconfigure.SpringBootApplication;
import com.example.spring.provider.spring1_5.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication public class ThreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadApplication.class, args);
    }

}
