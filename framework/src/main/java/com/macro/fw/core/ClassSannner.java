package com.macro.fw.core;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassSannner {

    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        //com.macro.zbs => com/macro/zbs
        String path = packageName.replace(".","/");
        System.out.println(path);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();   // 获取默认类加载器
        Enumeration<URL> resources = classLoader.getResources(path);

        while(resources.hasMoreElements()){
            URL resource = resources.nextElement();

            if(resource.getProtocol().contains("jar")){
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();

                //System.out.println(jarFilePath);
                // jar包路径 /Users/edz/Desktop/code/java/minispring/test/build/libs/test-1.0-SNAPSHOT.jar
                // 获取jar包中的class ，限定包路径为com/macro/zbs
                classList.addAll(getClassesFromJar(jarFilePath,path));
            }else{
                //
            }

        }

        return classList;
    }

    private static List<Class<?>> getClassesFromJar(String jarFilePath,String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while(jarEntries.hasMoreElements()){
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName();  // com/macro/zbs/..

            if(entryName.startsWith(path) && entryName.endsWith(".class")){
                String classFullName = entryName.replace("/",".").substring(0,entryName.length()-6);
                classes.add(Class.forName(classFullName));
            }

        }
        return classes;
    }







}
