package aurorayqz.packagecom.myapplication.model.event;

import java.util.List;

import aurorayqz.packagecom.myapplication.data.Song;

/**
 * Created by Aurorayqz on 2017/12/6.
 */

public interface LocalIView {
    interface LocalMusic{
        //获取本地歌曲
        void getLocalMusic(List<Song> songs);
    }

    interface LocalAlbum{
        void getLocalAlbum(List<String> albums);
    }
}
