package com.socket.viewpagerdemo;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import androidx.appcompat.widget.AppCompatImageView;

import java.lang.ref.WeakReference;

/**
 * 只支持手势缩放的ImageView
 */
public class ZoomOnlyImageView extends AppCompatImageView implements ScaleGestureDetector.OnScaleGestureListener {

    private ScaleGestureDetector scaleGestureDetector;//手势缩放
    /**
     * MSCALE_X  MSKEW_X    MTRANS_X
     * MKEW_Y    MSCALE_Y   MTRANS_Y
     * MPERSP_0  MPERSP_1   MPERSP_2
     */
    private Matrix mMatrix;//缩放矩阵
    private float maxScale = 4.0f;//最大缩放到原图的四倍
    private float minScale = 0.5f;//最小缩放到原图的0.5倍

    public ZoomOnlyImageView(Context context) {
        super(context);
        init(context);
    }

    public ZoomOnlyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomOnlyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //初始化参数
    private void init(Context context) {
        setScaleType(ScaleType.MATRIX);//允许imageview缩放
        scaleGestureDetector = new ScaleGestureDetector(new WeakReference<Context>(context).get(),
                new WeakReference<ZoomOnlyImageView>(this).get());
        mMatrix = new Matrix();
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {//OnScaleGestureListener里的方法
        if (getDrawable() == null) {
            return true;
        }
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
            setImageMatrix(mMatrix);
            makeDrawableCenter();
        }
        return true;
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
        return scaleGestureDetector.onTouchEvent(event);
    }

    //获取目前一共缩放了多少
    private float getPreScale() {
        float[] matrix = new float[9];
        mMatrix.getValues(matrix);
        return matrix[Matrix.MSCALE_X];
    }

    //缩小的时候让图片居中
    private void makeDrawableCenter() {

        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());//设置rect的初始四个角值是图片的四个顶点值
            Log.i("zhangdi", "bitmapWidth = "+d.getIntrinsicWidth()+", bitmapHeight = "+d.getIntrinsicHeight());
            mMatrix.mapRect(rect);//获取通过当前矩阵变换后的四个角值
            Log.i("zhangdi", "matrixWidth = "+rect.width()+", matrixHeight = "+rect.height());
            Log.i("zhangdi", "bmLeft: "+rect.left+" bmRight: "+rect.right+" bmTop: "+rect.top+" bmBottom: "+rect.bottom);
        }

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

        if (rect.width() < width) {
            dx = width/2 - (rect.right - rect.width()/2);//控件中心点横坐标减去图片中心点横坐标为X方向应移动距离
        }
        if (rect.height() < height) {
            dy = height/2 - (rect.bottom - rect.height()/2);
        }
        Log.i("zhangdi", "dx = "+dx+", dy = "+dy);

        if (dx != 0 || dy != 0) {
            mMatrix.postTranslate(dx, dy);
            setImageMatrix(mMatrix);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
        scaleGestureDetector = null;
    }
}
