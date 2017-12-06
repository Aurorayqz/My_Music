package aurorayqz.packagecom.myapplication.ui.cnmusic;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import aurorayqz.packagecom.myapplication.R;
import aurorayqz.packagecom.myapplication.service.MusicPlayerManager;
import aurorayqz.packagecom.myapplication.ui.play.PlayingActivity;
import kr.co.namee.permissiongen.PermissionGen;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //权限的添加,针对6.0
        PermissionGen.needPermission(BaseFragment.this, 100,
                new String[] {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_CONTACTS
                }
        );
    }


    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    public void showToast(int toastRes) {
        Toast.makeText(getActivity(), getString(toastRes), Toast.LENGTH_SHORT).show();
    }

    public boolean gotoSongPlayerActivity(){
        if(MusicPlayerManager.get().getPlayingSong() == null){
            showToast(R.string.music_playing_none);
            return false;
        }
        PlayingActivity.open(getActivity());
        return true;
    }

}
