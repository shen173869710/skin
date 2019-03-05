package com.embed.skin.entity;

import java.util.List;

/**
 * Created by Administrator on 2019/3/4.
 */

public class SettingInfo {
    public String name;
    public List<MachineInfo>info;

    public SettingInfo(String name) {
        this.name = name;
    }

    public static class  MachineInfo {
        public String title;
        public boolean isSel;

        public MachineInfo(String title) {
            this.title = title;
        }
    }
}
