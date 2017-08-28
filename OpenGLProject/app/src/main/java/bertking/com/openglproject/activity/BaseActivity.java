package bertking.com.openglproject.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 参考链接：https://mp.weixin.qq.com/s/VCTPXO49dp2x3w7tOFPvvw
 *  typeface:字型，字体
 *  delegate：代理，委托
 *
 */

public class BaseActivity extends AppCompatActivity {
    private Typeface mTypeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        设置字体
        if(mTypeface == null){
            mTypeface = Typeface.createFromAsset(getAssets(),"hua_wen_xing_kai.ttf");
            LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
                @Override
                public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                    AppCompatDelegate delegate = getDelegate();
                    View view = delegate.createView(parent, name, context, attrs);
                    if(view != null && (view instanceof TextView)){
                        ((TextView) view).setTypeface(mTypeface);
                    }
                    return view;
                }
            });
        }
        super.onCreate(savedInstanceState);
    }
}
