package bertking.com.openglproject.activity;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import bertking.com.openglproject.R;
import bertking.com.openglproject.util.DialogUtils;
import bertking.com.openglproject.util.LogUtil;
import bertking.com.openglproject.util.ScreenUtil;

public class Scroller1Activity extends BaseActivity {

    private AnimationDrawable mAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("-----onCreate----------");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scroller1);
                LogUtil.d("-------------"+ ScreenUtil.dip2px(300));
//
//        View view = LayoutInflater.from(this).inflate(R.layout.com_loading_refresh, null);
        ImageView imageView = (ImageView) findViewById(R.id.static_loading);
        imageView.setImageResource(R.drawable.anim_loading);
        mAnimationDrawable = (AnimationDrawable) imageView.getDrawable();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LogUtil.d("------onWindowFocusChanged-----");
        mAnimationDrawable.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("------------onStart------------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("-------onResume--------------");
        Dialog dialog = DialogUtils.createLoadingDialog(this, "加载动画...");
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAnimationDrawable!= null||mAnimationDrawable.isRunning()){
            mAnimationDrawable.stop();
        }
    }
}
