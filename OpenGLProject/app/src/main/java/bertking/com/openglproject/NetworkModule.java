package bertking.com.openglproject;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by Bertking on 2017/9/26.
 * http://blog.csdn.net/afanyusong/article/details/51276141
 */

@Module
public class NetworkModule {
    @Provides
    @NonNull
    @Singleton
    public OkHttpClient provideOkHttpClient(Cache cache ) {

        OkHttpClient okHttpClient = new OkHttpClient();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? BODY : NONE);

        OkHttpClient newClient = okHttpClient.newBuilder()
                .addInterceptor(httpLoggingInterceptor)
//                .addInterceptor(mashapeKeyInterceptor)
//                .addNetworkInterceptor(new CacheInterceptor())
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        return newClient;
    }
}
