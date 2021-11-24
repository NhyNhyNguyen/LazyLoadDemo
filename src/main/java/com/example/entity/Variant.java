package com.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "variant")
public class Variant {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vId;
    private String vName;
    private double price;

    public int getvId() {
        return vId;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "vId=" + vId +
                ", vName='" + vName + '\'' +
                ", price=" + price +
                '}';
    }
}
