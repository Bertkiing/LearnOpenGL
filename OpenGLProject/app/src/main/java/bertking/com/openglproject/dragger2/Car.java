package bertking.com.openglproject.dragger2;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import static bertking.com.openglproject.activity.RxjavaThreadActivity.TAG;

/**
 * Created by king on 2017/9/25.
 * 项目名称:OpenGLProject
 * 描述:
 */

public class Car {
    @Inject
    Engine mEngine;
    @Inject
    Seat mSeat;
    @Inject
    Wheel mWheel;

    @LeatherColor(color = "black")
    @Inject
    Leather mLeather;

    public Car(){
        DaggerCarComponent
                .builder()
                .carModule(new CarModule())
                .build()
                .inject(this);
        Log.d(TAG, "Car: ==="+"new Car()");
    }
}
