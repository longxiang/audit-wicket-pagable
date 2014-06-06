package ru.sbrf.partneronline.application.dto;

import java.util.Date;

/**
 * Created by sbt-bauer-vv on 05.05.2014.
 */
public class AuditView {

    private long id;

    private Date createDate;

    private String action;

    private String userName;

    private String ipAddress;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", action='" + action + '\'' +
                ", userName='" + userName + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
