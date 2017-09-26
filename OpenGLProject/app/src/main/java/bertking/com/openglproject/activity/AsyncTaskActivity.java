package bertking.com.openglproject.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import bertking.com.openglproject.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AsyncTaskActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        ButterKnife.bind(this);
        mProgressDialog = new ProgressDialog(this);
    }

    @OnClick(R.id.tv_title)
    public void onClick() {
        new MyAsyncTask().execute();
    }





    class MyAsyncTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(AsyncTaskActivity.this, "AsyncTask onPreExecute()", Toast.LENGTH_SHORT).show();
            mProgressDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                while (true) {
                    int downloadPercent = doDownload();
                    publishProgress(downloadPercent);
                    if (downloadPercent >= 100) {
                        break;
                    }
                }
            } catch (Exception e) {
                return 0;
            }
            return 100;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressDialog.setMessage("当前下载进度：" + values[0] + "%");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            mProgressDialog.dismiss();
            if (integer == 100) {
                mTvTitle.setText(String.valueOf(integer));
                Toast.makeText(AsyncTaskActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AsyncTaskActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
    int i = 0;
    private int doDownload() {
        while (true){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    i++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            if(i >= 100){
                break;
            }
        }
        return i;
    }
}
