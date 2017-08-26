package bertking.com.openglproject.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import bertking.com.openglproject.MyApplication;

/**
 * Created by apecrafts on 16/8/2.
 * <p>
 * https://developer.android.com/reference/android/util/DisplayMetrics.html
 */
public class ScreenUtil {
    public static final int WIDTH_THAN_HEIGHT = 0;
    public static final int HEIGHT_THAN_WIDTH = 1;
    public static Context context = MyApplication.getInstance().getApplicationContext();


    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 获取屏幕的宽度 width
     *
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕的宽度  width
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getScreenWidth() {
        return getDisplayMetrics(context).widthPixels;
    }


    /**
     * 获取屏幕的高度 height
     *
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获取屏幕的高度 height
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    public static int getScreenHeight() {
        return getDisplayMetrics(context).heightPixels;
    }

    /**
     * 获取屏幕的分辨率 density
     *
     * @param activity
     * @return
     */
    public static float getScreenDensity(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }

    /**
     * 获取屏幕的分辨率 density
     *
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        return getDisplayMetrics(context).density;
    }

    public static float getScreenDensity() {
        return getDisplayMetrics(context).density;
    }


    /**
     * dp 转 px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float density = getScreenDensity(context);
        return (int) (dpValue * density + 0.5f);
    }

    public static int dip2px(float dpValue) {
        float density = getScreenDensity(context);
        return (int) (dpValue * density + 0.5f);
    }


    /**
     * px 转 dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float density = getScreenDensity(context);
        return (int) (pxValue / density + 0.5f);
    }

    public static int px2dip(float pxValue) {
        float density = getScreenDensity(context);
        return (int) (pxValue / density + 0.5f);
    }


    //获取手机状态栏高度
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int getStatusBarHeight() {
        return getStatusBarHeight(context);
    }




    /**
     * 获取 Android某些垃圾手机的虚拟键
     *
     * @param context
     * @return
     */
    public static Point getNavigationBarSize(Context context) {
        Point appUsableSize = getAppUsableScreenSize(context);
        Point realScreenSize = getRealScreenSize(context);
// navigation bar on the right
        if (appUsableSize.x < realScreenSize.x) {
            return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
        }
// navigation bar at the bottom
        if (appUsableSize.y < realScreenSize.y) {
            return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
        }
// navigation bar is not present
        return new Point();
    }


    public static Point getNavigationBarSize() {
        return getNavigationBarSize(context);
    }


    public static Point getAppUsableScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static Point getAppUsableScreenSize() {
        return getAppUsableScreenSize(context);
    }

    /**
     * 获取真实屏幕：即去掉虚拟键
     *
     * @param context
     * @return
     */
    public static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else if (Build.VERSION.SDK_INT >= 14) {
            try {
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
        }
        return size;
    }

    public static Point getRealScreenSize() {
        return getRealScreenSize(context);
    }


}
