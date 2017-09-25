package bertking.com.openglproject;

import android.support.annotation.DrawableRes;

/**
 * Created by king on 2017/9/18.
 * 项目名称:OpenGLProject
 * 描述:
 */

public enum Avatar {
    ONE(R.mipmap.loading_1,"ONE"),
    TWO(R.mipmap.loading_2,"TWO"),
    THREE(R.mipmap.loading_3,"THREE"),
    FOUR(R.mipmap.loading_4,"FOUR"),
    FIVE(R.mipmap.loading_5,"FIVE"),
    SIX(R.mipmap.loading_6,"SIX"),
    SEVEN(R.mipmap.loading_7,"SEVEN"),
    eight(R.mipmap.loading_8,"EIGHT");


    public static final String TAG = Avatar.class.getSimpleName();

    private final int mResId;
    public static final String GET_POST_URL = "www.baidu.com";

    Avatar(@DrawableRes final int resId, String one) {
        mResId = resId;
    }


    @DrawableRes
    public int getDrawableId() {
        return mResId;
    }

    public String getNameForAccessibility() {
        return TAG + " " + ordinal() + 1;
    }
}
