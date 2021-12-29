package org.example;

import org.example.starter.TinyApplication;

public class TestApplication {
    public static void main(String[] args) {
        //测试打印
        System.out.println("Hello World!");
        //启动方法
        TinyApplication.run(TestApplication.class, args);
    }
}
