package ru.sbrf.partneronline.web.page.audit;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sbt-bauer-vv on 06.05.2014.
 */
public class AuditFilter implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long auditId;

    private String action;

    private String userName;

    private Date dateFrom;

    private Date dateTo;

    private String ipAddress;


    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
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

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "SimpleFilter{" +
                "action='" + action + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
