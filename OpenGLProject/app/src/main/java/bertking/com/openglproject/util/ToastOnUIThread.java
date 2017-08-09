package bertking.com.openglproject.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;
import android.widget.Toast;

/**
 * Created by king on 2017/8/9.
 * 项目名称:OpenGLProject
 * 描述:
 */

public class ToastOnUIThread {
    public void toast(final Context context, final String string) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 参考链接：http://prasanta-paul.blogspot.kr/2013/09/android-looper-and-toast-from.html
     *
     * @param context
     * @param string
     */
    public void toast2(Context context, String string) {
        Looper.prepare();
        MessageQueue queue = Looper.myQueue();
        queue.addIdleHandler(new MessageQueue.IdleHandler() {
            int mReqCount = 0;

            @Override
            public boolean queueIdle() {
                if (++mReqCount == 2) {
                    Looper.myLooper().quit();
                    return false;
                } else {
                    return true;
                }
            }
        });
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public void toast3(final Activity context, final String string) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
            }
        });
    }

//    1.get UI thread Handler instance and use handler.sendMessage();
//    2.like method 1 ,but call post() method handler.post();
//    3.runOnUiThread();
//    4.view.post()

    public void toast4(final View view, final String string) {
        view.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(view.getContext(), string,Toast.LENGTH_SHORT).show();
            }
        });
    }


}
