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
 */

/**
 * @desciption: 最近播放列表，最多100首记录
 */

public class MusicRecentPlayList {

    private static final String PLAY_QUEUE = "song_queue";
    private static final int RECENT_COUNT = 10;
    private static MusicRecentPlayList instance;

    private ArrayList<Song> queue;


    public static MusicRecentPlayList getInstance() {
        if (instance == null) {
            instance = new MusicRecentPlayList();
        }
        return instance;
    }

    private MusicRecentPlayList() {
        queue = readQueueFromFileCache();
    }

    public void addPlaySong(final Song song) {
        queue.add(0, song);
        for (int i = queue.size() - 1; i > 0; i--) {
            if (i >= RECENT_COUNT) {
                queue.remove(i);
                continue;
            }
            if (song.getId() == queue.get(i).getId()) {
                queue.remove(i);
                break;
            }
        }
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                addQueueToFileCache();
            }
        }).subscribeOn(Schedulers.io()).subscribe();

    }

    public ArrayList<Song> getQueue() {
        return queue;
    }

    /**
     * 将播放列表缓存到文件中，以便下次使用时直接读取
     */
    private void addQueueToFileCache() {
        ACache.get(MyApplication.getInstance(), PLAY_QUEUE).put(PLAY_QUEUE, queue);
    }

    /**
     * 从文件缓存中读取上一次的播放列表
     */
    @SuppressWarnings("unchecked")
    private ArrayList<Song> readQueueFromFileCache() {
        Object serializable = ACache.get(MyApplication.getInstance(), PLAY_QUEUE).getAsObject(PLAY_QUEUE);
        if (serializable != null && serializable instanceof ArrayList) {
            return (ArrayList<Song>) serializable;
        }
        return new ArrayList<>();
    }


    public void clearRecentPlayList(){
        queue.clear();
    }

}