package bertking.com.openglproject.bean.customize_annatation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Bertking on 2017/9/26.
 * 自定义 注解Annotation
 * 参考自：http://a.codekk.com/detail/Android/Trinea/%E5%85%AC%E5%85%B1%E6%8A%80%E6%9C%AF%E7%82%B9%E4%B9%8B%20Java%20%E6%B3%A8%E8%A7%A3%20Annotation
 * <p>
 * 1.@Documented 是否会保存到 Javadoc 文档中
 * 2.@Retention 保留时间，可选值 SOURCE（源码时），CLASS（编译时），RUNTIME（运行时），
 * 默认为 CLASS，
 * SOURCE 大都为 Mark Annotation，这类 Annotation 大都用来校验，比如 Override, SuppressWarnings
 * <p>
 * 3.@Target 可以用来修饰哪些程序元素，如 TYPE, METHOD, CONSTRUCTOR, FIELD, PARAMETER 等,未标注则表示可修饰所有
 * 4.@Inherited 是否可以被继承，默认为 false
 * <p>
 * <p>
 * a.运行时 Annotation 指 @Retention 为 RUNTIME 的 Annotation
 * b.编译时 Annotation 指 @Retention 为 CLASS 的 Annotation，甴编译器自动解析
 */

//不能用于构造函数

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface MethodInfo {
    String author() default "kingcheeng163@gmail.com";

    String date();

    int version() default 1;

}
