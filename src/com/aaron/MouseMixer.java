package com.aaron;

import java.awt.*;

public class MouseMixer {

    private EntropyPool entropyPool;

    public MouseMixer(EntropyPool entropyPool) {
        this.entropyPool = entropyPool;
    }

    public void run() {
        Point point = MouseInfo.getPointerInfo().getLocation();
        while (true) {
            Point newPoint = MouseInfo.getPointerInfo().getLocation();
            if (!point.equals(newPoint)) {
                point = newPoint;
                entropyPool.mix(Integer.toHexString(point.x * point.y).getBytes());
            }
        }
    }
}
