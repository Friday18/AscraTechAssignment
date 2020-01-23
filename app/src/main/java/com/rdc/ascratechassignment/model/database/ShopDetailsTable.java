package com.rdc.ascratechassignment.model.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ShopDetailsTable")
public class ShopDetailsTable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shopId")
    private int shopId;

    @ColumnInfo(name = "shopNameId")
    private String shopNameId;

    @ColumnInfo(name = "shopnm")
    private String shopnm;

    @ColumnInfo(name = "productName")
    private String productName;

    @ColumnInfo(name = "quantity")
    private String quantity;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "totalPrice")
    private String totalPrice;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopnm() {
        return shopnm;
    }

    public void setShopnm(String shopnm) {
        this.shopnm = shopnm;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShopNameId() {
        return shopNameId;
    }

    public void setShopNameId(String shopNameId) {
        this.shopNameId = shopNameId;
    }
}
