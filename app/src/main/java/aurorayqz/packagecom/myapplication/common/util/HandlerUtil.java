package aurorayqz.packagecom.myapplication.common.util;

import android.content.Context;
import android.os.Handler;
import java.lang.ref.WeakReference;

/**
 * Created by Aurorayqz on 2017/12/10.
 */

public class HandlerUtil extends Handler {

    private static HandlerUtil instance = null;
    WeakReference<Context> mActivityReference;

    public static HandlerUtil getInstance(Context context) {
        if (instance == null) {
            instance = new HandlerUtil(context.getApplicationContext());
        }
        return instance;
    }

    HandlerUtil(Context context) {
        mActivityReference = new WeakReference<>(context);
    }
}
