package com.example.main.FarDto;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class FarListModel  implements Serializable
{
    private String UNI_ID;  // 주유소ID
    private int PRICE;      // 판매가격
    private String POLL_DIV_CD; // 상표(SKE:SK에너지, GSC:GS칼텍스, HDO:현대오일뱅크, SOL:S-OIL, RTO:자영알뜰, RTX:고속도로알뜰, NHO:농협알뜰, ETC:자가상표, E1G: E1, SKG:SK가스 )
    private String OS_NM; // 상호
    private double DISTANCE; // 거리
    private double GIS_X_COOR;  // X좌표
    private double GIS_Y_COOR;  // Y좌표

    public FarListModel ()
    {
        super();
    }

    public FarListModel (
            String UNI_ID,
            int PRICE,
            String POLL_DIV_CD,
            String OS_NM,
            double DISTANCE,
            double GIS_X_COOR,
            double GIS_Y_COOR
    )
    {
        super();

        this.UNI_ID = UNI_ID;
        this.PRICE = PRICE;
        this.POLL_DIV_CD = POLL_DIV_CD;
        this.OS_NM = OS_NM;
        this.DISTANCE = DISTANCE;
        this.GIS_X_COOR = GIS_X_COOR;
        this.GIS_Y_COOR = GIS_Y_COOR;
    }

    // 수신 Parsing
    public FarListModel(JSONObject obj)
    {
        try
        {
            if (obj.has("UNI_ID"))
                setUNI_ID(obj.getString("UNI_ID"));

            if (obj.has("PRICE"))
                setPRICE(obj.getInt("PRICE"));

            if (obj.has("POLL_DIV_CD"))
                setPOLL_DIV_CD(obj.getString("POLL_DIV_CD"));

            if (obj.has("OS_NM"))
                setOS_NM(obj.getString("OS_NM"));

            if (obj.has("DISTANCE"))
                setDISTANCE(obj.getDouble("DISTANCE"));

            if (obj.has("GIS_X_COOR"))
                setGIS_X_COOR(obj.getDouble("GIS_X_COOR"));

            if (obj.has("GIS_Y_COOR"))
                setGIS_Y_COOR(obj.getDouble("GIS_Y_COOR"));

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e("TEST", "===== FarListModel ERROR : ", e);
        }
    }

    public String getUNI_ID() {
        return UNI_ID;
    }

    public void setUNI_ID(String UNI_ID) {
        this.UNI_ID = UNI_ID;
    }

    public int getPRICE() {
        return PRICE;
    }

    public void setPRICE(int PRICE) {
        this.PRICE = PRICE;
    }

    public String getPOLL_DIV_CD() {
        return POLL_DIV_CD;
    }

    public void setPOLL_DIV_CD(String POLL_DIV_CD) {
        this.POLL_DIV_CD = POLL_DIV_CD;
    }

    public String getOS_NM() {
        return OS_NM;
    }

    public void setOS_NM(String OS_NM) {
        this.OS_NM = OS_NM;
    }

    public double getDISTANCE() {
        return DISTANCE;
    }

    public void setDISTANCE(double DISTANCE) {
        this.DISTANCE = DISTANCE;
    }

    public double getGIS_X_COOR() {
        return GIS_X_COOR;
    }

    public void setGIS_X_COOR(double GIS_X_COOR) {
        this.GIS_X_COOR = GIS_X_COOR;
    }

    public double getGIS_Y_COOR() {
        return GIS_Y_COOR;
    }

    public void setGIS_Y_COOR(double GIS_Y_COOR) {
        this.GIS_Y_COOR = GIS_Y_COOR;
    }
}
