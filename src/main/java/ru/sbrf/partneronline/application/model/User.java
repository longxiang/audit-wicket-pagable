package ru.sbrf.partneronline.application.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vbauer on 11/05/14.
 */

@Entity
@Table(name="USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userName;

    @OneToMany(mappedBy = "whoIsUser")
    private Set<Audit> audits = new HashSet<Audit>();

    public User() {}

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }
}

