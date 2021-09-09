package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AreaDetailActivity  extends AppCompatActivity
{
    String UNI_ID;  // 수신된 주유소ID
    String URL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent getIntent = getIntent();
        UNI_ID = getIntent.getStringExtra("UNI_ID");

        Log.d("TEST", "===== AreaDetailActivity (AREA 상세) UNI_ID : " + UNI_ID);

        URL = "http://www.opinet.co.kr/api/detailById.do?code=F982210415&id=" + UNI_ID + "&out=json";

        Log.d("TEST", "===== AreaDetailActivity (AREA 상세) URL : " + URL);
    }
}
