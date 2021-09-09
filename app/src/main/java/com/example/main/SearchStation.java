package com.example.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchStation extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // PUTEXTRA TAG
    public static String PUTEXTRA_UNI_ID;   // 태그 - 주유소 ID

    // 받은값
    private String getIntent_UNI_ID;

    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_station);

        Button Na = findViewById(R.id.B_Navi);
        Button Ca = findViewById(R.id.B_Call);

        Ca.setOnClickListener (new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setAction((Intent.ACTION_DIAL));
                intent.setData(Uri.parse("tel:010-4073-1142"));
                startActivity(intent);
            }
        });

        Na.setOnClickListener (new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                try {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.skt.tmap.ku");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch(Exception e){
                    String url = "market://details?id=com.skt.tmap.ku";
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }

            }
        });



        SupportMapFragment mapFragment = (SupportMapFragment)
        getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /**
         * getIntent
         */

        Intent getIntent = getIntent();
        getIntent_UNI_ID = getIntent.getStringExtra(PUTEXTRA_UNI_ID);

        Log.i("TEST", "===== SearchStation getIntent_UNI_ID : " + getIntent_UNI_ID);

        URL = "http://www.opinet.co.kr/api/detailById.do?code=F982210415&id=" + getIntent_UNI_ID + "&out=json";

        Log.i("TEST", "===== SearchStation URL : " + URL);

    }

    public void getJsonData()
    {
        /**
         * GPS좌표 가져오기
         */

        String queryUrl="http://www.opinet.co.kr/api/detailById.do?code=F982210415&id=" + getIntent_UNI_ID + "&out=json";

        Log.d("TEST", " ===== getJsonData (네트워크 파싱 시작) : " + queryUrl);

        try {
            java.net.URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            InputStreamReader inputStreamReader = new InputStreamReader(is);
            Stream<String> streamOfString= new BufferedReader(inputStreamReader).lines();
            String streamToString = streamOfString.collect(Collectors.joining());




            Log.d("TEST", " ===== getJsonData (네트워크 파싱 값 1) : " + streamToString);

            JSONObject jsonObject_original = new JSONObject(streamToString);

            Log.i("TEST", "===== JSON = " + jsonObject_original.toString());

            JSONObject jsonObject_result = (JSONObject) jsonObject_original.get("RESULT");

            Log.i("TEST", "===== JSON (jsonObject_result) = " + jsonObject_result.toString());

            JSONArray jsonObject_oil = (JSONArray) jsonObject_result.get("OIL");

            Log.i("TEST", "===== JSON (jsonObject_oil) = " + jsonObject_oil.toString());

        }
        catch (Exception e)
        {
            Log.e("TEST", "===== getJsonData ERROR : ", e);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng SEOUL = new LatLng(37.56, 127.00);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("대한민국 수도");
        mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


}