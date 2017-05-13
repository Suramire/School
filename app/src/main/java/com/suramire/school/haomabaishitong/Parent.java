package com.suramire.school.haomabaishitong;

import java.util.ArrayList;

/**
 * Created by Suramire on 2017/5/1.
 */

public class Parent {
    private ArrayList<Child> childArrayList;
    private String parentName;

    public Parent(ArrayList<Child> childArrayList, String parentName) {
        this.childArrayList = new ArrayList<Child>(childArrayList);
        this.parentName = parentName;
    }

    public Parent(String parentName) {
        this.childArrayList = new ArrayList<>();
        this.parentName = parentName;
    }

    public ArrayList<Child> getChildArrayList() {
        return childArrayList;
    }

    public void setChildArrayList(ArrayList<Child> childArrayList) {
        this.childArrayList = new ArrayList<Child>(childArrayList);
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
