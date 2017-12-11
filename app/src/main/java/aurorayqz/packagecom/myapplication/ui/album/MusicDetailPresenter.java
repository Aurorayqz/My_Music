package aurorayqz.packagecom.myapplication.ui.album;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import aurorayqz.packagecom.myapplication.common.net.ApiLoader;
import aurorayqz.packagecom.myapplication.common.net.ApiStore;
import aurorayqz.packagecom.myapplication.common.net.RetrofitManager;
import aurorayqz.packagecom.myapplication.data.RankingInsideInfo;
import aurorayqz.packagecom.myapplication.data.SongListInsideInfo;
import aurorayqz.packagecom.myapplication.model.NewSongDetaiIView;
import aurorayqz.packagecom.myapplication.model.RankingDetaiIView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.content.Context;

public class MusicDetailPresenter {
    private Context context;
    private RankingDetaiIView localAlbumIView;
    private NewSongDetaiIView mSongDetaiIView;
    private ApiLoader mMovieLoader;

    public MusicDetailPresenter(Context context, RankingDetaiIView localAlbumIView) {
        this.context = context;
        this.localAlbumIView = localAlbumIView;
    }

    public MusicDetailPresenter(Context context, NewSongDetaiIView localAlbumIView) {
        this.context = context;
        this.mSongDetaiIView = localAlbumIView;
    }

    //详情页面的数据解析
    public void initListData(int type, String id) {
        switch (type) {
            case 0:
                Observable<RankingInsideInfo> observable = RetrofitManager.getInstance()
                        .getAPIService(ApiStore.BASE_PARAMETERS_URL)
                        .getRankingInsideInfo("" + id);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<RankingInsideInfo>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                localAlbumIView.loadFail();
                            }

                            @Override
                            public void onNext(RankingInsideInfo rankingInfo) {
                                localAlbumIView.loadMusicDetailData(rankingInfo);
                            }
                        });
                break;

            case 1:
                OkGo.<String>get(ApiStore.GEDANINFO_URL + id)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Gson gson = new GsonBuilder().create();
                                SongListInsideInfo songListInsideInfo = gson.fromJson(response.body(), SongListInsideInfo.class);
                                mSongDetaiIView.loadMusicDetailData(songListInsideInfo);
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                mSongDetaiIView.loadFail(response.getException());
                            }
                        });


                break;
        }

    }


}
