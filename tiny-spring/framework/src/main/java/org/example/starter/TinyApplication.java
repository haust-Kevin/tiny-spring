package org.example.starter;

import org.apache.catalina.LifecycleException;
import org.example.beans.BeanFactory;
import org.example.core.ClassScanner;
import org.example.web.handler.HandlerMgr;
import org.example.web.server.TomcatServer;

import java.util.List;

/**
 * 框架启动类，同 Spring 的 SpringApplication
 */
public class TinyApplication {
    public static void run(Class<?> cls,String[] args){
        System.out.println("Hello tiny-spring application！");

        TomcatServer server = new TomcatServer(args);
        try {
            server.start();
            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
            classList.forEach(it->System.out.println(it.getName()));
            HandlerMgr.resolveMappingHandler(classList);
            BeanFactory.initBean(classList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}