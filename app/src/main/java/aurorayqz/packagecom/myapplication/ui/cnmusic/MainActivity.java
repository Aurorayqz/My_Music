package aurorayqz.packagecom.myapplication.ui.cnmusic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import aurorayqz.packagecom.myapplication.BaseActivity;
import aurorayqz.packagecom.myapplication.MyApplication;
import aurorayqz.packagecom.myapplication.PermissionActivity;
import aurorayqz.packagecom.myapplication.R;
import aurorayqz.packagecom.myapplication.service.MusicPlayerManager;
import aurorayqz.packagecom.myapplication.ui.adapter.MenuItemAdapter;
import aurorayqz.packagecom.myapplication.ui.album.AlbumFragment;
import aurorayqz.packagecom.myapplication.ui.dynamic.DynamicFragment;
import aurorayqz.packagecom.myapplication.ui.local.LocalFragment;
import aurorayqz.packagecom.myapplication.ui.widget.CustomViewPager;

public class MainActivity extends PermissionActivity {
    private ActionBar ab;
    private ImageView barnet, barmusic,barfriends;
    private ArrayList<ImageView> tabs = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ListView mLvLeftMenu;
    private long time = 0;
    private boolean isNight = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //夜间模式
        if (MyApplication.appConfig.getNightModeSwitch()) {
            this.setTheme(R.style.Theme_setting_night);
            isNight = true;
        } else {
            this.setTheme(R.style.Theme_setting_day);
            isNight = false;
        }

        setContentView(R.layout.activity_main);


        getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);

        barnet = (ImageView) findViewById(R.id.bar_net);
        barmusic = (ImageView) findViewById(R.id.bar_music);
        barfriends = (ImageView) findViewById(R.id.bar_friends);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);

        setToolBar();
        setCustomViewPager();
        setUpDrawer();

    }

    /***
     * 设置toolbar
     */
    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setTitle("");
    }

    /***
     * 设置自定义的viewpager
     */
    private void setCustomViewPager() {
        //添加tab标签
        tabs.add(barnet);
        tabs.add(barmusic);
        tabs.add(barfriends);


        final CustomViewPager customViewPager = (CustomViewPager) findViewById(R.id.main_viewpager);
        final LocalFragment localFragment = new LocalFragment();
        final AlbumFragment albumFragment = new AlbumFragment();
        final DynamicFragment dynamicFragment = new DynamicFragment();
        CustomViewPagerAdapter customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        customViewPagerAdapter.addFragment(albumFragment);
        customViewPagerAdapter.addFragment(localFragment);
        customViewPagerAdapter.addFragment(dynamicFragment);
        customViewPager.setAdapter(customViewPagerAdapter);
        customViewPager.setCurrentItem(1);
        barmusic.setSelected(true);
        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        barnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewPager.setCurrentItem(0);
            }
        });
        barmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewPager.setCurrentItem(1);
            }
        });
        barfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewPager.setCurrentItem(2);
            }
        });

    }

    /***
     * 初始化侧滑菜单
     */
    private void setUpDrawer() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mLvLeftMenu.addHeaderView(inflater.inflate(R.layout.nav_header_main, mLvLeftMenu, false));
        mLvLeftMenu.setAdapter(new MenuItemAdapter(this));
        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        //当前的模式
                        boolean isNightMode = MyApplication.appConfig.getNightModeSwitch();
                        MyApplication.appConfig.setNightModeSwitch(!isNightMode);
                        changeSkinMode(!isNightMode);
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case 2:
                        //主题换肤
                        break;
                    case 3:
                        //定时关闭音乐
                        TimingFragment fragment3 = new TimingFragment();
                        fragment3.show(getSupportFragmentManager(), "timing");
                        drawerLayout.closeDrawers();
                        break;
                    case 4:
                        //清除缓存
                        break;
                    case 5:
                        //退出
                        if (MusicPlayerManager.get().isPlaying()) {
                            MusicPlayerManager.get().pause();
                        }
                        unbindService();
                        finish();
                        break;

                }
            }
        });
    }

    public void changeSkinMode(boolean isNight) {
        changeActionbarSkinMode(ab, isNight);
    }

    /***
     * 切换toolbar的tab标签
     */
    private void changeTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            if (position == i) {
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }

    /**
     * customViewpager的适配器
     */
    static class CustomViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();

        public CustomViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case android.R.id.home: //Menu icon
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /***
     * 双击返回桌面
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
