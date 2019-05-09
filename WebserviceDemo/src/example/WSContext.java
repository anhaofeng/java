package example;


import java.io.Serializable;

public class WSContext implements Serializable {
    private String userName;
    private String password;
    private String slnName;
    private String dcName;
    private int dbType;
    private String sessionId;


    public WSContext() {
    }

    public WSContext(String userName, String password, String slnName, String dcName, int dbType, String sessionId) {
        this.userName = userName;
        this.password = password;
        this.slnName = slnName;
        this.dcName = dcName;
        this.dbType = dbType;
        this.sessionId = sessionId;
    }

    public int getDbType() {
        return this.dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    public String getDcName() {
        return this.dcName;
    }

    public void setDcName(String dcName) {
        this.dcName = dcName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSlnName() {
        return this.slnName;
    }

    public void setSlnName(String slnName) {
        this.slnName = slnName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}