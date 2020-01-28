1. 系统Web服务模型
客户端发送请求 ----bit流----> 操作系统(TCP/IP栈) 
-> web服务器(Servlet容器) -> Servlet容器处理请求
-> 返回Response

web服务器(tomcat) 监听TCP端口 -> 
Servlet: 
一种规范: 约束了Java服务器与业务类的通信方式
一个接口: javax.servlet.Servlet
一种java类: 实现了Servlet接口的应用程序类

2. Tomcat的四个容器 Engine Host Context Wrapper
Servlet在Context中注册

3. Tomcat可以通过web.xml配置servlet转发路径

4. Spring -> 利用一个Servlet来转发所有的请求
web请求不再由servlet容器分发而由程序本身分发

5. @Document注解: @Documented注解标记的元素，
Javadoc工具会将此注解标记元素的注解信息包含在javadoc中。
默认，注解信息不会包含在Javadoc中。

6. @Retention作用是定义被它所注解的注解保留多久，一共有三种策略，
定义在RetentionPolicy枚举中.
从注释上看：
source：注解只保留在源文件，
当Java文件编译成class文件的时候，注解被遗弃；被编译器忽略
class：注解被保留到class文件，但jvm加载class文件时候被遗弃，
这是默认的生命周期
runtime：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
这3个生命周期分别对应于：Java源文件(.java文件) ---> 
.class文件 ---> 内存中的字节码。

7. Class Loader -> 将.class加载进jvm内存中 -> 
Execution Engine(JIT, GC) -> Native Library
1. 通过类的全限定名获得类的二进制字节流
2. 解析二进制字节流, 获取Class类实例
3. 加载classpath下的静态资源

8. Servlet注册进Tomcat的Context容器中
ClassScanner ->    
//通过包名, 获取jar包, 获取class, 获取类名
//实际生产代码 SpringApplication.run(HelloWorldMainApplication.class, args);
//获取HelloWorldMainApplication.class所在的jar包 -> ClassLoader获取该Class所在的包名扫描该包中所有的class类
//获得该jar包下所有类的全类名利用ClassLoader加载Class
//将所有的class类利用MappingHandlerManager遍历利用MappingHandler包装class
List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
//在Servlet中遍历MappingHandler中的容器 -> 存放MappingHandler(通过反射获取拥有@Controller注解的包装类)
//实例化MappingHandler调用MappingHandler中的方法处理请求
