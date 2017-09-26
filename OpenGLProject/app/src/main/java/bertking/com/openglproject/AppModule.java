package bertking.com.openglproject;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bertking on 2017/9/26.
 */
@Module
public class AppModule {
    private MyApplication mApplication;
    private Context mContext;

    public AppModule(MyApplication application, Context context) {
        mApplication = application;
        mContext = context;
    }

    @Provides
    @Singleton
    public MyApplication getApplication() {
        return mApplication;
    }


    @Provides
    @Singleton
    public Context getContext() {
        return mContext;
    }
}
