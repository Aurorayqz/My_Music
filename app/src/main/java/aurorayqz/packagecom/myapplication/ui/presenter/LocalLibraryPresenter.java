package aurorayqz.packagecom.myapplication.ui.presenter;

import android.content.Context;

import java.util.List;

import aurorayqz.packagecom.myapplication.common.util.LocalMusicLibrary;
import aurorayqz.packagecom.myapplication.data.Album;
import aurorayqz.packagecom.myapplication.data.Song;
import aurorayqz.packagecom.myapplication.model.event.LocalIView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 歌曲的获取,逻辑处理
 * Created by Aurorayqz on 2017/11/18.
 */

/**
 * presenter层进行调用Model
 */
public class LocalLibraryPresenter {


    private LocalIView.LocalMusic localMusic;
    private LocalIView.LocalAlbum localAlbum;
    private LocalIView.LocalArtist localArtist;

    private Context context;

    public LocalLibraryPresenter(LocalIView.LocalMusic localMusic, Context context) {
        this.localMusic = localMusic;
        this.context = context;
    }

    public LocalLibraryPresenter(LocalIView.LocalAlbum localAlbum, Context context) {
        this.localAlbum = localAlbum;
        this.context = context;
    }

    public LocalLibraryPresenter(LocalIView.LocalArtist localArtist, Context context) {
        this.localArtist = localArtist;
        this.context = context;
    }

    public void requestMusic() {
        Observable.create(
                new Observable.OnSubscribe<List<Song>>() {
                    @Override
                    public void call(Subscriber<? super List<Song>> subscriber) {
                        List<Song> songs = LocalMusicLibrary.getAllSongs(context);
                        subscriber.onNext(songs);
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Song>>() {
                    @Override
                    public void call(List<Song> songs) {
                        if (localMusic != null)
                            localMusic.getLocalMusic(songs);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    public void requestAlbum() {
        Observable.create(
                new Observable.OnSubscribe<List<Album>>() {
                    @Override
                    public void call(Subscriber<? super List<Album>> subscriber) {
                        List<Album> albums = LocalMusicLibrary.getAllAlbums(context);
                        subscriber.onNext(albums);
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Album>>() {
                    @Override
                    public void call(List<Album> albums) {
                        if (localAlbum != null)
                            localAlbum.getLocalAlbum(albums);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

}