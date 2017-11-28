package aurorayqz.packagecom.myapplication.ui.album;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import aurorayqz.packagecom.myapplication.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 唱片
 */
public class AlbumFragment extends Fragment {


    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.bottom_container)
    FrameLayout bottomContainer;
    private TabAlbumAdapter mAdapter;

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_album, container, false);
        ButterKnife.bind(this, inflate);
        mAdapter = new TabAlbumAdapter(getChildFragmentManager());
        viewpager.setAdapter(mAdapter);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(mAdapter.getCount());
        tabLayout.setupWithViewPager(viewpager);


        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
