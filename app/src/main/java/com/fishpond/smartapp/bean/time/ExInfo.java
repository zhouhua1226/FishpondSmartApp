package com.fishpond.smartapp.bean.time;

import java.io.Serializable;

/**
 * Created by zhouh on 2018/12/25.
 */
public class ExInfo implements Serializable{
    private String type;
    private String marketId;

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMarketId() {
        return marketId;
    }

    public String getType() {
        return type;
    }
}
