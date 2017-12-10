package aurorayqz.packagecom.myapplication.data;

/**
 * Created by Aurorayqz on 2017/12/10.
 */

import com.google.gson.annotations.SerializedName;

public class Artwork {

    private static final String URL = "#text";
    private static final String SIZE = "size";

    @SerializedName(URL)
    public String mUrl;

    @SerializedName(SIZE)
    public String mSize;
}