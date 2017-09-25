package bertking.com.openglproject.dragger2;

import android.util.Log;

import static bertking.com.openglproject.activity.RxjavaThreadActivity.TAG;

/**
 * Created by king on 2017/9/25.
 * 项目名称:OpenGLProject
 * 描述: 车座
 */

public class Seat {
    private Leather mLeather;
    public Seat() {
        Log.d(TAG, "Seat: ===="+"new Seat()");
    }

    public Seat(Leather leather) {
        mLeather = leather;
        Log.d(TAG, "Seat: ==="+"new Seat(leather)");
    }

}
