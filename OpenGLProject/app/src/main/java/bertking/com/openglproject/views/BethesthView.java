package bertking.com.openglproject.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bertking on 2017/8/14.
 * 贝塞斯弧线
 */

public class BethesthView extends View {
    private Paint mLinePaint;
    private Context mContext;
    private List<Point> points = new ArrayList<>();
    private Paint mPointPaint;
    private Path mPath;
    private Rect mPointRect;

    public BethesthView(Context context) {
        super(context ,null);
    }

    public BethesthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLinePaint();
        initPointPaint();
        //实例化path ,rect
        mPath = new Path();
        mPointRect = new Rect();
        mContext = context;


        points.add(new Point(10,900));
        points.add(new Point(50,150));
        points.add(new Point(100,100));
        points.add(new Point(200,300));
        points.add(new Point(300,500));
        points.add(new Point(400,650));
        points.add(new Point(450,300));
        points.add(new Point(600,450));
        points.add(new Point(700,200));
        points.add(new Point(750,350));
        points.add(new Point(830,600));
        points.add(new Point(910,1000));
        points.add(new Point(1000,550));

    }

    private void initPointPaint() {
        //初始化点的paint
        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setColor(Color.BLUE);
    }

    private void initLinePaint() {
        //实例化曲线paint
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setAntiAlias(true);
    }

    public BethesthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0, j = points.size() - 1; i < j; i++) {
            Point startPoint = points.get(i);
            Point endPoint = points.get(i + 1);
            //控制点1
            int cpX = (startPoint.x + endPoint.x) >> 1;
            //控制点2
            int cpY1 = startPoint.y, cpY2 = endPoint.y;
            mPath.reset();
            mPath.moveTo(startPoint.x, startPoint.y);
            //连接曲线
            mPath.cubicTo(cpX, cpY1, cpX, cpY2, endPoint.x, endPoint.y);
            canvas.drawPath(mPath, mLinePaint);
            //画点
            canvas.drawCircle(startPoint.x, startPoint.y, 8, mPointPaint);
        }
        Point point = points.get(points.size() - 1);
        canvas.drawCircle(point.x, point.y, 8, mPointPaint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            //处理touch事件
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                if (touchedPoint(x, y)) {
                    Toast.makeText(mContext, "Clicked a Point"+"X:"+x+"Y:"+y, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
        return super.onTouchEvent(event);
    }
    /**
     * @param x
     * @param y
     * @return 是否点击了图表上的点
     */
    private boolean touchedPoint(float x, float y) {
        for (Point point : points) {
            mPointRect.set(point.x - 20, point.y - 20, point.x + 20, point.y + 20);
            if (mPointRect.contains((int) x, (int) y)) {
                return true;
            }
        }
        return false;
    }
}
