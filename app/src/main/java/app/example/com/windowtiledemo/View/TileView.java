package app.example.com.windowtiledemo.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;

import java.sql.Time;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.spec.DESedeKeySpec;

import app.example.com.windowtiledemo.R;
import app.example.com.windowtiledemo.Utils.DestiyUtil;
import app.example.com.windowtiledemo.Utils.FastBlurUtil;

/**
 * Created by Administrator on 2018/3/24.
 */

public class TileView extends android.support.v7.widget.AppCompatImageView {
    Context mContext;
    private Paint P_Text, P_Rect;
    private int VIEW_W, VIEW_H;
    private Matrix M_Rect;
    private int DP_VIEW_W = 200, DP_VIEW_H = 200, SP_Text = 16;
    private float DP_Rect_H = 0;
    private int DP_RECT_H_MAX = 80;
    private int DP_Text_Left_Margin = 30, DP_Text_Bottom_Margin = 20;
    private Rect mRect;
    private int angle = 0;
    private int ANGLE_MAX = -15;
    private float COS30 = (float) 0.16;
    private ValueAnimator VA_DOWN, VA_UP;
    @LayoutRes
    private int Rect_BG;//角上提示背景图
    private long Anim_Duration = 1000;
    private int SLEEP_TIME_RANGE = 4000;
    private int SLEEP_TIME_MIN = 2000;
    private Random random;
    private Timer timer;
    private TimerTask timerTask;
    private Boolean isBeTouching = false;
    private Bitmap bitmap;


    public TileView(Context context) {
        super(context);
        init(context);
    }

    public TileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int Spec_W = MeasureSpec.makeMeasureSpec(DestiyUtil.dip2px(mContext, DP_VIEW_W), MeasureSpec.EXACTLY);
        int Spec_H = MeasureSpec.makeMeasureSpec(DestiyUtil.dip2px(mContext, DP_VIEW_H), MeasureSpec.EXACTLY);
        super.onMeasure(Spec_W, Spec_H);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initAngle();
        canvas.rotate(angle, 0, DestiyUtil.dip2px(mContext, DP_Rect_H));
        super.onDraw(canvas);
        initBitmap();
        DrawRect(canvas);
        DrawText(canvas);
    }

    private void init(Context mContext) {
        this.mContext = mContext;
        P_Text = new Paint();
        P_Rect = new Paint();
        random = new Random();

        P_Text.setColor(Color.BLACK);
        P_Text.setStyle(Paint.Style.FILL_AND_STROKE);
        P_Text.setAntiAlias(true);
        P_Text.setTextSize(DestiyUtil.dip2px(mContext, SP_Text));

        P_Rect.setColor(Color.MAGENTA);
        P_Rect.setStyle(Paint.Style.FILL);
        P_Rect.setAntiAlias(true);


        mRect = new Rect();
        timer = new Timer();
        initSleep();
    }

    private void initBitmap() {
        if (bitmap != null) {
            return;
        }
        this.buildDrawingCache(true);
        this.buildDrawingCache();
        bitmap = this.getDrawingCache();
        this.setDrawingCacheEnabled(false);
        bitmap = FastBlurUtil.doBlur(bitmap, 10, true);
    }

    private void initAnimUP(float distance) {
        VA_UP = ValueAnimator.ofFloat(distance, 0);
        VA_UP.setDuration(Anim_Duration);
        VA_UP.setInterpolator(new BounceInterpolator());
    }

    private int getSleepTime() {
        int time = SLEEP_TIME_MIN + random.nextInt(SLEEP_TIME_RANGE);
        return time;
    }


    private void initAnimDown(float distance) {
        VA_DOWN = ValueAnimator.ofFloat(DP_Rect_H, distance);
        VA_DOWN.setDuration(Anim_Duration);
        VA_DOWN.setInterpolator(new BounceInterpolator());
    }


    private void initSleep() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (DP_Rect_H > 0) {
                    Looper.prepare();
                    if (!isBeTouching) {
                        startUpAnim();
                    }
                    timer.cancel();
                    timerTask.cancel();
                    timer = new Timer();
                    initSleep();
                    Looper.loop();

                } else {
                    Looper.prepare();
                    if (!isBeTouching) {
                        startDownAnim();
                    }
                    timer.cancel();
                    timerTask.cancel();
                    timer = new Timer();
                    initSleep();
                    Looper.loop();
                }
            }
        };
        timer.schedule(timerTask, getSleepTime());
    }


    private void DrawText(Canvas canvas) {
        canvas.drawText("测试测试", 0 + DestiyUtil.dip2px(mContext, DP_Text_Left_Margin),
                DestiyUtil.dip2px(mContext, DP_Rect_H - DP_Text_Bottom_Margin), P_Text);
    }

    private void DrawRect(Canvas canvas) {
        mRect.set(0, 0, DestiyUtil.dip2px(mContext, DP_VIEW_W / COS30), DestiyUtil.dip2px(mContext, DP_Rect_H));
        canvas.drawRect(mRect, P_Rect);
        canvas.clipRect(mRect);
        canvas.rotate(-angle, 0, DestiyUtil.dip2px(mContext, DP_Rect_H));
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.rotate(angle, 0, DestiyUtil.dip2px(mContext, DP_Rect_H));
    }

    private void startDownAnim() {
        if (VA_UP != null && VA_UP.isRunning()) {
            VA_UP.cancel();
        } else if (VA_DOWN != null && VA_DOWN.isRunning()) {
            return;
        }
        initAnimDown(DP_RECT_H_MAX - DP_Rect_H);
        VA_DOWN.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                DP_Rect_H = (float) VA_DOWN.getAnimatedValue();
                postInvalidate();
            }
        });
        VA_DOWN.start();

    }

    private void startUpAnim() {
        if (VA_DOWN != null && VA_DOWN.isRunning()) {
            VA_DOWN.cancel();
        } else if (VA_UP != null && VA_UP.isRunning()) {
            return;
        }
        initAnimUP(DP_Rect_H);
        VA_UP.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                DP_Rect_H = (float) VA_UP.getAnimatedValue();
                postInvalidate();
            }
        });
        VA_UP.start();
    }


    private void initAngle() {
        float radio = DP_Rect_H / DP_RECT_H_MAX;
        angle = (int) (ANGLE_MAX * radio);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startDownAnim();
                isBeTouching = true;
                break;
            case MotionEvent.ACTION_UP:
                startUpAnim();
                isBeTouching = false;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                isBeTouching = false;
                break;
        }
        return true;
    }


}
