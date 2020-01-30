package com.haoming.beans;

import com.haoming.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean工厂
 *
 * @author 58212
 * @date 2020-01-30 23:00
 */
public class BeanFactory {

    private static Map<Class<?>, Object> classToBean = new ConcurrentHashMap<>();

    public static Object getBean(Class<?> cls) {
        return classToBean.get(cls);
    }

    //classList所有的class
    public static void initBean(List<Class<?>> classList) throws Exception {
        List<Class<?>> toCreate = new ArrayList<>(classList);
        while (toCreate.size() != 0) {
            int remainSize = toCreate.size();
            //实例化class
            for (int i = 0; i < toCreate.size(); i++) {
                if (finishCreate(toCreate.get(i))) {
                    toCreate.remove(i);
                }
            }
            //遍历了一遍Class列表, 没有类被创建 -> 循环依赖
            //例若A -> B -> C -> D -> E互相依赖则E一定可以被实例化 -> --size -> 若E依赖任何
            //一个其余Class则环形成 -> 循环依赖
            if (toCreate.size() == remainSize) {
                throw new Exception("cycle dependency");
            }
        }
    }

    private static boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
        if (!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)) {
            return true;
        }
        Object bean = cls.newInstance();
        for (Field field : cls.getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoWired.class)) {
                Class<?> fieldType = field.getType();
                //获得依赖的Bean
                Object reliantBean = BeanFactory.getBean(fieldType);
                //依赖的Bean未被初始化
                if (reliantBean == null) {
                    return false;
                }
                field.setAccessible(true);
                //依赖注入
                field.set(bean, reliantBean);
            }
        }
        classToBean.put(cls, bean);
        return true;
    }
}
