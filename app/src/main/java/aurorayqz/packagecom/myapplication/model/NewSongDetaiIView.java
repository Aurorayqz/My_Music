package aurorayqz.packagecom.myapplication.model;

import aurorayqz.packagecom.myapplication.data.SongListInsideInfo;

public interface NewSongDetaiIView extends BaseIView{

    void loadMusicDetailData(SongListInsideInfo baseBean);
    void loadFail(Throwable e);


}
