package bertking.com.openglproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import bertking.com.openglproject.util.Util;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class TextRxJavaActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = TextRxJavaActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_rx_java);
        findViewById(R.id.tv_thread).setOnClickListener(this);
        chainFunc();
        unChainFunc();

    }

    /**
     * ObservableEmitter : 其中Emitter是发射器的意思。他就是用来发出事件的，可以理解为：水龙头
     * 可以发出的事件：
     * onNext(T value);
     * onError(Throwable;
     * onComplete();
     *
     * 尤其要注意：发射事件需满足一定的规则：
     * 1.上游可以发送无数个onNext()事件，下游也可以接收无数个onNext()事件；
     * 2.当上游发送onComplete() or onNext()事件之后，就相当于关闭了水龙头，即使上游(可以)再发送事件，下游收到Complete之后就无法接收后续事件；
     * 3.上游可以不发送onComplete() or onError()事件；
     * 4.关键：onComplete 和 on Error不能共存且必须唯一（唯一且互斥）。
     * 对于第4点：发送多个onComplete()不会到这程序奔溃，但是发送多个onError()将会导致程序的奔溃Crash.
     *
     * 参考链接：http://www.jianshu.com/p/464fa025229e
     */
    private void unChainFunc() {
        //创建一个 被观察者 or 水管的上游
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                /***
                 * 通过测试发现：这个subscribe在建立连接后，只执行一次，只是不停的发送事件。
                 * 在我们判断是否disposed时候，必须跟发送的事件顺序相关联。
                 */
               Log.d(TAG,"........"+emitter.isDisposed());

                Log.d(TAG,"emitter:"+1);
                emitter.onNext("One");
                if(emitter.isDisposed()){
                    Log.d(TAG,"不发送事件啦");
                    return;
                }
                //上游也可以根据是否disposed来停止发送事件；
                emitter.onNext("Two");
                Log.d(TAG,"emitter:"+2);
//                emitter.onError(new Throwable("Error"));
//                emitter.onError(new Throwable("Error 2")); // 不能发送多个Error，将导致Crash; io.reactivex.exceptions.UndeliverableException:
                emitter.onComplete();
                emitter.onNext("Three");
            }
        });

        // 创建一个 观察者 or 水管的下游
        Observer<String> observer =  new Observer<String>() {
            private Disposable mDisposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG,"onSubscribe:"+d.toString());
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull String string) {
                Log.d(TAG,"OnNext:"+string);
                if(string.equals("One")){
                    mDisposable.dispose();
                    Log.d(TAG,"isDisposed:"+mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete");
            }
        };

        //将 两者(被观察者and 观察者)建立连接
        observable.subscribe(observer);
    }

    /**
     * Disposable:一次性的
     * 在RxJava中，可以理解卫调用它的dispose()方法，把两根管道切断，从而导致下游收不到事件。
     * 注意：该操作并不影响上游，依然会发送事件
     */
    private void chainFunc() {
        //链式调用
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "subscribe...");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, " next" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete:...");
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.tv_thread:
                Util.startOtherActivity(this,RxjavaThreadActivity.class);
                break;
        }
    }
}
