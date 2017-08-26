package bertking.com.openglproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bertking.com.openglproject.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 参考链接：http://www.jianshu.com/p/128e662906af
 */
public class RxJavaOperatorActivity extends AppCompatActivity {
    public static final String TAG = RxJavaOperatorActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_operator);


        mapOperator();
        //延时是耗时操作
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                flatMapOperator();
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                concatMapOperator();
            }
        });

        zipOperator();
    }

    /**
     * 使用map()操作符，将会把上游的每个事件给转换成另一个事件。
     */
    private void mapOperator() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        });
        /**
         * 这里注意当我们使用map()时，会产生一个新的Observable对象。
         */
        Observable<String> stringObservable = observable.map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                String string = null;
                switch (integer) {
                    case 1:
                        string = "One";
                        break;
                    case 2:
                        string = "Two";
                        break;
                    case 3:
                        string = "Three";
                        break;
                    default:
                        string = "This is a result" + String.valueOf(integer);
                        break;
                }
                return string;
            }
        });

        stringObservable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String string) throws Exception {
                Log.d(TAG,"result:----"+string);
            }
        });
    }

    /**
     * 使用flatMap()操作符，将会把上游的每个事件给转换成多个事件来让下游接收，但是这样做是不保证顺序的。
     */
    private void flatMapOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<String>(10);
                for (int i = 0; i < 3; i++) {
                    list.add("I am value:"+integer);
                }
                return Observable.fromIterable(list).delay(1, TimeUnit.SECONDS);
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object object) throws Exception {
                Log.d(TAG,"flatMap ----"+String.valueOf(object));
            }
        });

    }

    /**
     * concatMap()功能和flatMap()一样，但是 concatMap可以保证是有序的。
     */
    private void concatMapOperator(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<String>(10);
                for (int i = 0; i < 3; i++) {
                    list.add("concatMap---- I am value:"+integer);
                }
                return Observable.fromIterable(list).delay(1,TimeUnit.SECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String string) throws Exception {
                Log.d(TAG,string);
            }
        });
    }

    /************
     * 通过上面我们会发现，flatMap()和 concatMap()都是将一个上游的事件给分解为多个，让下游来接收。
     * 那么按照事物发展的规律，必然在“合并”的操作。你没有猜错，下面我们来看一下zip操作符。
     *
     * ***************/

    /**
     * zip()操作符，将多个上游事件合并为一个事件，让下游接收
     * 以事件少的那个Observable为准。
     * 由于每个Obserable的速度不一致，将会带来很多问题
     * http://www.jianshu.com/p/bb58571cdb64
     */
    private void zipOperator(){
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
            }
        });

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("One");
                emitter.onNext("Two");
                emitter.onNext("Three");
            }
        });

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(@NonNull Integer integer, @NonNull String string) throws Exception {
                return integer + string;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String string) {
                Log.d(TAG,"zip ---- onNext: "+string);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
