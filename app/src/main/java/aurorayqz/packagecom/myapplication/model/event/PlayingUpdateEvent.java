package aurorayqz.packagecom.myapplication.model.event;

/**
 * Created by Aurorayqz on 2017/12/10.
 */

public class PlayingUpdateEvent {
    private boolean isPlaying;

    public PlayingUpdateEvent(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}
