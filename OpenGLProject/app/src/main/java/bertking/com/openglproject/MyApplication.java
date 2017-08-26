package bertking.com.openglproject;

import android.app.Application;

/**
 * Created by king on 2017/8/26.
 * 项目名称:OpenGLProject
 * 描述:
 */

public class MyApplication extends Application {
    private static Application instance;

    public static synchronized Application  getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
