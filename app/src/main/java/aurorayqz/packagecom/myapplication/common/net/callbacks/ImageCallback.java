package aurorayqz.packagecom.myapplication.common.net.callbacks;

import android.graphics.Bitmap;

/**
 * Created by Aurorayqz on 2017/12/11.
 */

public interface ImageCallback {
    void getImageSuccess(Bitmap resource);
    void getImageFail();
}