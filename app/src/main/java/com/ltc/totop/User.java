package com.ltc.totop;

import java.sql.Time;

public class User {

    public String name;
    public Integer rate;

    public User(String name, Integer rate){

        this.name = name;

    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public void setOpened(Integer rate) {
        this.rate = rate;
    }

    public void setClosed(Integer rate) {
        this.rate = rate;
    }

    public void setCanceled(Integer rate) {
        this.rate = rate;
    }

    public void setMoved(Integer rate) {
        this.rate = rate;
    }

    public Integer getRate() {
        return rate;
    }

    public String getName() {
        return name;
    }

}