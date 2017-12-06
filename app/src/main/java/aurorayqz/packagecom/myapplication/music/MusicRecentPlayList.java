package aurorayqz.packagecom.myapplication.music;

import java.util.ArrayList;

import aurorayqz.packagecom.myapplication.MyApplication;
import aurorayqz.packagecom.myapplication.common.util.ACache;
import aurorayqz.packagecom.myapplication.data.Song;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Aurorayqz on 2017/12/6.
 * 最近播放的列表
 */

public class MusicRecentPlayList {
    private ArrayList<Song> queue;

    private static MusicRecentPlayList instance;
    private int RECENT_COUNT = 20;
    private String PLAY_QUEUE = "song_queue";

    private static MusicRecentPlayList getInstance(){
        if(instance == null){
            instance = new MusicRecentPlayList();
        }
        return instance;
    }

    private MusicRecentPlayList(){
        queue = readQueueFromFileCache();
    }


    //歌曲的添加
    private void addPlaySong(Song song){
        queue.add(song);
        for (int i = queue.size() - 1; i > 0; i--){
            //歌曲不能重复

            //最近播放歌曲有数量的限制
            if(i > RECENT_COUNT){
                queue.remove(i);
                continue;
            }

            if(song.getId() == queue.get(i).getId()){
                queue.remove(i);
                break;
            }
        }

        Observable.create(new Observable.OnSubscribe<Object>(){

            @Override
            public void call(Subscriber<? super Object> subscriber) {
                addQueueToFileCache();
            }
        }).subscribeOn(Schedulers.io()).subscribe();

    }

    /***
     *将播放列表缓存到文件中,以便下次读取
     */
    private void addQueueToFileCache(){
        ACache.get(MyApplication.getInstance(),PLAY_QUEUE).put(PLAY_QUEUE,queue);
    }


    //歌曲的读取
    private ArrayList<Song> readQueueFromFileCache(){
        Object object = ACache.get(MyApplication.getInstance(), PLAY_QUEUE).getAsObject(PLAY_QUEUE);

        if(object != null && object instanceof ArrayList){
            return (ArrayList<Song>) object;
        }

        return new ArrayList<>();
    }
}
