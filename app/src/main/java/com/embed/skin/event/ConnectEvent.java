package com.embed.skin.event;

public class ConnectEvent {

    private boolean isConnect;

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public ConnectEvent(boolean isConnect) {
        this.isConnect = isConnect;
    }
}
