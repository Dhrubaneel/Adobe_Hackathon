package com.tcs.dhrubaneel.hackathonapp.pojo.serviceOutput;

import java.io.Serializable;

public class ProductContent implements Serializable {
    private String description;
    private String sku;
    private String price;
    private ImageDetails imgSrc;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ImageDetails getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(ImageDetails imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "ProductContent{" +
                "description='" + description + '\'' +
                ", sku='" + sku + '\'' +
                ", price='" + price + '\'' +
                ", imgSrc=" + imgSrc +
                '}';
    }
}
