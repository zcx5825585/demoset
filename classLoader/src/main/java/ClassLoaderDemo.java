import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderDemo {
    public static void main(String[] args) throws Exception {
        String rootPath = "D:/IdeaProjects/demoset/classLoader/src/main/resources/";
        String path = rootPath + "script/Bridge.java"; //路径
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); //调用动态编译的工具
        int result = compiler.run(null, null, null, path);
        ; //进行动态编译，并返回结果
        System.out.println(result == 0 ? "编译成功" : "编译失败");

        //1、首先构建文件的目录url地址，
        URL[] urls = new URL[]{new URL("file:/" + rootPath)};
//        //2、使用URLClassLoader对象的loadClass方法加载对应类
        URLClassLoader loder = new URLClassLoader(urls);
        //3、获取所加载类的方法
        Class clazz = loder.loadClass("script.Bridge");
        // 4、传入方法所需的参数通过invoke运行方法
        Method method = clazz.getDeclaredMethod("main", String[].class);
        method.invoke(null, (Object) new String[]{}); //当类型为String[]时，需要(Object)new String[] {}初始化
    }
}
