package bertking.com.openglproject;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.debug.hv.ViewServer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import bertking.com.openglproject.activity.ARounterTestActivity;
import bertking.com.openglproject.activity.ApacheTestActivity;
import bertking.com.openglproject.activity.AsyncTaskActivity;
import bertking.com.openglproject.activity.CreditScoreActivity;
import bertking.com.openglproject.activity.OrientationActivity;
import bertking.com.openglproject.activity.Scroller1Activity;
import bertking.com.openglproject.activity.TestAAActivity;
import bertking.com.openglproject.activity.TextRxJavaActivity;
import bertking.com.openglproject.bean.TestPar;
import bertking.com.openglproject.dragger2.Car;
import bertking.com.openglproject.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 参考链接：http://blog.csdn.net/chenjie19891104/article/details/6311209
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.glSurfaceView)
    GLSurfaceView mGlSurfaceView;
    @BindView(R.id.btn_bethes)
    Button mBtnBethes;
    @BindView(R.id.btn_scroller)
    Button mBtnScroller;
    @BindView(R.id.btn_credit)
    Button mBtnCredit;
    @BindView(R.id.btn_screen_orientation)
    Button mBtnScreenOrientation;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;


    private GLSurfaceView mGLSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewServer.get(this).addWindow(this);
        ButterKnife.bind(this);
        mGLSurfaceView = (GLSurfaceView) findViewById(R.id.glSurfaceView);
        mGLSurfaceView.setRenderer(new MyRenderer());
//        new Car();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //当Activity暂停时，告诉GLSurfaceView也停止渲染，并释放资源。
        mGLSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //当Activity恢复时，告诉GLSurfaceView加载资源，继续渲染
        mGLSurfaceView.onResume();
    }

    @OnClick({R.id.tv_title, R.id.btn_bethes, R.id.btn_scroller, R.id.btn_credit, R.id.btn_screen_orientation,
            R.id.btn_test_aa,R.id.btn_test_apache,R.id.btn_test_asyncTask,R.id.btn_test_aRouter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                Util.startOtherActivity(this, TextRxJavaActivity.class);
                break;
            case R.id.btn_bethes:
                Util.startOtherActivity(this, BethesthActivity.class);
                break;
            case R.id.btn_scroller:
                Util.startOtherActivity(this, Scroller1Activity.class);
                break;
            case R.id.btn_credit:
                Util.startOtherActivity(this, CreditScoreActivity.class);
                break;
            case R.id.btn_screen_orientation:
                Util.startOtherActivity(this, OrientationActivity.class);
                break;
            case R.id.btn_test_aa:
                Util.startOtherActivity(this, TestAAActivity.class);
                break;
            case R.id.btn_test_apache:
                Util.startOtherActivity(this, ApacheTestActivity.class);
                break;
            case R.id.btn_test_asyncTask:
                Util.startOtherActivity(this, AsyncTaskActivity.class);
                break;
            case R.id.btn_test_aRouter:
                // 2. 跳转并携带参数
                TestPar testPar = new TestPar("Bertking");
                ARouter.getInstance().build("/activity/" + ARounterTestActivity.class.getSimpleName())
//                        .withObject("key3", testPar)
                        .navigation();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).setFocusedWindow(this);
    }

    /**
     * https://developer.android.com/reference/android/opengl/GLSurfaceView.Renderer.html
     * Renderer渲染器
     * 负责通知 OpenGL 去渲染 每一帧
     */
    class MyRenderer implements GLSurfaceView.Renderer {

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        }
    }

}

