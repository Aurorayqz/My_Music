package aurorayqz.packagecom.myapplication.ui.local;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aurorayqz.packagecom.myapplication.R;
import aurorayqz.packagecom.myapplication.ui.cnmusic.LocalMusicActivity;
import aurorayqz.packagecom.myapplication.ui.cnmusic.RecentPlayListActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalFragment extends Fragment {

    public static LocalFragment newInstance() {
        LocalFragment fragment = new LocalFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_local, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.recently_layout, R.id.local_layout, R.id.download_layout, R.id.artist_layout, R.id.add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recently_layout:
                RecentPlayListActivity.open(getActivity());
                break;
            case R.id.local_layout:
                startActivity(new Intent(getActivity(), LocalMusicActivity.class));
                break;
            case R.id.download_layout:
                break;
            case R.id.artist_layout:
                break;
            case R.id.add:
//                CollectionCreateActivity.open(getActivity());
                break;
        }
    }
}
