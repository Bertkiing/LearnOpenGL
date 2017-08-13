package bertking.com.openglproject.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by king on 2017/8/13.
 * 项目名称:OpenGLProject
 * 描述:
 */

public class Util {
    public static  void startOtherActivity(Context context, Class cls){
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
    }
}
