package bertking.com.openglproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.debug.hv.ViewServer;

import bertking.com.openglproject.R;

public class CreditScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_score);
        ViewServer.get(this).addWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).setFocusedWindow(this);
    }
}
