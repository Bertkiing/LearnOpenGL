package bertking.com.openglproject.views.scroller;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import bertking.com.openglproject.util.LogUtil;
import bertking.com.openglproject.util.ScreenUtil;

/**
 * 有弹性的ScrollView
 * 实现下拉弹回和上拉弹回
 * IOS的那种效果
 * 参考链接：http://blog.csdn.net/zhangjg_blog/article/details/19193671
 *对于文中提到的那个Bug，我估计是作者对事件分发理解不到位...
 * @author Bertking
 *
 * 理解Scrool的几个方法
 * http://www.jianshu.com/p/bb64a7bfd8ba
 */
public class ReboundScrollView extends ScrollView {

    private static final String TAG = "ElasticScrollView";

    //移动因子, 是一个百分比, 比如手指移动了100px, 那么View就只移动50px
    //目的是达到一个延迟的效果，调用contentView的layout方法对他重新布局
    private static final float MOVE_FACTOR = 0.5f;

    //松开手指后, 界面回到正常位置需要的动画时间
    private static final int ANIM_TIME = 300;

    //ScrollView的子View， 也是ScrollView的唯一一个子View
    private View contentView;

    //手指按下时的Y值, 用于在移动时计算移动距离
    //如果按下时不能上拉和下拉， 会在手指移动时更新为当前手指的Y值
    private float startY;

    //用于记录正常的布局位置
    private Rect originalRect = new Rect();

    //手指按下时记录是否可以继续下拉
    private boolean canPullDown = false;

    //手指按下时记录是否可以继续上拉
    private boolean canPullUp = false;

    //在手指滑动的过程中记录是否移动了布局
    private boolean isMoved = false;

    /**
     * 是否禁止在该控件之外也允许滑动，即：即使不在该控件的范围内，依然可以拖动该控件，默认false,
     * 因为这样在该控件范围比较小的情况下，会将该控件滑动到看不见的位置。
     */
    private boolean isCanRunningOutsideScrollView = false;

    public ReboundScrollView(Context context) {
        super(context);
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (contentView == null) return;

        //ScrollView中的唯一子控件的位置信息, 这个位置信息在整个控件的生命周期中保持不变
        originalRect.set(contentView.getLeft(), contentView.getTop(), contentView
                .getRight(), contentView.getBottom());
    }

    /**
     * 在触摸事件中, 处理上拉和下拉的逻辑
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (contentView == null) {
            return super.dispatchTouchEvent(ev);
        }
        //TODO: 待定....
        if (isCanRunningOutsideScrollView) {
            //手指是否移动到了当前ScrollView控件之外
            boolean isTouchOutOfScrollView = ev.getY() >= this.getHeight() || ev.getY() < 0;
            if (isTouchOutOfScrollView) {
                if (isMoved) {
                    boundBack();
                    return true;
                }
            }

        }


        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                //判断是否可以上拉和下拉
                canPullDown = isCanPullDown();
                canPullUp = isCanPullUp();

                //记录按下时的Y值
                startY = ev.getY();
                break;

            case MotionEvent.ACTION_UP:

                boundBack();

                break;
            case MotionEvent.ACTION_MOVE:

                //在移动的过程中， 既没有滚动到可以上拉的程度， 也没有滚动到可以下拉的程度
                if (!canPullDown && !canPullUp) {
                    startY = ev.getY();
                    canPullDown = isCanPullDown();
                    canPullUp = isCanPullUp();

                    break;
                }

                //计算手指移动的距离
                float nowY = ev.getY();
                int deltaY = (int) (nowY - startY);

                //是否应该移动布局
                boolean shouldMove =
                        (canPullDown && deltaY > 0)    //可以下拉， 并且手指向下移动
                                || (canPullUp && deltaY < 0)    //可以上拉， 并且手指向上移动
                                || (canPullUp && canPullDown); //既可以上拉也可以下拉（这种情况出现在ScrollView包裹的控件比ScrollView还小）

                if (shouldMove) {
                    //计算偏移量
                    int offset = (int) (deltaY * MOVE_FACTOR);

                    //随着手指的移动而移动布局，调用contentView的layout方法对他重新布局
                    contentView.layout(originalRect.left, originalRect.top + offset,
                            originalRect.right, originalRect.bottom + offset);

                    isMoved = true;  //记录移动了布局
                }

                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    /**
     * 将内容布局移动到原位置
     * 可以在UP事件中调用, 也可以在其他需要的地方调用, 如手指移动到当前ScrollView外时
     */
    private void boundBack() {
        if (!isMoved) return;

        // 开启动画
        TranslateAnimation anim = new TranslateAnimation(0, 0, contentView.getTop(),
                originalRect.top);
        anim.setDuration(ANIM_TIME);

        contentView.startAnimation(anim);

        // 设置回到正常的布局位置
        contentView.layout(originalRect.left, originalRect.top,
                originalRect.right, originalRect.bottom);

        //将标志位设回false
        canPullDown = false;
        canPullUp = false;
        isMoved = false;
    }


    /**
     * 判断是否滚动到顶部
     */
    private boolean isCanPullDown() {
        Log.d(TAG, "------滚动的距离：-------" + getScrollY());
        LogUtil.d("--------内容View的高度：-----" + contentView.getHeight() + ":" + ScreenUtil.dip2px(1050));
        LogUtil.d("------ScrollView的高度-----" + getHeight() + "---屏幕高度:" + ScreenUtil.getScreenHeight());
        return getScrollY() == 0 ||
                contentView.getHeight() < getHeight() + getScrollY();
    }

    /**
     * 判断是否滚动到底部
     */
    private boolean isCanPullUp() {
        return contentView.getHeight() <= getHeight() + getScrollY();
    }

}
