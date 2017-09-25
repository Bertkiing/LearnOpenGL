package bertking.com.openglproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.apache.commons.lang3.math.NumberUtils;

import bertking.com.openglproject.R;

public class ApacheTestActivity extends AppCompatActivity {

    private static final String TAG = ApacheTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apache_test);
        initDatas();
        Log.d(TAG, "onCreate: ");
    }

    private void initDatas() {
        int i = NumberUtils.toInt("");//output:0
        Log.d(TAG, "initDatas: ===" + i);
        int nullValue = NumberUtils.toInt(null);//output:0
        Log.d(TAG, "initDatas: ===" + nullValue);
    }

}
