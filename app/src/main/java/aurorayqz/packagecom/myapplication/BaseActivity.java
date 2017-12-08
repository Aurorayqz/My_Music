package aurorayqz.packagecom.myapplication;

import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import aurorayqz.packagecom.myapplication.ui.cnmusic.BottomFragment;
import aurorayqz.packagecom.myapplication.service.MusicPlayerManager;
import aurorayqz.packagecom.myapplication.ui.play.PlayingActivity;

public class BaseActivity extends AppCompatActivity  {

    private String TAG = "BaseActivity";

    /**
     * @param outState 取消保存状态
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    /**
     * @param savedInstanceState 取消保存状态
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //服务的初始化
        MusicPlayerManager.startServiceIfNecessary(getApplicationContext());
        showQuickControl(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unbind from the service
        unbindService();
    }

    public void unbindService() {

    }
    /**
     * 跳转到音乐播放界面
     * @return
     */
    public boolean gotoSongPlayerActivity() {
        if (MusicPlayerManager.get().getPlayingSong() == null) {
            showToast(R.string.music_playing_none);
            return false;
        }
        PlayingActivity.open(this);
        return true;
    }

    /**
     * snackbar的显示
     *
     * @param toast
     */
    public void showSnackBar(View view, String toast) {
        Snackbar.make(view, toast, Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBar(View view, @StringRes int toast) {
        Snackbar.make(view, getString(toast), Snackbar.LENGTH_SHORT).show();
    }

    public void showToast(int toastRes) {
        Toast.makeText(this, getString(toastRes), Toast.LENGTH_SHORT).show();
    }

    private BottomFragment fragment; //底部播放控制栏
    /**
     * @param show 显示或关闭底部播放控制栏
     */
    protected void showQuickControl(boolean show) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (show) {
            if (fragment == null) {
                fragment = BottomFragment.newInstance();
                ft.add(R.id.bottom_container, fragment).commitAllowingStateLoss();
            } else {
                ft.show(fragment).commitAllowingStateLoss();
            }
        } else {
            if (fragment != null)
                ft.hide(fragment).commitAllowingStateLoss();
        }
    }

}
