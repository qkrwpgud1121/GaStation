package com.example.main.FarDto;


public class FarProductTypeModel
{
    private String radiusName;        //거리 ex) 1km, 3km, 5km, 10km
    private String radiusCode;       // 거리 코드 ex)

    public FarProductTypeModel(String radiusName, String radiusCode)
    {
        super();

        this.radiusName = radiusName;
        this.radiusCode = radiusCode;
    }

    public String getradiusName() {
        return radiusName;
    }

    public void setradiusName(String radiusName) {
        this.radiusName = radiusName;
    }

    public String getradiusCode() {
        return radiusCode;
    }

    public void setradiusCode(String radiusCode) {
        this.radiusCode = radiusCode;
    }
}
