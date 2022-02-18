package com.socket.viewpagerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

/**
 * 支持手势缩放、平移和双击还原的FrameLayout
 */
public class ZoomTranslateDoubleTapController extends FrameLayout implements ScaleGestureDetector.OnScaleGestureListener {

    private ScaleGestureDetector scaleGestureDetector;//手势缩放
    /**
     * MSCALE_X  MSKEW_X    MTRANS_X
     * MKEW_Y    MSCALE_Y   MTRANS_Y
     * MPERSP_0  MPERSP_1   MPERSP_2
     */
    private Matrix mMatrix;//缩放矩阵
    private float maxScale = 4.0f;//最大缩放到原图的四倍
    private float minScale = 0.5f;//最小缩放到原图的0.5倍
    private float lastX=0, lastY=0;
    private int lastPointerCount;
    private GestureDetector gestureDetector;

    public ZoomTranslateDoubleTapController(Context context) {
        super(context);
        init(context);
    }

    public ZoomTranslateDoubleTapController(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomTranslateDoubleTapController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //初始化参数
    private void init(Context context) {
        scaleGestureDetector = new ScaleGestureDetector(new WeakReference<Context>(context).get(),
                new WeakReference<ZoomTranslateDoubleTapController>(this).get());
        mMatrix = new Matrix();
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {//双击图片还原
                float x = e.getX();
                float y = e.getY();
                if (getPreScale() != 1.0f) {
                    ZoomTranslateDoubleTapController.this.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            mMatrix.reset();
                            invalidate();
                            makeDrawableCenter();
                        }
                    },16);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {//OnScaleGestureListener里的方法
        //获取本次的缩放值
        float scale = detector.getScaleFactor();
        Log.i("zhangdi", "scaleFactor = "+scale);
        float preScale = getPreScale();
        Log.i("zhangdi", "preScale = "+preScale);
        if (preScale * scale < maxScale &&
                preScale * scale > minScale) {//preScale * scale可以计算出此次缩放执行的话，缩放值是多少

            //detector.getFocusX()缩放手势中心的x坐标，detector.getFocusY()y坐标
//            mMatrix.postScale(scale, scale, detector.getFocusX(), detector.getFocusY());
            mMatrix.postScale(scale, scale, getWidth()/2, getHeight()/2);
            invalidate();
            makeDrawableCenter();
        }
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {//OnScaleGestureListener里的方法，缩放开始
        return true;//必须返回true才有效果
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {//OnScaleGestureListener里的方法，缩放结束
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        scaleGestureDetector.onTouchEvent(event);

        if (getPreScale() > 1) {
            float x=0, y=0;
            final int pointerCount = event.getPointerCount();
            for (int i=0; i<pointerCount; i++) {
                x += event.getX(i);
                y += event.getY(i);
            }
            x = x/pointerCount;
            y = y/pointerCount;
            if (pointerCount != lastPointerCount)
            {
                lastX = x;
                lastY = y;
            }
            Log.i("zhangdi", "pointCount: "+pointerCount+", lastPointCount: "+lastPointerCount);
            lastPointerCount = pointerCount;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = x;
                    lastY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float delX = x - lastX;
                    float delY = y - lastY;

                    RectF rectF = getMatrixRectF();
                    if ((rectF.left >= 0 && delX > 0) || (rectF.right <= getWidth() && delX < 0)) {
                        delX = 0;
                    }
                    if ((rectF.top >= 0 && delY > 0) || (rectF.bottom <= getHeight() && delY < 0)) {
                        delY = 0;
                    }

                    mMatrix.postTranslate(delX, delY);
                    invalidate();
                    lastX = x;
                    lastY = y;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
        }
        return true;
    }

    //获取目前一共缩放了多少
    private float getPreScale() {
        float[] matrix = new float[9];
        mMatrix.getValues(matrix);
        return matrix[Matrix.MSCALE_X];
    }

    private RectF getMatrixRectF() {

        RectF rect = new RectF();
        Drawable d = new BitmapDrawable(getResources(), getDrawingCache());
        if (d != null) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            Log.i("zhangdi", "bitmapWidth = "+d.getIntrinsicWidth()+", bitmapHeight = "+d.getIntrinsicHeight());
            mMatrix.mapRect(rect);
            Log.i("zhangdi", "matrixWidth = "+rect.width()+", matrixHeight = "+rect.height());
            Log.i("zhangdi", "bmLeft: "+rect.left+" bmRight: "+rect.right+" bmTop: "+rect.top+" bmBottom: "+rect.bottom);
        }
        return rect;
    }

    //缩小的时候让图片居中
    private void makeDrawableCenter() {

        RectF rect = getMatrixRectF();

        int width = getWidth();
        int height = getHeight();

        float dx=0, dy=0;

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width)
        {
            if (rect.left > 0)
            {
                dx = -rect.left;
            }
            if (rect.right < width)
            {
                dx = width - rect.right;
            }
        }
        if (rect.height() >= height)
        {
            if (rect.top > 0)
            {
                dy = -rect.top;
            }
            if (rect.bottom < height)
            {
                dy = height - rect.bottom;
            }
        }

        if (rect.width() <= width) {
            dx = width/2 - (rect.right - rect.width()/2);//控件中心点横坐标减去图片中心点横坐标为X方向应移动距离
        }
        if (rect.height() <= height) {
            dy = height/2 - (rect.bottom - rect.height()/2);
        }
        Log.i("zhangdi", "dx = "+dx+", dy = "+dy);

        if (dx != 0 || dy != 0) {
            mMatrix.postTranslate(dx, dy);
            invalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setDrawingCacheEnabled(false);
        scaleGestureDetector = null;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setDrawingCacheEnabled(true);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        View view = getChildAt(0);
        canvas.save();
        canvas.concat(mMatrix);
        view.draw(canvas);
        canvas.restore();
    }
}
