package com.example.main.AreaDto;

/**
 * 제품 모델
 */
public class ProductTypeModel
{
    private String productName;        // 
    private String productCode;       // 시,도 코드 ex) 01, 02

    public ProductTypeModel(String productName, String productCode)
    {
        super();

        this.productName = productName;
        this.productCode = productCode;
    }

    public String getproductName() {
        return productName;
    }

    public void setproductName(String productName) {
        this.productName = productName;
    }

    public String getproductCode() {
        return productCode;
    }

    public void setproductCode(String productCode) {
        this.productCode = productCode;
    }
}
