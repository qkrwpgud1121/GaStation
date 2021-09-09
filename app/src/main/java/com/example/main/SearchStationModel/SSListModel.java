package com.example.main.SearchStationDto;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class SSListModel implements Serializable
{

    public static final String CAR_WASH_YN = "Y";
    private String UNI_ID;  // 주유소ID
    private String POLL_DIV_CD; // 상표(SKE:SK에너지, GSC:GS칼텍스, HDO:현대오일뱅크, SOL:S-OIL, RTO:자영알뜰, RTX:고속도로알뜰, NHO:농협알뜰, ETC:자가상표, E1G: E1, SKG:SK가스 )
    private String GPOLL_DIV_CD;
    private String OS_NM; // 상표
    private String VAN_ADR;
    private String NEW_ADR;
    private String TEL;
    private String SIGUNCD;
    private String LPG_YN;
    private String MAINT_YN;
//    private String CAR_WASH_YN;
    private String KPETRO_YN;
    private String CVS_YN;
    private double GIS_X_COOR;  // X좌표
    private double GIS_Y_COOR;  // Y좌표

    public SSListModel()
    {
        super();
    }

    public SSListModel(
            String UNI_ID,
            String POLL_DIV_CD,
            String GPOLL_DIV_CD,
            String OS_NM,
            String VAN_ADR,
            String NEW_ADR,
            String TEL,
            String SIGUNCD,
            String LPG_YN,
            String MAINT_YN,
            String CAR_WASH_YN,
            String KPETRO_YN,
            String CVS_YN,
            double GIS_X_COOR,
            double GIS_Y_COOR
    )
    {
        super();

        this.UNI_ID = UNI_ID;
        this.POLL_DIV_CD = POLL_DIV_CD;
        this.GPOLL_DIV_CD = GPOLL_DIV_CD;
        this.OS_NM = OS_NM;
        this.VAN_ADR = VAN_ADR;
        this.NEW_ADR = NEW_ADR;
        this.TEL = TEL;
        this.SIGUNCD = SIGUNCD;
        this.LPG_YN = LPG_YN;
        this.MAINT_YN = MAINT_YN;
//        this.CAR_WASH_YN = CAR_WASH_YN;
        this.KPETRO_YN = KPETRO_YN;
        this.CVS_YN = CVS_YN;
        this.GIS_X_COOR = GIS_X_COOR;
        this.GIS_Y_COOR = GIS_Y_COOR;
    }

    // 수신 Parsing
    public SSListModel(JSONObject obj)
    {
        try
        {
            if (obj.has("UNI_ID"))
                setUNI_ID(obj.getString("UNI_ID"));

            if (obj.has("POLL_DIV_CD"))
                setPOLL_DIV_CD(obj.getString("POLL_DIV_CD"));

            if (obj.has("GPOLL_DIV_CD"))
                setGPOLL_DIV_CD(obj.getString("GPOLL_DIV_CD"));

            if (obj.has("OS_NM"))
                setOS_NM(obj.getString("OS_NM"));

            if (obj.has("VAN_ADR"))
                setVAN_ADR(obj.getString("VAN_ADR"));

            if (obj.has("NEW_ADR"))
                setNEW_ADR(obj.getString("NEW_ADR"));

            if (obj.has("TEL"))
                setTEL(obj.getString("TEL"));

            if (obj.has("SIGUNCD"))
                setSIGUNCD(obj.getString("SIGUNCD"));

            if (obj.has("LPG_YN"))
                setLPG_YN(obj.getString("LPG_YN"));

            if (obj.has("MAINT_YN"))
                setMAINT_YN(obj.getString("MAINT_YN"));

//            if (obj.has("CAR_WASH_YN"))
//                setCAR_WASH_YN(obj.getString("CAR_WASH_YN"));

            if (obj.has("KPETRO_YN"))
                setKPETRO_YN(obj.getString("KPETRO_YN"));

            if (obj.has("CVS_YN"))
                setCVS_YN(obj.getString("CVS_YN"));

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

    public String getPOLL_DIV_CD() {
        return POLL_DIV_CD;
    }

    public void setPOLL_DIV_CD(String POLL_DIV_CD) {
        this.POLL_DIV_CD = POLL_DIV_CD;
    }

    public String getGPOLL_DIV_CD() {
        return GPOLL_DIV_CD;
    }

    public void setGPOLL_DIV_CD(String GPOLL_DIV_CD) {
        this.GPOLL_DIV_CD = GPOLL_DIV_CD;
    }

    public String getOS_NM() {
        return OS_NM;
    }

    public void setOS_NM(String OS_NM) {
        this.OS_NM = OS_NM;
    }

    public String getVAN_ADR() {
        return VAN_ADR;
    }

    public void setVAN_ADR(String VAN_ADR) {
        this.VAN_ADR = VAN_ADR;
    }

    public String getNEW_ADR() {
        return NEW_ADR;
    }

    public void setNEW_ADR(String NEW_ADR) {
        this.NEW_ADR = NEW_ADR;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public String getSIGUNCD() {
        return SIGUNCD;
    }

    public void setSIGUNCD(String SIGUNCD) {
        this.SIGUNCD = SIGUNCD;
    }

    public String getLPG_YN() {
        return LPG_YN;
    }

    public void setLPG_YN(String LPG_YN) {
        this.LPG_YN = LPG_YN;
    }

    public String getMAINT_YN() {
        return MAINT_YN;
    }

    public void setMAINT_YN(String MAINT_YN) {
        this.MAINT_YN = MAINT_YN;
    }

//    public String getCAR_WASH_YN() {
//        return CAR_WASH_YN;
//    }
//
//    public void setCAR_WASH_YN(String CAR_WASH_YN) {
//        this.CAR_WASH_YN = CAR_WASH_YN;
//    }

    public String getKPETRO_YN() {
        return KPETRO_YN;
    }

    public void setKPETRO_YN(String KPETRO_YN) {
        this.KPETRO_YN = KPETRO_YN;
    }

    public String getCVS_YN() {
        return CVS_YN;
    }

    public void setCVS_YN(String CVS_YN) {
        this.CVS_YN = CVS_YN;
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
