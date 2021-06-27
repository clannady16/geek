package week1;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = new HelloClassLoader().findClass("Hello");
        Object o = clazz.newInstance();
        Method method = clazz.getMethod("hello");
        method.invoke(o);
    }
    @Override
    protected Class<?> findClass(String name) {
        byte[] helloBytesOld = getXlassBytes(
                HelloClassLoader.class.getResource("").getPath()
                        + "/Hello.xlass");
        byte[] helloBytesNew = new byte[helloBytesOld.length];

        for (int i = 0 ; i < helloBytesOld.length; i++) {
            helloBytesNew[i] = (byte) (255 - helloBytesOld[i]);
        }

        return defineClass(name, helloBytesNew,0, helloBytesNew.length);
    }

    public byte[] getXlassBytes(String fileName) {
        File f = new File(fileName);

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }
}
