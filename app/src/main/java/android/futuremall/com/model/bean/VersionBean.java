package android.futuremall.com.model.bean;

/**
 * Created by PVer on 2017/3/1.
 */

public class VersionBean {

    private String Version;
    private String VersionDesc;
    private String UpdateUrl;
    private boolean IsEnforceUpdate;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getVersionDesc() {
        return VersionDesc;
    }

    public void setVersionDesc(String VersionDesc) {
        this.VersionDesc = VersionDesc;
    }

    public String getUpdateUrl() {
        return UpdateUrl;
    }

    public void setUpdateUrl(String UpdateUrl) {
        this.UpdateUrl = UpdateUrl;
    }

    public boolean isIsEnforceUpdate() {
        return IsEnforceUpdate;
    }

    public void setIsEnforceUpdate(boolean IsEnforceUpdate) {
        this.IsEnforceUpdate = IsEnforceUpdate;
    }

    @Override
    public String toString() {
        return "VersionBean{" +
                "Version='" + Version + '\'' +
                ", VersionDesc='" + VersionDesc + '\'' +
                ", UpdateUrl='" + UpdateUrl + '\'' +
                ", IsEnforceUpdate=" + IsEnforceUpdate +
                '}';
    }
}
