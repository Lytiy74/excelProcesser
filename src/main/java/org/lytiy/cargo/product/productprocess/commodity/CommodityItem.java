package org.lytiy.cargo.product.productprocess.commodity;

public class CommodityItem {
    private String parentId;
    private String id;
    private String text;

    public CommodityItem(String parentId, String id, String text) {
        this.parentId = parentId;
        this.id = id;
        this.text = text;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
