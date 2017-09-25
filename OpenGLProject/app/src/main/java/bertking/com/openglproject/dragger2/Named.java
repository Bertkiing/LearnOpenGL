package bertking.com.openglproject.dragger2;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by king on 2017/9/26.
 * 项目名称:OpenGLProject
 * 描述:
 */


@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Named {
    String value() default "";
}
