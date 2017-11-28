package aurorayqz.packagecom.myapplication.model.event;

import java.util.List;

import aurorayqz.packagecom.myapplication.data.Song;

/**
 * Created by Aurorayqz on 2017/11/18.
 */

public interface ILocalView {
    //单曲
    interface  LocalMusic{
        void getLocalMusicSuccess(List<Song> songs);
        void getLocalMusicFail(Throwable throwable);
    }

    //专辑
    interface  LocalAlbum{

    }
    //歌手
    interface  LocalArtist{

    }
}
