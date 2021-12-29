package org.example.core;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
                classList.addAll(getClassFromFile(new File(resource.getFile())));
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
            String entryName = jarEntry.getName();
            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                String classFullName = entryName.replace("/", ".").
                        substring(0, entryName.length() - 6);
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }

    public static List<Class<?>> getClassFromFile(File dir) throws ClassNotFoundException {
        List<Class<?>> result  = new ArrayList<>();
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            assert files != null;
            for (File file : files) {
                result.addAll( getClassFromFile(file));
            }
        } else if (dir.getName().endsWith(".class")) {
            String entryName = dir.getPath();      // 路径替换
            String className = entryName.substring(entryName.indexOf("classes") + 8).replace("\\", ".");
            String classFullName = className.substring(0, className.length() - 6);
            result.add(Class.forName(classFullName));
        }
        return result;
    }

}
