package com.socket.viewpagerdemo;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import pl.droidsonroids.gif.GifImageView;


public class TabFragment extends Fragment {
    private GestureDetector mGestureDetector;

    public static TabFragment newInstance(String label) {
        Bundle args = new Bundle();
        args.putString("label", label);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    private ImageView imageView;
    private ImageView image5;
    private ImageView image_e;
    private ImageView image_f;
    private RelativeLayout red;
    private RelativeLayout gray;
    private PointView point;
    private PointView point1;
    private LinearLayout change;
    private LinearLayout long_image;
    private GifImageView gif1;
    private GifImageView gif2;


    private ImageView image_new;
    private ImageView image_news;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();
        final String label = getArguments().getString("label");
        imageView = getView().findViewById(R.id.iv_content);
        image5 = getView().findViewById(R.id.image5);
        gif1 = getView().findViewById(R.id.gif1);
        gif2 = getView().findViewById(R.id.gif2);
        image_e = getView().findViewById(R.id.image_e);
        image_f = getView().findViewById(R.id.image_f);
        long_image = getView().findViewById(R.id.long_image);
        change = getView().findViewById(R.id.change);
        point = getView().findViewById(R.id.point);
        point1 = getView().findViewById(R.id.point1);
        red = getView().findViewById(R.id.red);
        gray = getView().findViewById(R.id.gray);
        image_new = getView().findViewById(R.id.image_new);
        image_news = getView().findViewById(R.id.image_news);

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                intent.putExtra("gif", "3");
                startActivity(intent);

            }
        });
        gif1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                intent.putExtra("gif", "1");
                startActivity(intent);
            }
        });
        gif2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                intent.putExtra("gif", "2");
                startActivity(intent);
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_f.setVisibility(View.VISIBLE);
                image_e.setVisibility(View.GONE);
                gray.setBackground(getResources().getDrawable(R.drawable.gray));
                red.setBackground(getResources().getDrawable(R.drawable.red));
            }
        });
        gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_f.setVisibility(View.GONE);
                image_e.setVisibility(View.VISIBLE);
                gray.setBackground(getResources().getDrawable(R.drawable.red));
                red.setBackground(getResources().getDrawable(R.drawable.gray));
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("可能原因".equals(label)) {

                } else {
                    Intent intent = new Intent(getActivity(), TestActivity.class);
                    intent.putExtra("str", label);
                    startActivity(intent);
                }

            }
        });
        image_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                intent.putExtra("change", "1");
                startActivity(intent);
            }
        });
        image_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                intent.putExtra("change", "2");
                startActivity(intent);
            }
        });
        if ("故障现象".equals(label)) {
            imageView.setVisibility(View.VISIBLE);
            long_image.setVisibility(View.GONE);
            change.setVisibility(View.GONE);
            Drawable drawable = getResources().getDrawable(R.drawable.a);
            image_new.setImageDrawable(drawable);
            image_news.setVisibility(View.GONE);
            image_new.setVisibility(View.VISIBLE);
        } else if ("故障机理".equals(label)) {
            imageView.setVisibility(View.VISIBLE);
            long_image.setVisibility(View.GONE);
            change.setVisibility(View.GONE);
            Drawable drawable = getResources().getDrawable(R.drawable.b);
            image_new.setImageDrawable(drawable);
            image_news.setVisibility(View.GONE);
            image_new.setVisibility(View.VISIBLE);
        } else if ("接线图".equals(label)) {
            imageView.setVisibility(View.VISIBLE);
            long_image.setVisibility(View.GONE);
            change.setVisibility(View.GONE);
            Drawable drawable = getResources().getDrawable(R.drawable.c);
            image_new.setImageDrawable(drawable);
            image_news.setVisibility(View.GONE);
            image_new.setVisibility(View.VISIBLE);
        } else if ("接线插图".equals(label)) {
            imageView.setVisibility(View.GONE);
            long_image.setVisibility(View.GONE);
            change.setVisibility(View.VISIBLE);
            Drawable drawable = getResources().getDrawable(R.drawable.d);
            image_new.setImageDrawable(drawable);
            image_news.setVisibility(View.GONE);
            image_new.setVisibility(View.VISIBLE);

        } else if ("可能原因".equals(label)) {
            imageView.setVisibility(View.VISIBLE);
            long_image.setVisibility(View.GONE);
            change.setVisibility(View.GONE);
            Drawable drawable = getResources().getDrawable(R.drawable.e);
            image_new.setImageDrawable(drawable);
            image_news.setVisibility(View.GONE);
            image_new.setVisibility(View.VISIBLE);
        } else if ("故障检测".equals(label)) {
            imageView.setVisibility(View.GONE);
            long_image.setVisibility(View.VISIBLE);
            change.setVisibility(View.GONE);
            Drawable drawable = getResources().getDrawable(R.drawable.f);
            image_new.setImageDrawable(drawable);
            image_news.setImageDrawable(drawable);
            image_news.setVisibility(View.VISIBLE);
            image_new.setVisibility(View.GONE);
        }
