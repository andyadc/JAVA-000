package com.andyadc.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，
 * 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件
 */
public class CustomClassLoader extends ClassLoader {

	/**
	 * 文件路径
	 */
    private String path;

    public CustomClassLoader() {
    }

    public CustomClassLoader(String path) {
        super();
        this.path = path;
    }

    public static void main(String[] args) throws Exception {
    	String  path = "/Hello.xlass";
        CustomClassLoader loader = new CustomClassLoader(path);

        Class clazz = loader.findClass("Hello");
        Method method = clazz.getMethod("hello");
        method.invoke(clazz.newInstance(), null);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(path);
        try {
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
