package bertking.com.openglproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.apache.commons.lang3.math.NumberUtils;

import bertking.com.openglproject.R;
import bertking.com.openglproject.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApacheTestActivity extends AppCompatActivity {

    private static final String TAG = ApacheTestActivity.class.getSimpleName();
    @BindView(R.id.btn_proxy)
    Button mBtnProxy;
    @BindView(R.id.btn_other)
    Button mBtnOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apache_test);
        ButterKnife.bind(this);
        initDatas();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initDatas() {
        int i = NumberUtils.toInt("");//output:0
        Log.d(TAG, "initDatas: ===" + i);
        int nullValue = NumberUtils.toInt(null);//output:0
        Log.d(TAG, "initDatas: ===" + nullValue);
    }

    @OnClick({R.id.btn_proxy, R.id.btn_other, R.id.btn_vega})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_proxy:
                Util.startOtherActivity(this, ProxyActivity.class);
                break;
            case R.id.btn_other:
                break;
            case R.id.btn_vega:
                Util.startOtherActivity(this, VegaActivity.class);
                break;
        }
    }


}
