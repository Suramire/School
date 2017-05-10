package com.suramire.school.youwannanchang;

/**
 * Created by Suramire on 2017/5/10.
 */

public class Item {
    String name;
    String text;
    int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item(String name, String text, int image) {
        this.name = name;
        this.text = text;
        this.image = image;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
