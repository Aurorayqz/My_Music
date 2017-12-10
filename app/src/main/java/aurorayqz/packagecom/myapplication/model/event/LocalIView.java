package aurorayqz.packagecom.myapplication.model.event;

import java.util.List;

import aurorayqz.packagecom.myapplication.data.Album;
import aurorayqz.packagecom.myapplication.data.Artist;
import aurorayqz.packagecom.myapplication.data.Song;

/**
 * Created by Aurorayqz on 2017/12/6.
 * 本地视图的接口
 */

public interface LocalIView {

    interface LocalMusic{
        void getLocalMusic(List<Song> songs);
    }

    interface LocalAlbum{
        void getLocalAlbum(List<Album> alba);
    }

    interface LocalArtist{
        void getLocalArtist(List<Artist> artists);
    }
}
