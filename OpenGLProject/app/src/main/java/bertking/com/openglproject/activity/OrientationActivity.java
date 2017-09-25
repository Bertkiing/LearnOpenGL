package bertking.com.openglproject.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import bertking.com.openglproject.R;
import bertking.com.openglproject.util.LogUtil;
import bertking.com.openglproject.util.ScreenRotateUtil;

public class OrientationActivity extends AppCompatActivity implements SensorEventListener {
    public static final String TAG = OrientationActivity.class.getSimpleName();
    private Context mContext;
    private boolean isLandscape = false;      // 默认是竖屏
    private int mOrientation;

    private static final int _DATA_X = 0;
    private static final int _DATA_Y = 1;
    private static final int _DATA_Z = 2;

    public static final int ORIENTATION_UNKNOWN = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_orientation);
        Log.d(TAG, "========onCreate========");
        mContext = this;


    }

    @Override
    protected void onResume() {
        super.onResume();

        ScreenRotateUtil.getInstance(this).start(this);
        showOrientation();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "=======onConfigurationChanged=======");
        showOrientation();
    }

    private void showOrientation() {
        LogUtil.d(TAG, "===============" + mOrientation);
        if (isLandscape) {
            Toast.makeText(this, "当前为横屏", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "当前为竖屏", Toast.LENGTH_SHORT).show();
        }
    }

    private void showOrientation2() {
        if (ScreenRotateUtil.getInstance(this).isLandscape()) {
            Toast.makeText(this, "当前为横屏", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "当前为竖屏", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScreenRotateUtil.getInstance(this).stop();

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        mOrientation = ORIENTATION_UNKNOWN;
        float X = -values[_DATA_X];
        float Y = -values[_DATA_Y];
        float Z = -values[_DATA_Z];
        float magnitude = X * X + Y * Y;
        // Don't trust the angle if the magnitude is small compared to the y
        // value
        if (magnitude * 4 >= Z * Z) {
            // 屏幕旋转时
            float OneEightyOverPi = 57.29577957855f;
            float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
            mOrientation = 90 - Math.round(angle);
            // normalize to 0 - 359 range
            while (mOrientation >= 360) {
                mOrientation -= 360;
            }
            while (mOrientation < 0) {
                mOrientation += 360;
            }
        }


        if (mOrientation > 45 && mOrientation < 135) {

//                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            isLandscape = true;
        } else if (mOrientation > 135 && mOrientation < 225) {

//                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            isLandscape = false;
        } else if (mOrientation > 225 && mOrientation < 315) {

//                    mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            isLandscape = true;
        } else if ((mOrientation > 315 && mOrientation < 360) || (mOrientation > 0 && mOrientation < 45)) {

//                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            isLandscape = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
