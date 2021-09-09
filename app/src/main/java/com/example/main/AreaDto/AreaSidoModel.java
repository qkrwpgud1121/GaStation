package com.example.main.AreaDto;

/**
 * 시, 도 모델
 */
public class AreaSidoModel
{
    private String sidoName;        // 시,도 이름 ex) 서울시, 경기도
    private String sidoCode;       // 시,도 코드 ex) 01, 02

    public AreaSidoModel(String sidoName, String sidoCode)
    {
        super();

        this.sidoName = sidoName;
        this.sidoCode = sidoCode;
    }

    public String getSidoName() {
        return sidoName;
    }

    public void setSidoName(String sidoName) {
        this.sidoName = sidoName;
    }

    public String getSidoCode() {
        return sidoCode;
    }

    public void setSidoCode(String sidoCode) {
        this.sidoCode = sidoCode;
    }
}
