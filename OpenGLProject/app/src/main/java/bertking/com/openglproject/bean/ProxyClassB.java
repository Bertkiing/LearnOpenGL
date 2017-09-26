package bertking.com.openglproject.bean;

import bertking.com.openglproject.bean.customize_annatation.MethodInfo;

/**
 * Created by Bertking on 2017/9/26.
 * 代理类：
 */

public class ProxyClassB {
    ClassA mClassA;

    public ProxyClassB(ClassA classA) {
        mClassA = classA;
    }

    @MethodInfo(author = "Bertking",
            date = "today",
            version = 2
    )
    public void operate1() {
        mClassA.method1();
    }

    public void operate2() {
        mClassA.method2();
    }

}
