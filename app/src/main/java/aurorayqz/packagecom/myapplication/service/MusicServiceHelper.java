package aurorayqz.packagecom.myapplication.service;

/**
 * Created by Aurorayqz on 2017/12/5.
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class MusicServiceHelper {
    private Context mContext;

    private static MusicServiceHelper musicServiceHelper =
            new MusicServiceHelper();


    public static MusicServiceHelper get(Context context){
        musicServiceHelper.mContext = context;
        return musicServiceHelper;
    }

    MusicService musicService;


    /***
     * 服务初始化
     */
    public void initService(){
        if(musicService == null){
            Intent intent = new Intent(mContext, MusicService.class);

            ServiceConnection conn =new ServiceConnection(){

                @Override
                public void onServiceConnected(ComponentName componentName, IBinder service) {
                    MusicService.MyBinder binder = (MusicService.MyBinder) service;
                    musicService = binder.getMusicService();
                    musicService.setUp();
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {

                }
            };
            mContext.startService(intent);
            mContext.bindService(intent,conn,Context.BIND_AUTO_CREATE);

        }
    }

    public MusicService getService() {
        return musicService;
    }
}
