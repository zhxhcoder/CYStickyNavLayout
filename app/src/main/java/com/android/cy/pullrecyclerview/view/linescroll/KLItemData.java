package com.android.cy.pullrecyclerview.view.linescroll;

import java.io.Serializable;

/**
 * Created by zhxh on 2018/1/2.
 */

public class KLItemData implements Serializable {

    private String times;
    private String highp;
    private String openp;
    private String breakp;
    private String reversep;
    private String lowp;
    private String nowv;
    private String preclose;
    private String curvol;
    private String curvalue;
    private String signType;
    private String date;
    private String price;
    private String wPointP;

    public String getReversep() {
        return reversep;
    }

    public void setReversep(String reversep) {
        this.reversep = reversep;
    }

    public String getwPointP() {
        return wPointP;
    }

    public void setwPointP(String wPointP) {
        this.wPointP = wPointP;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBreakp() {
        return breakp;
    }

    public void setBreakp(String breakp) {
        this.breakp = breakp;
    }

    private int signFlag; //1看多 -1看空 0默认
    private String areaType; //A B C D
    private String type;
    private int resistancePrice;
    private boolean isResistance;
    private String dkWarnType;

    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDkWarnType() {
        return dkWarnType;
    }

    public void setDkWarnType(String dkWarnType) {
        this.dkWarnType = dkWarnType;
    }

    public int getResistancePrice() {
        return resistancePrice;
    }

    public void setResistancePrice(int resistancePrice) {
        this.resistancePrice = resistancePrice;
    }

    public boolean isResistance() {
        return isResistance;
    }

    public void setResistance(boolean resistance) {
        isResistance = resistance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private int startI; //
    private int lengthI; //

    public int getLengthI() {
        return lengthI;
    }

    public void setLengthI(int lengthI) {
        this.lengthI = lengthI;
    }

    public int getStartI() {
        return startI;
    }

    public void setStartI(int startI) {
        this.startI = startI;
    }

    public int getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(int signFlag) {
        this.signFlag = signFlag;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getHighp() {
        return highp;
    }

    public void setHighp(String highp) {
        this.highp = highp;
    }

    public String getOpenp() {
        return openp;
    }

    public void setOpenp(String openp) {
        this.openp = openp;
    }

    public String getLowp() {
        return lowp;
    }

    public void setLowp(String lowp) {
        this.lowp = lowp;
    }

    public String getNowv() {
        return nowv;
    }

    public void setNowv(String nowv) {
        this.nowv = nowv;
    }

    public String getPreclose() {
        return preclose;
    }

    public void setPreclose(String preclose) {
        this.preclose = preclose;
    }

    public String getCurvol() {
        return curvol;
    }

    public void setCurvol(String curvol) {
        this.curvol = curvol;
    }

    public String getCurvalue() {
        return curvalue;
    }

    public void setCurvalue(String curvalue) {
        this.curvalue = curvalue;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
