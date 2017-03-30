package com.pkg.android.grossary.model;

/**
 * Created by GAURAV on 04-02-2017.
 */

public class UserInfo {

    public long userid;
    public String emailid;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public UserInfo(long userid, String emailid) {
        this.userid = userid;
        this.emailid = emailid;
    }

    public UserInfo() {
    }
}
