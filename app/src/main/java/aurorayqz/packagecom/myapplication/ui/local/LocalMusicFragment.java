package aurorayqz.packagecom.myapplication.ui.local;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Iterator;
import java.util.List;

import aurorayqz.packagecom.myapplication.R;
import aurorayqz.packagecom.myapplication.data.Song;
import aurorayqz.packagecom.myapplication.model.event.ILocalView;
import aurorayqz.packagecom.myapplication.ui.adapter.LocalRecyclerAdapter;
import aurorayqz.packagecom.myapplication.ui.cnmusic.BaseFragment;
import aurorayqz.packagecom.myapplication.ui.presenter.LocalLibraryPresenter;

/***
 * 1.song的实体类
 * 2.歌曲的获取         //获取的过程,数据的处理过程,逻辑部分
 * 3.Recycler的适配器
 *
 * 权限获取,6.0以上
 * https://github.com/lovedise/PermissionGen
 *
 *
 * 4.歌曲如何播放
 * 5.歌曲如何存取
 *
 */
public class LocalMusicFragment extends BaseFragment implements ILocalView.LocalMusic{

    private RecyclerView mRecyclerView;
    private LocalRecyclerAdapter mAdapter;
    private LocalLibraryPresenter mLibraryPresenter;

    public static LocalMusicFragment newInstance() {
        LocalMusicFragment fragment = new LocalMusicFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLibraryPresenter = new LocalLibraryPresenter(getActivity(), this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_local_music, container, false);
        initRecyclerView(inflate);

        return inflate;
    }

    /***
     * 初始化recyclerView
     * @param inflate
     */
    private void initRecyclerView(View inflate) {
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);

        mAdapter = new LocalRecyclerAdapter(getActivity());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(mAdapter);

        mLibraryPresenter.requestMusic();

    }


    @Override
    public void getLocalMusicSuccess(List<Song> songs) {
        //获取到本地音乐的数据
        mAdapter.setData(songs);

    }

    @Override
    public void getLocalMusicFail(Throwable throwable) {
        //获取失败

    }
}