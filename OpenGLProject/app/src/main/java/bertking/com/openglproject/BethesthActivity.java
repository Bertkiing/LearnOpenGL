package bertking.com.openglproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BethesthActivity extends AppCompatActivity {
    private static final String TAG = BethesthActivity.class.getCanonicalName();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bethesth);

    }

    /**
     * blah-blah1
     */
    public void sdfjl() {
        for (int j = 0; j < 10; j++) {
            Log.i(TAG, String.format("Iteration:%s", j));
        }
    }
}
