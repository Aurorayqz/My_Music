package aurorayqz.packagecom.myapplication.ui.cnmusic;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import aurorayqz.packagecom.myapplication.BaseActivity;
import aurorayqz.packagecom.myapplication.R;
import aurorayqz.packagecom.myapplication.ui.adapter.LocalMusicAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LocalMusicActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    private LocalMusicAdapter mLocalMusicAdapter;
    private String TAG = "LocalMusicActivity";
    private ActionBar ab;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        ButterKnife.bind(this);

        setToolBar();

        mLocalMusicAdapter = new LocalMusicAdapter(getSupportFragmentManager());

        mViewpager.setAdapter(mLocalMusicAdapter);

        mViewpager.setCurrentItem(0);

        mViewpager.setOffscreenPageLimit(mLocalMusicAdapter.getCount());

        Log.d("TAG","mLocalMusicAdapter.getCount()="+mLocalMusicAdapter.getCount());

        mTabLayout.setupWithViewPager(mViewpager);

    }

    /***
     * 设置toolbar
     */
    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.actionbar_back);
        ab.setTitle("本地音乐");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
