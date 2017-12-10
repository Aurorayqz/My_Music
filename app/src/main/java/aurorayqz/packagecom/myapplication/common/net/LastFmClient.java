package aurorayqz.packagecom.myapplication.common.net;

import android.content.Context;

import aurorayqz.packagecom.myapplication.common.net.callbacks.ArtistInfoListener;
import aurorayqz.packagecom.myapplication.data.AlbumInfo;
import aurorayqz.packagecom.myapplication.data.AlbumQuery;
import aurorayqz.packagecom.myapplication.data.ArtistInfo;
import aurorayqz.packagecom.myapplication.data.ArtistQuery;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Aurorayqz on 2017/12/10.
 */

public class LastFmClient {

    public static final String BASE_API_URL = "http://ws.audioscrobbler.com/2.0";
    private static final Object sLock = new Object();
    private static LastFmClient sInstance;
    private LastFmRestService mRestService;

    public static LastFmClient getsInstance(Context context){
        synchronized (sLock){
            if(sInstance == null){
                sInstance = new LastFmClient();
                sInstance.mRestService = RestServiceFactory.create(context,BASE_API_URL,LastFmRestService.class);
            }
            return sInstance;
        }
    }

    /****
     * 获取歌手信息
     * @param artistQuery
     * @param listener
     */
    public void getArtistInfo(ArtistQuery artistQuery, final ArtistInfoListener listener){
        mRestService.getArtistInfo(artistQuery.mArtist, new Callback<ArtistInfo>() {
            @Override
            public void success(ArtistInfo artistInfo, Response response) {
                listener.artistInfoSucess(artistInfo.mArtist);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.artistInfoFailed();

            }
        });

    }

    public void getAlbumInfo(AlbumQuery albumQuery){
        mRestService.getAlbumInfo(albumQuery.mALbum, albumQuery.mALbum, new Callback<AlbumInfo>() {
            @Override
            public void success(AlbumInfo albumInfo, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }




}
