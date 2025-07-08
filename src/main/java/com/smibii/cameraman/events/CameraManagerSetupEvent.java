package com.smibii.cameraman.events;

import com.smibii.cameraman.camera.CameraManager;
import net.minecraftforge.eventbus.api.Event;

public class CameraManagerSetupEvent extends Event {
    public CameraManager manager;

    public CameraManagerSetupEvent(CameraManager manager) {
        this.manager = manager;
    }
}
