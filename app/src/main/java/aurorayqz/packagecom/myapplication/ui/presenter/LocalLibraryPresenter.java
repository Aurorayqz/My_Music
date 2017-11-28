package aurorayqz.packagecom.myapplication.ui.presenter;

import android.content.Context;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

import aurorayqz.packagecom.myapplication.common.util.LocalMusicLibrary;
import aurorayqz.packagecom.myapplication.data.Song;
import aurorayqz.packagecom.myapplication.model.event.ILocalView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 歌曲的获取,逻辑处理
 * Created by Aurorayqz on 2017/11/18.
 */

public class LocalLibraryPresenter {
    private Context mContext;
    private ILocalView.LocalMusic mILocalView;

    public LocalLibraryPresenter (Context context,ILocalView.LocalMusic iLocalView){
        this.mContext = context;
        this.mILocalView = iLocalView;
    }


    /***
     * 获取本地音乐
     */
    public void requestMusic(){
        Observable.create(new Observable.OnSubscribe<List<Song>>() {
            @Override
            public void call(Subscriber<? super List<Song>> subscriber) {
                //从底层获取的本地音乐
                List<Song> songs = LocalMusicLibrary.getAllSongs(mContext);
                subscriber.onNext(songs);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Song>>() {
                    @Override
                    public void call(List<Song> songs) {
                        //歌曲获取成功
                        if(mILocalView !=null){
                            mILocalView.getLocalMusicSuccess(songs);
                        }
                        Log.d("TAG","歌曲数量为"+songs.size());
                        Iterator<Song> it=songs.iterator();
                        while (it.hasNext())
                        {
                            Log.d("TAG","歌曲命名为"+it.next().getTitle());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //歌曲获取失败
                        mILocalView.getLocalMusicFail(throwable);
                    }
                });


    }

}
