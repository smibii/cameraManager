package com.smibii.cameraman.math;

public class Easing {
    public static double linear(double t) {
        return t;
    }

    public static double easeInOutQuad(double t) {
        return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
    }

    public static double easeOutCubic(double t) {
        return 1 - Math.pow(1 - t, 3);
    }

    public static double easeInCubic(double t) {
        return t * t * t;
    }

    public static double easeInOutSine(double t) {
        return -(Math.cos(Math.PI * t) - 1) / 2;
    }
}