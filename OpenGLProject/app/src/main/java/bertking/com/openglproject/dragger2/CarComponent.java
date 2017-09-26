package bertking.com.openglproject.dragger2;

import dagger.Component;

/**
 * Created by king on 2017/9/25.
 * 项目名称:OpenGLProject
 * 描述:
 *
 * 参考链接：http://mushuichuan.com/2016/02/18/dagger/
 */
@Component(modules = CarModule.class)
public interface CarComponent {
    void inject(Car car);
}
