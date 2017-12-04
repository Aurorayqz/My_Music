package aurorayqz.packagecom.myapplication.ui.play;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import aurorayqz.packagecom.myapplication.R;
import aurorayqz.packagecom.myapplication.common.util.LocalMusicLibrary;
import aurorayqz.packagecom.myapplication.data.Song;
import aurorayqz.packagecom.myapplication.service.MusicService;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static aurorayqz.packagecom.myapplication.service.MusicService.mediaPlayer;

public class PlayingActivity extends AppCompatActivity {


    /**
     * 播放seekbar
     **/
    @Bind(R.id.play_seek)
    SeekBar mPlaySeek;
    /**
     * 歌曲的长度
     **/
    @Bind(R.id.music_duration)
    TextView mMusicDuration;

    /**
     * 上一首
     **/
    @Bind(R.id.playing_pre)
    ImageView mPlayingPre;

    /**
     * 播放按钮
     **/

    @Bind(R.id.playing_play)
    ImageView mPlayingPlay;

    /**
     * 播放下一首
     **/
    @Bind(R.id.playing_next)
    ImageView mPlayingNext;
    @Bind(R.id.music_duration_played)
    TextView mMusicDurationPlayed;

    private List<Song> mAllSongs;
    private MusicService musicService;
    private MyServiceConn conn;
    private int index;
    private MediaPlayer mediaPlayer;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mPlaySeek.setProgress(mediaPlayer.getCurrentPosition());
            mMusicDurationPlayed.setText(formatChange(mediaPlayer.getCurrentPosition()));
            mHandler.postDelayed(mRunnable, 100);
        }
    };


    private class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
            musicService = myBinder.getMusicService();
            mediaPlayer = musicService.mediaPlayer;
            if (mediaPlayer != null) {
                //默认播放音乐
                playMusic(0);
                //改变图标,pause

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        playMusic(1);
                    }
                });


            } else {
                //改变图标
            }

            //播放音乐
            mPlaySeek.setMax(mediaPlayer.getDuration());
            mHandler.removeCallbacks(mRunnable);
            mHandler.post(mRunnable);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        ButterKnife.bind(this);

        init();

        //开启服务
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        //绑定服务
        conn = new MyServiceConn();
        bindService(intent, conn, BIND_AUTO_CREATE);

    }

    private void init() {
        //获取歌曲的数据
        mAllSongs = LocalMusicLibrary.getAllSongs(this);

        mPlaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //seekbar有两种进度改变的方式,一种人为
                if (fromUser) {
                    if (mediaPlayer != null) {
                        mediaPlayer.seekTo(progress);
                    }
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    @OnClick({R.id.playing_pre, R.id.playing_play, R.id.playing_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playing_pre:
                //上一首
                playMusicByStatu(2);
                break;
            case R.id.playing_play:
                //播放暂停
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        musicService.pause();
                        //改变图标,play
                    } else {
                        musicService.continueMusic();
                        //改变图标,pause
                    }
                }

                break;
            case R.id.playing_next:
                //下一首
                playMusicByStatu(1);

                break;
        }
    }

    /****
     * 根据歌曲状态进行播放
     *
     * @param status
     */
    public void playMusicByStatu(int status) {

        switch (status) {
            case 0:
                break;
            case 1:
                //播放下一首
                index++;
                if (index == mAllSongs.size()) {
                    index = 0;
                }

                break;
            case 2:
                //播放上一首
                index--;
                if (index == -1) {
                    index = mAllSongs.size() - 1;
                }

                break;

        }
        playMusic(index);
    }

    /***
     * 根据歌曲下标播放歌曲
     *
     * @param index
     */
    private void playMusic(int index) {
        if (mAllSongs.size() > 0) {
            musicService.play(mAllSongs.get(index).getPath());
            mMusicDuration.setText(formatChange(mAllSongs.get(index).getDuration()));
        }
        mPlaySeek.setMax(mediaPlayer.getDuration());

    }

    /***
     * 对歌曲长度的格式进行转换
     *
     * @param millSeconds
     */
    public String formatChange(int millSeconds) {
        int seconds = millSeconds / 1000;
        int second = seconds % 60;
        int munite = (seconds - second) / 60;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(munite) + ":" + decimalFormat.format(second);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);//如果不解绑,退出应用,音乐会关闭
    }
}
