package bertking.com.openglproject.bean;

import android.util.Log;

/**
 * Created by Bertking on 2017/9/26.
 * 委托类：即"被代理类"
 */

public class ClassA {
    private static final String TAG = ClassA.class.getSimpleName();

    public void method1() {
        Log.d(TAG, "method1: ");
    }

    public void method2() {
        Log.d(TAG, "method2: ");
    }

    public void method3() {
        Log.d(TAG, "method3: ");
    }
}
