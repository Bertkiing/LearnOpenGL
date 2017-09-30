package bertking.com.openglproject;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by king on 2017/8/26.
 * 项目名称:OpenGLProject
 * 描述:
 */

public class MyApplication extends Application {
    private static Application instance;
    private        Context     mContext;
    boolean isDebug = true;


    public static synchronized Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();

        initARouter();

        DaggerAppComponent
                .builder()
                .appModule(new AppModule(this, mContext))
                .build()
                .inject(this);

    }


    /**
     * 初始化“阿里路由框架”
     */
    private void initARouter() {
        if (isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
}
