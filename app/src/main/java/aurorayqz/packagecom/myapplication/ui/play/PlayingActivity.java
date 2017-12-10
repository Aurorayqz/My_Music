package aurorayqz.packagecom.myapplication.ui.play;

import android.content.Context;
import android.content.Intent;
import android.support.v4.media.session.PlaybackStateCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import aurorayqz.packagecom.myapplication.R;
import aurorayqz.packagecom.myapplication.common.util.ImageUtils;
import aurorayqz.packagecom.myapplication.data.Song;
import aurorayqz.packagecom.myapplication.service.MusicPlayerManager;
import aurorayqz.packagecom.myapplication.service.OnSongChangedListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class PlayingActivity extends AppCompatActivity implements OnSongChangedListener {


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

    @Bind(R.id.coverImage)
    ImageView mCoverImage;
    @Bind(R.id.music_duration_played)
    TextView mMusicDurationPlayed;
    private Toolbar toolbar;
    private Song song;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        ButterKnife.bind(this);

        MusicPlayerManager.get().registerListener(this);
        initData();
        updateProgress();
        updateData();

    }



    private void initData() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        song = MusicPlayerManager.get().getPlayingSong();
        if(song == null){
            finish();
        }

        mPlaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    MusicPlayerManager.get().seekTo(progress);
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

    /***
     * 更新进度条,进度显示,歌曲长度
     */
    private void updateProgress() {
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mPlaySeek.setMax(MusicPlayerManager.get().getCurrentMaxDuration());
                        mPlaySeek.setProgress(MusicPlayerManager.get().getCurrentPosition());
                        mMusicDuration.setText(formatChange(
                                MusicPlayerManager
                                        .get()
                                        .getCurrentMaxDuration()));
                        mMusicDurationPlayed.setText(formatChange(
                                MusicPlayerManager
                                        .get()
                                        .getCurrentPosition()));

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }


    /***
     * 更新数据:封面,标题,图标
     */
    private void updateData() {
        //歌曲的封面
        String coverUrl = song.getCoverUrl();
        ImageUtils.GlideWith(this,coverUrl,R.drawable.ah1,mCoverImage);

        //设置标题
        if(!TextUtils.isEmpty(song.getAlbumName())){
            String title = song.getAlbumName();
            Spanned s = Html.fromHtml(title);
            getSupportActionBar().setTitle(s);
        }
        toolbar.setTitle(song.getTitle());

        if(MusicPlayerManager.get().getPlayingSong() != null){
            mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_pause);
        }

    }

    @Override
    public void onSongChanged(Song song) {
        this.song = song;
        updateData();
    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {

    }


    @OnClick({R.id.playing_pre, R.id.playing_play, R.id.playing_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playing_pre:
                MusicPlayerManager.get().playPrev();
                break;
            case R.id.playing_play:
                if(MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PLAYING){
                    MusicPlayerManager.get().pause();
                    mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_play);
                }else if(MusicPlayerManager.get().getState() == PlaybackStateCompat.STATE_PAUSED){
                    MusicPlayerManager.get().play();
                    mPlayingPlay.setImageResource(R.drawable.play_rdi_btn_pause);
                }


                break;
            case R.id.playing_next:
                MusicPlayerManager.get().playNext();
                break;
        }
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
        MusicPlayerManager.get().unregisterListener(this);

    }

    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,PlayingActivity.class);
        context.startActivity(intent);
    }

//    设置返回按钮
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