//        text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), label, Toast.LENGTH_LONG).show();
//            }
//        });
//        text.setText(label);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private final class TouchListener implements View.OnTouchListener {


        /**
         * 记录是拖拉照片模式还是放大缩小照片模式
         */
        private int mode = 0;// 初始状态
        /**
         * 拖拉照片模式
         */
        private static final int MODE_DRAG = 1;
        /**
         * 放大缩小照片模式
         */
        private static final int MODE_ZOOM = 2;

        /**
         * 用于记录开始时候的坐标位置
         */
        private PointF startPoint = new PointF();
        /**
         * 用于记录拖拉图片移动的坐标位置
         */
        private Matrix matrix = new Matrix();
        /**
         * 用于记录图片要进行拖拉时候的坐标位置
         */
        private Matrix currentMatrix = new Matrix();

        /**
         * 两个手指的开始距离
         */
        private float startDis;
        /**
         * 两个手指的中间点
         */
        private PointF midPoint;


        @Override
        public boolean onTouch(View v, MotionEvent event) {

            /** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                // 手指压下屏幕
                case MotionEvent.ACTION_DOWN:

                    mode = MODE_DRAG;
                    // 记录ImageView当前的移动位置
                    currentMatrix.set(imageView.getImageMatrix());
                    startPoint.set(event.getX(), event.getY());
                    break;
                // 手指在屏幕上移动，改事件会被不断触发
                case MotionEvent.ACTION_MOVE:
                    // 拖拉图片
                    if (mode == MODE_DRAG) {
                        float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
                        float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
                        // 在没有移动之前的位置上进行移动
                        matrix.set(currentMatrix);
                        matrix.postTranslate(dx, dy);
                    }
                    // 放大缩小图片
                    else if (mode == MODE_ZOOM) {
                        float endDis = distance(event);// 结束距离
                        if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                            float scale = endDis / startDis;// 得到缩放倍数
                            matrix.set(currentMatrix);
                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                        }
                    }

                    break;

                // 手指离开屏幕
                case MotionEvent.ACTION_UP:
                    // 当触点离开屏幕，但是屏幕上还有触点(手指)
                case MotionEvent.ACTION_POINTER_UP:
                    mode = 0;
                    break;
                // 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = MODE_ZOOM;
                    /** 计算两个手指间的距离 */
                    startDis = distance(event);
                    /** 计算两个手指间的中间点 */
                    if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                        midPoint = mid(event);
                        //记录当前ImageView的缩放倍数
                        currentMatrix.set(imageView.getImageMatrix());
                    }
                    break;
            }
            imageView.setImageMatrix(matrix);
            return true;

        }

        /**
         * 计算两个手指间的距离
         */
        private float distance(MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            /** 使用勾股定理返回两点之间的距离 */
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * 计算两个手指间的中间点
         */
        private PointF mid(MotionEvent event) {
            float midX = (event.getX(1) + event.getX(0)) / 2;
            float midY = (event.getY(1) + event.getY(0)) / 2;
            return new PointF(midX, midY);
        }


    }
}

