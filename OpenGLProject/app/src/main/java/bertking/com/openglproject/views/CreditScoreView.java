package bertking.com.openglproject.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import bertking.com.openglproject.util.LogUtil;
import bertking.com.openglproject.util.ScreenUtil;

/**
 * Created by king on 2017/8/30.
 * 项目名称:OpenGLProject
 * 描述:
 * 参考自：http://blog.csdn.net/kong_gu_you_lan/article/details/52904064
 */

public class CreditScoreView extends View {

    //数据个数
    private int dataCount = 5;
    //每个角的弧度
    private float radian = (float) (Math.PI * 2 / dataCount);

    //信誉图半径
    private float mRadius;
    //中心点X坐标
    private int mCenterX;
    //中心点Y坐标
    private int mCenterY;

    //各维度标题
    private String[] titles = {"履约能力", "信用历史", "人脉关系", "行为偏好", "身份特质"};

    //各维度分值
    private float[] data = {170, 180, 160, 170, 180};
    //数据最大值
    private float maxValue = 190;
    //雷达图与标题的间距
    private int radarMargin = ScreenUtil.dip2px(getContext(), 15);

    // 多边形外围的画笔
    private Paint mMainPaint;


    /**
     * 对于自定义View，最好初始化在最后的一个构造方法中，否则将会导致空指针异常：
     * 我们可以将构造方法的super 替换为 this ，保证其构造方法的正确执行，然后将init操作放在最后被调用的构造方法。
     * Java.Lang.NullPointerException: Attempt to invoke virtual method 'long android.graphics.Paint.getNativeInstance()' on a null object reference
     * @param context
     */
    public CreditScoreView(Context context) {
        this(context, null);
        LogUtil.d("-------构造方法 1----------");
    }

    public CreditScoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        LogUtil.d("-------构造方法 2----------");
    }

    public CreditScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LogUtil.d("-------构造方法 3----------");
        init();
    }

    private void init() {
        mMainPaint = new Paint();
        mMainPaint.setColor(Color.GREEN);
        mMainPaint.setAntiAlias(true);
        mMainPaint.setStyle(Paint.Style.STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        drawPolygon(canvas);
        drawLines(canvas);
        super.onDraw(canvas);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        //雷达图半径
        mRadius = Math.max(w, h) / 4.0f;
        //中心坐标
        mCenterX = w / 2;
        mCenterY = h / 2;

        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < dataCount; i++) {
            if (i == 0) {
                path.moveTo(getPoint(i).x, getPoint(i).y);
            } else {
                path.lineTo(getPoint(i).x, getPoint(i).y);
            }
        }

        //闭合路径
        path.close();
        if(canvas!=null && path != null && mMainPaint != null){
            canvas.drawPath(path, mMainPaint);

        }
    }

    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < dataCount; i++) {
            path.reset();
            path.moveTo(mCenterX, mCenterY);
            path.lineTo(getPoint(i).x, getPoint(i).y);
            canvas.drawPath(path, mMainPaint);
        }
    }

    private Point getPoint(int position) {
        return getPoint(position, 0, 1);
    }

    private Point getPoint(int position, int radarMargin, float percent) {
        int x = 0;
        int y = 0;
        float radiusAndMargin = mRadius + radarMargin;

        if (position == 0) {
            x = (int) ((mCenterX + radiusAndMargin * Math.sin(radian)) * percent);
            y = (int) ((mCenterY - radiusAndMargin * Math.cos(radian)) * percent);
        } else if (position == 1) {
            x = (int) ((mCenterX + radiusAndMargin * Math.sin(radian/2)) * percent);
            y = (int) ((mCenterY + radiusAndMargin * Math.cos(radian/2)) * percent);
        } else if (position == 2) {
            x = (int) ((mCenterX - radiusAndMargin * Math.sin(radian/2)) * percent);
            y = (int) ((mCenterY + radiusAndMargin * Math.cos(radian/2)) * percent);
        } else if (position == 3) {
            x = (int) ((mCenterX - radiusAndMargin * Math.sin(radian)) * percent);
            y = (int) ((mCenterY - radiusAndMargin * Math.cos(radian)) * percent);
        } else {
            x = mCenterX;
            y = (int) (mCenterY - radiusAndMargin * percent);
        }

        return new Point(x, y);
    }


}
