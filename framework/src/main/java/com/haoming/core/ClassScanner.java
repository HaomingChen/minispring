package com.haoming.core;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author 58212
 * @date 2020-01-28 21:36
 */
public class ClassScanner {
    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
        //利用ClassLoader寻找class的全类名
        List<Class<?>> classList = new ArrayList<>();
        String path = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            //仅仅处理资源类型为jar包
            if (resource.getProtocol().contains("jar")) {
                //在jar包中寻找class文件
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();
                classList.addAll(getClassesFromJar(jarFilePath, path));
            } else {
                //todo
            }
        }
        return classList;
    }

    private static List<Class<?>> getClassesFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName(); //com/haoming/test/Test.class
            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                //这一步使得class路径名变为com.haoming.Application.class
                //获得com.haoming.Application全类名
                String classFullName = entryName.replace("/", ".").
                        substring(0, entryName.length() - 6);
                System.out.println("Class Full Name" + classFullName);
                //利用ClassLoader和全类名获取Class
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }
}
