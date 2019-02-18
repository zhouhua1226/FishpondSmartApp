package com.fishpond.smartapp.utils;

/**
 * Created by zhouh on 2018/12/15.
 */
public enum CmdType {
    ON(1),
    OFF(2);

    private int value;

    CmdType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static String getSceneName(int value) {
        CmdType[] types = CmdType.values();
        for(int i = 0; i < types.length; i++) {
            if (types[i].value == value) {
                return types[i].name();
            }
        }
        return null;
    }
}
