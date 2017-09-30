package bertking.com.openglproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.launcher.ARouter;

import bertking.com.openglproject.R;
import bertking.com.openglproject.bean.TestPar;

@Route(path = "/activity/ARounterTestActivity")
public class ARounterTestActivity extends AppCompatActivity {

    private static final String TAG = ARounterTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arounter_test);

        SerializationService serializationService = ARouter.getInstance().navigation(SerializationService.class);
        TestPar testPar = serializationService.json2Object(getIntent().getStringExtra("key3"), TestPar.class);
        Log.d(TAG, "onCreate: =======testPar:====" + testPar.getName());
    }
}
