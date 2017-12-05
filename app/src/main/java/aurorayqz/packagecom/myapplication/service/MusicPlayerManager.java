package aurorayqz.packagecom.myapplication.service;

import android.content.Context;

/**
 * 歌曲播放管理类
 * Created by Aurorayqz on 2017/12/5.
 */

public class MusicPlayerManager {

    private final static MusicPlayerManager instance =
            new MusicPlayerManager();

    private Context mContext;
    private MusicService mService;

    public static MusicPlayerManager get(){
        return instance;
    }

    /***
     * 设置Context和Service
     * @return
     */
    public static MusicPlayerManager from(MusicService musicService){
        return MusicPlayerManager.get().setContext(musicService).setService(musicService);

    }

    public MusicPlayerManager setContext(Context context){
        this.mContext = context;
        return this;
    }

    public MusicPlayerManager setService(MusicService service){
        this.mService = service;
        return this;
    }


}
