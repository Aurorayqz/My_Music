package aurorayqz.packagecom.myapplication.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import java.io.IOException;

import aurorayqz.packagecom.myapplication.data.Song;
import aurorayqz.packagecom.myapplication.music.MusicRecentPlayList;

/***
 * 音乐播放的服务
 *
 * 1.开启服务-----开启服务的帮助类
 * 2.MediaSession框架,专门解决媒体播放时界面和服务通讯问题
 * 3.监听的接口----歌曲的切换
 * 4.音乐播放的管理
 */


public class MusicService extends Service implements OnSongchangeListener{



    public final Binder mBinder = new MyBinder();


    private MusicPlayerManager playerManager;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat mState;

    @Override
    public void onSongChanged(Song song) {
        //添加播放过的歌曲
        MusicRecentPlayList.getInstance().addPlaySong(song);
    }

    @Override
    public void onPlayBackStateChanged(PlaybackStateCompat state) {

    }

    public class MyBinder extends Binder{
        public MusicService getMusicService(){
            return MusicService.this;
        }
    }


    /***
     * 停止服务
     */
    public void stopService() {
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //服务的入口方法
        MediaButtonReceiver.handleIntent(mediaSession, intent);
        return super.onStartCommand(intent, flags, startId);
    }

    //create,new
    public static MediaPlayer mediaPlayer = new MediaPlayer();

    public void setUp(){
        playerManager = MusicPlayerManager.from(this);
        setUpMediaSession();

    }

    /***
     * 使用MediaButtonReceiVer来兼容api21之前的版本
     */
    private void setUpMediaSession() {
        ComponentName componentName = new ComponentName(getPackageName(), MediaButtonReceiver.class.getName());
        mediaSession = new MediaSessionCompat(this, "fd", componentName, null);
        //设置处理media button的flag
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        //设置回调
        mediaSession.setCallback(new MidiaSessionCallback());
        setState(PlaybackStateCompat.STATE_NONE);

    }

    /****
     * 设置播放状态
     */
    public void setState(int state) {
        mState = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY|
                                PlaybackStateCompat.ACTION_PLAY_PAUSE |
                                PlaybackStateCompat.ACTION_PAUSE|
                                PlaybackStateCompat.ACTION_SKIP_TO_NEXT|
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS|
                                PlaybackStateCompat.ACTION_STOP|
                                PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID|
                                PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH|
                                PlaybackStateCompat.ACTION_SEEK_TO|
                                PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM
                )
                .setState(state,PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN,
                        1.0f, SystemClock.elapsedRealtime())
                .build();

        mediaSession.setPlaybackState(mState);
        mediaSession.setActive(state != PlaybackStateCompat.STATE_NONE &&
                state != PlaybackStateCompat.STATE_STOPPED);


    }

    /***
     * 获取播放状态
     * @return
     */
    public int getState(){
        Log.e("getState: ", "getState: " +mState.getState());
        return mState.getState();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaSession.release();

    }



    public class MidiaSessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            super.onPlay();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
        }

        @Override
        public void onStop() {
            super.onStop();
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
        }
    }



}