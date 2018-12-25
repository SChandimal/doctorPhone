package com.doctorPhone.doctorPhone.common;

import javax.persistence.*;

@Entity
@Table(name = "phone_details")
public class Phone_Details {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "phoneId")
    private int phoneId;

    @Column(name = "phone_toestelmerk")
    private String toestelmerk;

    @Column(name = "phone_model")
    private String model;

    @Column(name = "phone_title")
    private String tochuscreen;

    @Column(name = "phone_titleDetails")
    private String tochDetails;

    @Column(name = "phone_tildsduur")
    private String tildsduur;

    @Column(name = "phone_garantie")
    private String garantie;

    @Column(name = "phone_price")
    private String price;

    @Column(name = "Current_Link")
    private String currentLink;

    public String getCurrentLink() {
        return currentLink;
    }

    public void setCurrentLink(String currentLink) {
        this.currentLink = currentLink;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    public String getToestelmerk() {
        return toestelmerk;
    }

    public void setToestelmerk(String toestelmerk) {
        this.toestelmerk = toestelmerk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTochuscreen() {
        return tochuscreen;
    }

    public void setTochuscreen(String tochuscreen) {
        this.tochuscreen = tochuscreen;
    }

    public String getTochDetails() {
        return tochDetails;
    }

    public void setTochDetails(String tochDetails) {
        this.tochDetails = tochDetails;
    }

    public String getTildsduur() {
        return tildsduur;
    }

    public void setTildsduur(String tildsduur) {
        this.tildsduur = tildsduur;
    }

    public String getGarantie() {
        return garantie;
    }

    public void setGarantie(String garantie) {
        this.garantie = garantie;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
