package org.example.web.server;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.example.web.servlet.DispatcherServlet;

public class TomcatServer {

    private Tomcat tomcat;
    private String[] agrs;

    public TomcatServer(String[] agrs) {
        this.agrs = agrs;
    }


    public void start() throws LifecycleException {
        int port = 80;
        tomcat = new Tomcat();
        tomcat.setPort(port);

        //实例化context容器
        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());

        // 添加 dispatcher
        Tomcat.addServlet(context, "DispatcherServlet", new DispatcherServlet()).setAsyncSupported(true);
        context.addServletMappingDecoded("/*", "DispatcherServlet");

        tomcat.getHost().addChild(context);
        tomcat.start();
        System.out.println(String.format("server start in port %d  http://%s:%d",
                port,
                tomcat.getServer().getAddress()
                , port));

        Thread waitThread = new Thread(() -> tomcat.getServer().await());
        waitThread.setDaemon(false);
        waitThread.start();


    }

}
