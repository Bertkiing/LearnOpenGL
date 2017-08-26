package bertking.com.openglproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import bertking.com.openglproject.R;
import bertking.com.openglproject.util.Util;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 参考链接：http://www.jianshu.com/p/8818b98c44e2
 */
public class RxjavaThreadActivity extends AppCompatActivity {
    public static final String TAG = RxjavaThreadActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_thread);

        funcSameThread();
        Log.d(TAG,"--------看下面的不同线程--------");
        funcOtherThread();
        Log.d(TAG,"--------不同线程的规则--------");
        funcOtherThread2();

    }

    /**
     * 默认情况下，被观察者和观察者都是工作在同一线程中的；
     * 即：被观察者在哪个线程发事件，观察者就在哪个线程中接收事件；
     */
    private void funcSameThread() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello,Rxjava");
                Log.d(TAG,"被观察者："+Thread.currentThread().getName());
                emitter.onComplete();
            }
        });

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String string) throws Exception {
                Log.d(TAG,"观察者："+Thread.currentThread().getName());
                Log.d(TAG,"onNext:"+string);
            }
        };

        observable.subscribe(consumer);
    }

    /**
     * 通过Rxjava内置的线程调度器来使：子线程发送事件，主线程接收事件；
     */
    private void funcOtherThread(){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("不错");
                Log.d(TAG,"被观察者："+Thread.currentThread().getName());
                emitter.onComplete();
            }
        });

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String string) throws Exception {
                Log.d(TAG,"观察者："+Thread.currentThread().getName());
                Log.d(TAG,"onNext:"+string);
            }
        };

        /**
         * 通过Rxjava内置的线程调度器来使：子线程发送事件，主线程接收事件；
         *
         * subscribeOn:指定的是被观察者事件的线程；
         * observeOn:指定的是观察者接收事件的线程；
         *
         * 规则：多次调用subscribeOn():只有第一次有效，其余的忽略；
         * 多次调用observeOn()是可以的，每调用一次，线程就会切换一次；
         */
        observable.subscribeOn(Schedulers.newThread())//RxNewThreadScheduler-1
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    /**
     * 来看一下线程调度器的规则：
     * 规则：多次调用subscribeOn():只有第一次有效，其余的忽略；
     * 多次调用observeOn()是可以的，每调用一次，线程就会切换一次；
     *
     * 在Rxjava中，已经内置了很多线程相关的选项供我们选择：
     * Schedulers.io()：代表io操作的线程，通常用于网络，读写文件等io密集的操作；
     * Schedulers.computation:代表CPU计算密集型的操作，例如大量的计算；
     * Schedulers.newThread:代表一个常规的新线程；
     * AndroidSchedulers.mainThread()代表Android的主线程。
     *
     *其内部使用线程池在维护，效率比较高
     */
    private void funcOtherThread2() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("不错");
                Log.d(TAG,"被观察者："+Thread.currentThread().getName());
                emitter.onComplete();
            }
        });

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String string) throws Exception {
                Log.d(TAG,"观察者："+Thread.currentThread().getName());
                Log.d(TAG,"onNext:"+string);
            }
        };

        /**
         * 通过Rxjava内置的线程调度器来使：子线程发送事件，主线程接收事件；
         *
         *
         */
        observable.subscribeOn(Schedulers.newThread())//RxNewThreadScheduler-1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(consumer);
    }


    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_operator:
                Util.startOtherActivity(this,RxJavaOperatorActivity.class);
                break;
        }
    }
}
