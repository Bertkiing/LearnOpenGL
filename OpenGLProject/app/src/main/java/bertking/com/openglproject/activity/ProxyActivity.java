package bertking.com.openglproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Proxy;

import bertking.com.openglproject.R;
import bertking.com.openglproject.bean.ClassA;
import bertking.com.openglproject.bean.Operate;
import bertking.com.openglproject.bean.OperateImpl;
import bertking.com.openglproject.bean.ProxyClassB;
import bertking.com.openglproject.bean.TimingInvocationHandler;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 静态代理 与动态代理
 * 参考链接：http://a.codekk.com/detail/Android/Caij/%E5%85%AC%E5%85%B1%E6%8A%80%E6%9C%AF%E7%82%B9%E4%B9%8B%20Java%20%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86
 */
public class ProxyActivity extends AppCompatActivity {

    @BindView(R.id.tv_proxy)
    TextView mTvProxy;
    @BindView(R.id.tv_static_proxy)
    TextView mTvStaticProxy;
    @BindView(R.id.tv_dynatic_proxy)
    TextView mTvDynaticProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        ButterKnife.bind(this);

        new ProxyClassB(new ClassA()).operate1();
        new ProxyClassB(new ClassA()).operate2();

        // create proxy instance
        TimingInvocationHandler timingInvocationHandler = new TimingInvocationHandler(new OperateImpl());
        Operate operate = (Operate) (Proxy.newProxyInstance(Operate.class.getClassLoader(), new Class[]{Operate.class},
                timingInvocationHandler));

        // call method of proxy instance
        operate.operateMethod1();
        System.out.println();
        operate.operateMethod2();
        System.out.println();
        operate.operateMethod3();

        initView();
    }

    private void initView() {
        String proxy = "在某些情况下，我们不希望或是不能直接访问对象 A，而是通过访问一个中介对象 B，由 B 去访问 A 达成目的，这种方式我们就称为代理\n 1.这里对象 A 所属类我们称为委托类，也称为被代理类;\n 2.对象 B 所属类称为代理类";
        mTvProxy.setText(proxy);
        String staticProxy = "代理类在程序运行前已经存在的代理方式称为静态代理。";
        mTvStaticProxy.setText(staticProxy);
        String dynamicProxy = "代理类在程序运行前不存在、运行时由程序动态生成的代理方式称为动态代理。\n Java 提供了动态代理的实现方式，可以在运行时刻动态生成代理类";
        mTvDynaticProxy.setText(dynamicProxy);

    }

    @OnClick({R.id.tv_proxy, R.id.tv_static_proxy, R.id.tv_dynatic_proxy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_proxy:
                break;
            case R.id.tv_static_proxy:
                break;
            case R.id.tv_dynatic_proxy:
                break;
        }
    }
    /**
     代理优点有：
     隐藏委托类的实现
     解耦，不改变委托类代码情况下做一些额外处理，比如添加初始判断及其他公共操作
     根据程序运行前代理类是否已经存在，可以将代理分为静态代理和动态代理。
     */
}
