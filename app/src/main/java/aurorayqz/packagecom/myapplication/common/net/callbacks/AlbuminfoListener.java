package aurorayqz.packagecom.myapplication.common.net.callbacks;

import aurorayqz.packagecom.myapplication.data.LastfmAlbum;

/**
 * Created by Aurorayqz on 2017/12/10.
 */

public interface AlbuminfoListener {

    void albumInfoSucess(LastfmAlbum album);

    void albumInfoFailed();

}
