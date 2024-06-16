package com.omni.minecraft.database.storage.table;

public class Size {

    private int size;
    private int decimals;

    public Size(int size, int decimals) {
        this.size = size;
        this.decimals = decimals;
    }

    public Size(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        if (size == 0) return "";
        return "(" + size + (decimals != 0 ? "," + decimals : "") + ")";
    }
}
