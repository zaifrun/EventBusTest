package org.webdevelopment.eventbustest;

/**
 * Created by makn on 28-08-2016.
 */
public class ProgressEvent {
    public int progress;

    public ProgressEvent(int progress) {
        this.progress = progress;
    }

    public int getProgress()
    {
        return progress;
    }

}
