import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @name Hello Class Loader
 *
 * @description 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有
 * 字节（x=255-x）处理后的文件。文件群里提供。
 *
 * @author Sunyt
 */
public class HelloClassLoader extends ClassLoader {
    // 类名
    private static final String CLASS_NAME = "Hello";
    // 方法名
    private static final String METHOD_NAME = "hello";

    public static void main(String[] args) throws Exception {
        // 加载类
        Class<?> clazz = new HelloClassLoader().findClass(CLASS_NAME);

        // new 一个实例
        Object obj = clazz.newInstance();
        System.out.println(obj);

        // 调用 hello 方法
        clazz.getMethod(METHOD_NAME).invoke(obj);
    }

    /**
     * 加载类
     * @param name 类名
     * @return 类
     * @throws ClassNotFoundException 未找到类异常
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 文件路径
        final String path = this.getClass().getResource("/Hello.xlass").getPath();

        // 读取文件
        byte[] fileBytes = readFile(path);

        // 解密字节数组
        for (int i = 0; i < fileBytes.length; i++) {
            fileBytes[i] = (byte) (255 - fileBytes[i]);
        }

        // 定义类
        return defineClass(name, fileBytes, 0, fileBytes.length);
    }

    /**
     * 读取文件
     * @param path 文件路径
     * @return 字节数组
     */
    private byte[] readFile(String path) {
        File file = new File(path);
        byte[] bytes = new byte[2048];
        try {
            new FileInputStream(file).read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
