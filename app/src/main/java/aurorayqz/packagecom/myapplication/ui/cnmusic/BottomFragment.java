package aurorayqz.packagecom.myapplication.ui.cnmusic;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.facebook.drawee.view.SimpleDraweeView;

import aurorayqz.packagecom.myapplication.R;
import aurorayqz.packagecom.myapplication.common.util.ImageUtils;
import aurorayqz.packagecom.myapplication.data.Song;
import aurorayqz.packagecom.myapplication.service.MusicPlayerManager;
import aurorayqz.packagecom.myapplication.service.OnSongchangeListener;
import aurorayqz.packagecom.myapplication.ui.play.PlayingActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends BaseFragment implements OnSongchangeListener{


    @Bind(R.id.playbar_img)
    SimpleDraweeView mPlaybarImg;
    @Bind(R.id.playbar_info)
    TextView mTitle;
    @Bind(R.id.playbar_singer)
    TextView mArtist;
    @Bind(R.id.play_list)
    TintImageView mPlayList;
    @Bind(R.id.control)
    TintImageView mPlayPause;
    @Bind(R.id.play_next)
    TintImageView mPlayNext;
    @Bind(R.id.linear)
    LinearLayout mLinear;
    @Bind(R.id.song_progress_normal)
    SeekBar mSongProgressNormal;
    @Bind(R.id.nav_play)
    LinearLayout mNavPlay;

    private View rootView;

    private boolean duetoplaypause = false;
    private Song song;
    private Subscription progressSub, timerSub;
    private boolean isPaused;

    public static BottomFragment newInstance() {
        return new BottomFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_nav, container, false);
        this.rootView = rootView;

        ButterKnife.bind(this, rootView);

        //音乐信息的更新的监听注册
        MusicPlayerManager.get().registerListener(this);

        //点击跳转
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.play_list, R.id.play_next, R.id.control})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_list:
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PlayQueueFragment queueFragment = new PlayQueueFragment();
                        queueFragment.show(getFragmentManager(),"playqueuefragment");
                    }
                },60);

                break;
            case R.id.play_next:
                MusicPlayerManager.get().playNext();

                break;
            case R.id.control:
                if(MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PLAYING){
                    MusicPlayerManager.get().pause();
                    mPlayPause.setImageResource(R.drawable.playbar_btn_play);
                }else if(MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PAUSED){
                    MusicPlayerManager.get().play();
                    mPlayPause.setImageResource(R.drawable.playbar_btn_pause);
                }
                break;
        }
    }

    /***
     * 更新数据:封面,标题,图标
     */
    private void updateData() {
        //歌曲的封面
        String coverUrl = song.getCoverUrl();
        ImageUtils.GlideWith(getActivity(),coverUrl,R.drawable.ah1,mPlaybarImg);

        //设置标题
        if(!TextUtils.isEmpty(song.getAlbumName())){
            String title = song.getAlbumName();
            mTitle.setText(title);
        }
        //设置歌手
        if(!TextUtils.isEmpty(song.getArtistName())){
            String title = song.getAlbumName();
            mArtist.setText(title);
        }


        if(MusicPlayerManager.get().getPlayingSong() != null){
            mPlayPause.setImageResource(R.drawable.playbar_btn_pause);
        }

    }





    @Override
    public void onSongChanged(Song song) {
        this.song = song;
        updateData();
    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {
        if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PLAYING) {

            mPlayPause.setImageResource(R.drawable.playbar_btn_pause);
        } else if (MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PAUSED) {
            mPlayPause.setImageResource(R.drawable.playbar_btn_play);

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        isPaused = false;
//        updateData();
    }

    @Override
    public void onPause() {
        isPaused = true;
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MusicPlayerManager.get().unregisterListener(this);
//        progressSub.unsubscribe();
    }
}