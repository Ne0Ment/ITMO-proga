package org.neoment.shared;

import java.io.Serializable;

public class User implements Serializable {
    public Long id;
    public String username;
    public String passwd;

    public User(Long id, String username, String passwd) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
    }
}
