package com.njupt.school.model;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by YYT on 2015/12/2.
 */
public class User extends BmobUser {
    private static final long serialVersionUID = 1L;
    private String sex;
    private String school="南京邮电大学";
    private String academy;
    private BmobFile picuser;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public BmobFile getPicuser() {
        return picuser;
    }

    public void setPicuser(BmobFile picuser) {
        this.picuser = picuser;
    }
}
