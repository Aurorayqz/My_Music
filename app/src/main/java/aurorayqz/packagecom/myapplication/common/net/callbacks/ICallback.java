package aurorayqz.packagecom.myapplication.common.net.callbacks;

import aurorayqz.packagecom.myapplication.data.SongInfo;

public interface ICallback {

    void getSongInfoSucess(SongInfo songInfo);

    void getSongInfoFailed(Throwable e);

}

