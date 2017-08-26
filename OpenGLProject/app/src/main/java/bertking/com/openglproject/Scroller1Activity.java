package bertking.com.openglproject;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import bertking.com.openglproject.util.LogUtil;
import bertking.com.openglproject.util.ScreenUtil;

public class Scroller1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scroller1);
        LogUtil.d("-------------"+ ScreenUtil.dip2px(300));
//
//        View view = LayoutInflater.from(this).inflate(R.layout.com_loading_refresh, null);
        ImageView imageView = (ImageView) findViewById(R.id.static_loading);
        imageView.setImageResource(R.drawable.anim_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
    }
}
