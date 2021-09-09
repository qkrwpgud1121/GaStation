package com.example.main.FarDto;

public class FarModel
{
    private String FarproductName;        // 유종 ex) 휘발유, 경유
    private String FarproductCode;       // 유종 코드 ex) 휘발유:B027, 경유:D047, 고급휘발유: B034, 실내등유: C004, 자동차부탄: K015

    public FarModel(String FarproductName, String FarproductCode)
    {
        super();

        this.FarproductName = FarproductName;
        this.FarproductCode = FarproductCode;
    }

    public String getFarproductName() {
        return FarproductName;
    }

    public void setFarproductName(String FarproductName) {
        this.FarproductName = FarproductName;
    }

    public String getFarproductCode() {
        return FarproductCode;
    }

    public void setFarproductCode(String FarproductCode) {
        this.FarproductCode = FarproductCode;
    }
}
