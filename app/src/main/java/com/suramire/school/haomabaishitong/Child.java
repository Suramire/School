package com.suramire.school.haomabaishitong;

/**
 * Created by Suramire on 2017/5/1.
 */

public class Child {
    private String name;
    private String number;
    private  int groupindex;

    public int getGroupindex() {
        return groupindex;
    }

    public void setGroupindex(int groupindex) {
        this.groupindex = groupindex;
    }
    //默认分组(学生)
    public Child(String name, String number) {
        this.name = name;
        this.number = number;
        this.groupindex = 1;
    }
    //根据下标选择分组
    public Child(String name, String number, int groupindex) {
        this.name = name;
        this.number = number;
        this.groupindex = groupindex;
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
}
