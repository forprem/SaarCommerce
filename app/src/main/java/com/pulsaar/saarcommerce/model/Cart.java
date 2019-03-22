package com.pulsaar.saarcommerce.model;

public class Cart {

    private String pid, pname, price, quantity, disount;

    public Cart() {
    }

    public Cart(String pid, String pname, String price, String quantity, String disount) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.disount = disount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDisount() {
        return disount;
    }

    public void setDisount(String disount) {
        this.disount = disount;
    }
}
