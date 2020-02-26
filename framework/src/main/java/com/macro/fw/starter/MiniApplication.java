package com.macro.fw.starter;

import com.macro.fw.beans.BeanFactory;
import com.macro.fw.core.ClassSannner;
import com.macro.fw.web.handler.HandlerManager;
import com.macro.fw.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

public class MiniApplication {
    // 传入应用入口类和参数
    public static void run(Class<?> cls,String[] args){
        System.out.println("hello run");

        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();

            //System.out.println(cls.getPackage().getName());
            //获得入口类所在包名 com.macro.zbs
            List<Class<?>>  classList = ClassSannner.scanClasses(cls.getPackage().getName());
            classList.forEach(it -> System.out.println(it) );


            // 初始化bean
            BeanFactory.initBean(classList);


            // 初始化handle
            HandlerManager.resolveMappingHandler(classList);


        } catch (LifecycleException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
