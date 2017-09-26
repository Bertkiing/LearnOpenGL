package bertking.com.openglproject;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by Bertking on 2017/9/26.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MyApplication myApplication);
}
