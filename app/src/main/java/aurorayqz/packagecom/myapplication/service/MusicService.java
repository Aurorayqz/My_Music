package aurorayqz.packagecom.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {

    public final Binder mBinder=new MyBinder();

    public class MyBinder extends Binder{
        public MusicService getMusicService(){
            return MusicService.this;
        }
    }

    public static MediaPlayer mediaPlayer=new MediaPlayer();

    public void play(String path){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();//播放
            }
        });
    }

    public void pause(){
        if (mediaPlayer!=null) {
            mediaPlayer.pause();
        }
    }

    public void stop(){
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
        }
    }

    public void continueMusic(){
        mediaPlayer.start();
    }

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }
}
