package ru.sbrf.partneronline.application.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vbauer on 11/05/14.
 */
@Entity
@Table(name = "AUDIT")
public class Audit {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User whoIsUser;

    private Date createDate;

    private String action;

    private String ipAddress;

    public Audit(){}

    public long getId() {
        return id;
    }

    public User getWhoIsUser() {
        return whoIsUser;
    }

    public void setWhoIsUser(User whoIsUser) {
        this.whoIsUser = whoIsUser;
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
                ", whoIsUser=" + whoIsUser +
                ", createDate=" + createDate +
                ", action='" + action + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
