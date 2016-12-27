package com.android.lq.p2p.lili.model;

/**
 * Created by a on 2016/12/27.
 */

public class InvestGirdModel {
    private int imageId;
    private String title;

    public InvestGirdModel(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }
    public InvestGirdModel() {
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
