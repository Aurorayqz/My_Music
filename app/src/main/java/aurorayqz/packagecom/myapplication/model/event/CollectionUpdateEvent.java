package aurorayqz.packagecom.myapplication.model.event;

/**
 * Created by Aurorayqz on 2017/12/8.
 */

public class CollectionUpdateEvent {
    private boolean update;

    public CollectionUpdateEvent(boolean update) {
        this.update = update;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
