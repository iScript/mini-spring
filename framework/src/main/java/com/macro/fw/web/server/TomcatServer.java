package com.macro.fw.web.server;

import com.macro.fw.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {

    private Tomcat tomcat;
    private String[] args;

    public TomcatServer(String[] args){
        this.args = args;
    }

    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(6699);
        tomcat.start();

        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        DispatcherServlet dServlet = new DispatcherServlet();
        Tomcat.addServlet(context,"dservlet",dServlet).setAsyncSupported(true);  // 注册servlet
        context.addServletMappingDecoded("/","dservlet");
        tomcat.getHost().addChild(context);


        // 简写方式， new 直接实现接口
        Thread awaitthread = new Thread("tomcat"){

            @Override
            public void run(){
                TomcatServer.this.tomcat.getServer().await();
            }

        };
        awaitthread.setDaemon(false);
        awaitthread.start();
    }
}
