package aurorayqz.packagecom.myapplication.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import aurorayqz.packagecom.myapplication.R;
import aurorayqz.packagecom.myapplication.ui.cnmusic.BaseActivity;
import aurorayqz.packagecom.myapplication.ui.cnmusic.MainActivity;
import aurorayqz.packagecom.myapplication.ui.guide.GuideActivity;

public class WelcomeActivity extends BaseActivity {

    private ImageView mLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mLogo = (ImageView) findViewById(R.id.logo);
        startAnimation();
    }

    /***
     * 启动页的动画
     */
    private void startAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogo, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogo, "scaleY", 0f, 1f);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mLogo, "alpha", 0f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alphaAnimator).with(scaleX).with(scaleY);
        animatorSet.setDuration(1500);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //1.当APP有用户之后,可以在此判断是否登录

                //2.需要跳转界面

                startToActivity(GuideActivity.class);
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }

        return false;
    }
}
