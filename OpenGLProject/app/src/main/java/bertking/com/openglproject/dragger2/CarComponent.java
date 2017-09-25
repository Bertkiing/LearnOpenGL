package bertking.com.openglproject.dragger2;

import dagger.Component;

/**
 * Created by king on 2017/9/25.
 * 项目名称:OpenGLProject
 * 描述:
 */
@Component(modules = CarModule.class)
public interface CarComponent {
    void inject(Car car);
}
