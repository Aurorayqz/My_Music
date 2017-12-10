package aurorayqz.packagecom.myapplication.service;

import android.support.v4.media.session.PlaybackStateCompat;

import aurorayqz.packagecom.myapplication.data.Song;

/**
 * Created by Aurorayqz on 2017/12/6.
 */

public interface OnSongChangedListener {
    //歌曲改变的回调
    void onSongChanged(Song song);
    //歌曲后台改变的回调
    void onPlayBackStateChanged(PlaybackStateCompat state);
}
