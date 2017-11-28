package aurorayqz.packagecom.myapplication.ui.cnmusic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import aurorayqz.packagecom.myapplication.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //默认屏幕不能横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //setContentView(R.layout.activity_base);
    }

    /***
     * snackbar的显示
     * @param view
     * @param text
     */
    public void showSnackBar(View view,@Nullable String text){
        Snackbar.make(view,text,Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBar(View view,int resID){
        Snackbar.make(view,resID,Snackbar.LENGTH_SHORT).show();
    }

    public void startToActivity(Class activity){
        Intent intent=new Intent();
        intent.setClass(this,activity);
        startActivity(intent);
    }
}
