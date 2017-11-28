package aurorayqz.packagecom.myapplication.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import aurorayqz.packagecom.myapplication.ui.local.ArtistFragment;
import aurorayqz.packagecom.myapplication.ui.local.LocalAlbumFragment;
import aurorayqz.packagecom.myapplication.ui.local.LocalMusicFragment;

/**
 * Created by Aurorayqz on 2017/11/17.
 */

public class LocalMusicAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"单曲", "歌手", "专辑"};
    private LocalMusicFragment localMusicFragment;
    private LocalAlbumFragment localAlbumFragment;
    private ArtistFragment artistFragment;

    public LocalMusicAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                if (localMusicFragment == null) {
                    localMusicFragment = LocalMusicFragment.newInstance();
                }
                return localMusicFragment;
            case 1:
                if (artistFragment == null) {
                    artistFragment = ArtistFragment.newInstance();
                }
                return artistFragment;
            case 2:

                if (localAlbumFragment == null) {
                    localAlbumFragment = LocalAlbumFragment.newInstance();
                }
                return localAlbumFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
