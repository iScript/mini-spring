package com.macro.fw.web.handler;

import com.macro.fw.web.mvc.Controller;
import com.macro.fw.web.mvc.RequestMapping;
import com.macro.fw.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class HandlerManager {

    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    public static void resolveMappingHandler(List<Class<?>> classList){
        for(Class<?> cls : classList){
            if(cls.isAnnotationPresent(Controller.class)){  // 判断controller注解是否存在
                parseHandlerFromController(cls);
            }
        }
    }

    private static void parseHandlerFromController(Class<?> cls){
        Method[] methods = cls.getDeclaredMethods();       // 通过反射获取类定义的方法

        for(Method method : methods){
            if(!method.isAnnotationPresent(RequestMapping.class)){  // 方法没被注解，则不处理
                continue;
            }
            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();    //获取RequestMapping注解里的参数
            // System.out.println(uri); //gettest
            List<String> paramNameList = new ArrayList<>();

            for(Parameter parameter : method.getParameters()){
                if(parameter.isAnnotationPresent(RequestParam.class)){
                    paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                    System.out.println(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }

            // list 转array array类型string
            String[] params = paramNameList.toArray(new String[paramNameList.size()]);

            MappingHandler mappingHandler = new MappingHandler(uri,method,cls,params);
            HandlerManager.mappingHandlerList.add(mappingHandler);
        }
     }

}
