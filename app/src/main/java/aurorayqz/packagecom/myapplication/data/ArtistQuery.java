package aurorayqz.packagecom.myapplication.data;

/**
 * Created by Aurorayqz on 2017/12/10.
 */

import com.google.gson.annotations.SerializedName;

public class ArtistQuery {

    private static final String ARTIST_NAME = "artist";

    @SerializedName(ARTIST_NAME)
    public String mArtist;

    public ArtistQuery(String artist) {
        this.mArtist = artist;
    }


}
