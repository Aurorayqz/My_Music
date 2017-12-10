package aurorayqz.packagecom.myapplication.common.net.callbacks;

import aurorayqz.packagecom.myapplication.data.LastfmArtist;

/**
 * Created by Aurorayqz on 2017/12/10.
 */

public interface ArtistInfoListener {

    void artistInfoSucess(LastfmArtist artist);

    void artistInfoFailed();

}