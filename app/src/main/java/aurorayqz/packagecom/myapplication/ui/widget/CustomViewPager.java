package aurorayqz.packagecom.myapplication.ui.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Aurorayqz on 2017/11/8.
 */

public class CustomViewPager extends ViewPager {


    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    PointF mPointF = new PointF();
    OnSingleTouchListner mOnSingleTouchListner;


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //获取按下的坐标
                mPointF.x = ev.getX();
                mPointF.y = ev.getY();
                if(this.getChildCount() > 1){
                    //通知父控件不进行拦截点击事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(this.getChildCount() > 1){
                    //通知父控件不进行拦截点击事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_UP:
                if(PointF.length(ev.getX() - mPointF.x,ev.getY()-mPointF.y)< (float) 5.0){

                    //单纯的点击
                    onSingleTouch(this);

                }

                break;


        }

        return super.onTouchEvent(ev);
    }

    private void onSingleTouch(View view) {
        if(mOnSingleTouchListner != null){
            mOnSingleTouchListner.onSingleTouch();
        }

    }


    public interface OnSingleTouchListner{
        void onSingleTouch();
    }

    public void setOnSingleTouchListner(OnSingleTouchListner onSingleTouchListner){
        this.mOnSingleTouchListner = onSingleTouchListner;
    }



}
