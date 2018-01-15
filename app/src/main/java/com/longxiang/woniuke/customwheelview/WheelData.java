/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.longxiang.woniuke.customwheelview;

import java.io.Serializable;

/**
 * 滚轮数据
 *
 * @author venshine
 */
public class WheelData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     */
    private String id;
    /**
     * 资源图片
     */
    private String icon;

    /**
     * 数据名称
     */
    private String name;

    private String bgid;
    private String jnid;
    private String djid;
    public WheelData() {
    }

    public WheelData(String icon, String name,String bgid,String jnid) {
        this.id="0";
        this.icon = icon;
        this.name = name;
        this.bgid=bgid;
        this.jnid=jnid;
    }

    public String getDjid() {
        return djid;
    }

    public void setDjid(String djid) {
        this.djid = djid;
    }

    public String getJnid() {
        return jnid;
    }

    public void setJnid(String jnid) {
        this.jnid = jnid;
    }

    public String getBgid() {
        return bgid;
    }

    public void setBgid(String bgid) {
        this.bgid = bgid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WheelData{");
        sb.append("icon=").append(icon);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
