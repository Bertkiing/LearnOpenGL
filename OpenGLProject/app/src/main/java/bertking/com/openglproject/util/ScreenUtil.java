package bertking.com.openglproject.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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



    /******************************以下代码段是为处理顶部状态栏的************************************/

    /**
     * 沉浸式状态栏
     */
    public static boolean immerseStatusBar(Activity activity) {
        boolean success = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            success = true;
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            success = true;
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return success;
    }

    public static boolean setDarkMode(Activity activity) {
        return setDarkMode(activity, true);
    }

    /**
     * darkMode设置
     *
     * @return 是否成功
     */
    public static boolean setDarkMode(Activity activity, boolean darkMode) {
        String brand = Build.BRAND;
        boolean success = false;
        if (brand.contains("Xiaomi")) {
            success = setXiaomiDarkMode(activity, darkMode);
        } else if (brand.contains("Meizu")) {
            success = setMeizuDarkMode(activity, darkMode);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final int lFlags = activity.getWindow().getDecorView().getSystemUiVisibility();
            activity.getWindow().getDecorView().setSystemUiVisibility(darkMode ? (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
            success = true;
        }

        return success;
    }

    private static boolean setXiaomiDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean setMeizuDarkMode(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }
}
