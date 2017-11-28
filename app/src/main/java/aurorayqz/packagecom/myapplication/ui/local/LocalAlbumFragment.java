package aurorayqz.packagecom.myapplication.ui.local;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aurorayqz.packagecom.myapplication.R;
import aurorayqz.packagecom.myapplication.ui.cnmusic.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalAlbumFragment extends BaseFragment {

    public LocalAlbumFragment() {
        // Required empty public constructor
    }
    public static LocalAlbumFragment newInstance() {
        LocalAlbumFragment fragment = new LocalAlbumFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local_album, container, false);
    }
}
