package com.ruoyi.system.domain;

/**
 * @author Yancheng Liu
 * @create 2021-02-07 15:32
 */
public class Picture {

    private String img_picture;

    @Override
    public String toString() {
        return "Picture{" +
                "img_picture='" + img_picture + '\'' +
                '}';
    }

    public String getImg_picture() {
        return img_picture;
    }

    public void setImg_picture(String img_picture) {
        this.img_picture = img_picture;
    }
}
