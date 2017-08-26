package bertking.com.openglproject;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import bertking.com.openglproject.activity.Scroller1Activity;
import bertking.com.openglproject.activity.TextRxJavaActivity;
import bertking.com.openglproject.util.Util;

/**
 * 参考链接：http://blog.csdn.net/chenjie19891104/article/details/6311209
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity".getClass().getSimpleName();

    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGLSurfaceView = (GLSurfaceView) findViewById(R.id.glSurfaceView);
        mGLSurfaceView.setRenderer(new MyRenderer());

        findViewById(R.id.tv_title).setOnClickListener(this);
        findViewById(R.id.btn_bethes).setOnClickListener(this);
        findViewById(R.id.btn_scroller).setOnClickListener(this);



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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tv_title:
                Util.startOtherActivity(this,TextRxJavaActivity.class);
                break;
            case R.id.btn_bethes:
                Util.startOtherActivity(this,BethesthActivity.class);
                break;
            case R.id.btn_scroller:
                Util.startOtherActivity(this,Scroller1Activity.class);
                break;
        }
    }
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
