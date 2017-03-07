package com.futuremall.android.model.bean;

/**
 * Created by PVer on 2017/3/1.
 */

public class VersionBean {

    private String version_code;
    private String version_name;
    private String content;
    private String url;
    private String type;

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "VersionBean{" +
                "version_code='" + version_code + '\'' +
                ", version_name='" + version_name + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
