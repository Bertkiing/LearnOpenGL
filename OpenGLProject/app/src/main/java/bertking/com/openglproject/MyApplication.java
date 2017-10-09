package bertking.com.openglproject;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.didi.virtualapk.PluginManager;

/**
 * Created by king on 2017/8/26.
 * 项目名称:OpenGLProject
 * 描述:
 */

public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getSimpleName();
    private static Application instance;
    private        Context     mContext;
    boolean isDebug = true;


    public static synchronized Application getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
        Log.d(TAG, "attachBaseContext: ==========");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        Log.d(TAG, "onCreate: ===============");
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
