package ru.sbrf.partneronline.web.page.audit;

import java.io.Serializable;

/**
 * Created by sbt-bauer-vv on 06.05.2014.
 */
public class SimpleFilter implements Serializable{

    private static final long serialVersionUID = 1L;

    private String action;

    private String userName;




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


    @Override
    public String toString() {
        return "SimpleFilter{" +
                "action='" + action + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
