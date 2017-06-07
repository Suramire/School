package com.suramire.school.haomabaishitong;

/**
 * Created by Suramire on 2017/5/1.
 * 实体类 号码
 */

public class Child {
    private String name;//姓名
    private String number;//电话
    private String keyword;//关键字
    private String pingyin;//拼音
    private String p;//首字母

    public String getPingyin() {
        return pingyin;
    }

    public void setPingyin(String pingyin) {
        this.pingyin = pingyin;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    //不带关键字的联系人
    public Child(String name, String number) {
        this.name = name;
        this.number = number;
    }
    //带关键字的联系人
    public Child(String name, String number, String keyword) {
        this.name = name;
        this.number = number;
        this.keyword = keyword;
    }
    //带拼音不带关键字的联系人

    public Child(String name, String number, String pingyin, String p) {
        this.name = name;
        this.number = number;
        this.pingyin = pingyin;
        this.p = p;
    }

    //带拼音以及关键字的联系人
    public Child(String name, String number, String keyword, String pingyin, String p) {
        this.name = name;
        this.number = number;
        this.keyword = keyword;
        this.pingyin = pingyin;
        this.p = p;
    }
}
