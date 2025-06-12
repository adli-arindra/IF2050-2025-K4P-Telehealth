package org.drpl.telefe.app.utils;

public enum ToastType {
    SUCCESS("#18BB8B"),
    ERROR("#FF3333"),
    INFO("#FFB703");

    public final String color;

    ToastType(String color) {
        this.color = color;
    }
}

