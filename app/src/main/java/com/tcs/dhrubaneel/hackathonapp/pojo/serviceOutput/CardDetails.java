package com.tcs.dhrubaneel.hackathonapp.pojo.serviceOutput;

import java.io.Serializable;

public class CardDetails implements Serializable {
    private String title;
    private String productType;
    private String lastModified;
    private String id;
    private String recommended;
    private String displayType;
    private ProductContent content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public ProductContent getContent() {
        return content;
    }

    public void setContent(ProductContent content) {
        this.content = content;
    }
}
