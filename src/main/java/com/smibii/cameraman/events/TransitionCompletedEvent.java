package com.smibii.cameraman.events;

import com.smibii.cameraman.camera.CameraPoint;
import net.minecraftforge.eventbus.api.Event;

public class TransitionCompletedEvent extends Event {
    public CameraPoint point;
    public String pointName;

    public TransitionCompletedEvent(CameraPoint point, String pointName) {
        this.point = point;
        this.pointName = pointName;
    }
}
