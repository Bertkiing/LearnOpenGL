package bertking.com.openglproject.dragger2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by king on 2017/9/25.
 * 项目名称:OpenGLProject
 * 描述:http://www.jianshu.com/p/e76c1e0e7b40
 */
@Module
public class CarModule {
    @Provides
    public Engine provideEngine() {
        return new Engine();
    }

    @Provides
    public Leather provideLeather() {
        return new Leather();
    }

    @LeatherColor(color = "black")
    @Provides
    public Leather provideIntLeather() {
        return new Leather("black");
    }


    @Provides
    public Seat provideSeat(Leather leather) {
        return new Seat(leather);
    }

    @Provides
    public Wheel provideWheel() {
        return new Wheel();
    }
}
