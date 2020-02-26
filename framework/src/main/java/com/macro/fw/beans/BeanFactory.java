package com.macro.fw.beans;

import com.macro.fw.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    private static Map<Class<?>,Object> classToBean = new ConcurrentHashMap<>();

    public static Object getBean(Class<?> cls){
        return classToBean.get(cls);
    }

    public static void initBean(List<Class<?>> classList) throws Exception {
        List<Class<?>> toCreate = new ArrayList<>(classList);

        while(toCreate.size() != 0){
            int remainSize = toCreate.size();
            for(int i=0;i<toCreate.size();i++){
                if(finishCreate(toCreate.get(i))){
                    toCreate.remove(i);
                }
            }
            if(toCreate.size() == remainSize){
                //
                throw new Exception("可能类互相引用死循环了");
            }
        }
    }

    //
    private static boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
        // 没有标记注解
        if(!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class) ){
            return true;
        }



        // 标记了bean或controller注解的

        Object bean = cls.newInstance();

        System.out.println("=====");
        System.out.println(bean);

        // 获得所有字段
        for(Field field : cls.getDeclaredFields()){
            //如果标记了autowired注解
            if(field.isAnnotationPresent(AutoWired.class)){

                Class<?> fieldType = field.getType();   // 字段类型
                Object reliantBean = BeanFactory.getBean(fieldType);
                System.out.println(fieldType);
                System.out.println(reliantBean);

                // 这个字段里的值若为空，就先跳过。 等待这个字段对应的类被实例化后，再赋值，相当于依赖注入
                if(reliantBean == null ){
                    return false;
                }
                field.setAccessible(true);
                field.set(bean,reliantBean);    // 给这个field设置值
            }
        }
        classToBean.put(cls,bean);
        return true;
    }
}
