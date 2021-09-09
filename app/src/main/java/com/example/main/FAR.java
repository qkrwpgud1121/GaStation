package com.example.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.main.ExportLibrary.GPSConvert.GeoPoint;
import com.example.main.ExportLibrary.GPSConvert.GeoTrans;
import com.example.main.FarAdapter.FarAdapter;
import com.example.main.FarAdapter.FarListAdapter;
import com.example.main.FarAdapter.FarProductAdapter;
import com.example.main.FarDto.FarListModel;
import com.example.main.FarDto.FarModel;
import com.example.main.FarDto.FarProductTypeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FAR extends AppCompatActivity {

    private GpsTracker gpsTracker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    ArrayAdapter<CharSequence> adt1, adt2;
    Button Se;

    XmlPullParser xpp;

    String key="F982210415";
    String data;

    Spinner spinner1;
    FarAdapter farAdapter;
    ArrayList<FarModel> arrayList_Far;

    Spinner spinner2;
    FarProductAdapter farproductAdapter;
    ArrayList<FarProductTypeModel> arrayList_product;

    FarModel selectedFarModel;    // 선택된 시,도
    FarProductTypeModel selectedProductTypeModel; // 선택된 제품값 (휘발유, 경유)

    ListView lv_farList;
    FarListAdapter FarListAdapter;
    ArrayList<FarListModel> arrayList_Farlist = new ArrayList<>();

    TextView MyLocation;


    // 네트워크 종료 핸들러
    FAR.ParsingEndHandler handler_response = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_r);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

        final TextView textview_address = (TextView)findViewById(R.id.MyLocation);

        gpsTracker = new GpsTracker(FAR.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        String address = getCurrentAddress(latitude, longitude);
        textview_address.setText(address);

        Toast.makeText(FAR.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();

        Log.d("TEST", "===== FAR");

        spinner1 = (Spinner)findViewById(R.id.productName);
        spinner2 = (Spinner)findViewById(R.id.distance);
        lv_farList = (ListView) findViewById(R.id.lv_farList);

        handler_response = new FAR.ParsingEndHandler();

        arrayList_Far = new ArrayList<>();
        arrayList_Farlist = new ArrayList<>();

        FarModel dto1 = new FarModel("휘발유", "B027");
        FarModel dto2 = new FarModel("경유", "D047");
        FarModel dto3 = new FarModel("고급휘발유", "B034");
        FarModel dto4 = new FarModel("실내등유", "B027");
        FarModel dto5 = new FarModel("자동차부탄", "K015");

        arrayList_Far.add(dto1);
        arrayList_Far.add(dto2);
        arrayList_Far.add(dto3);
        arrayList_Far.add(dto4);
        arrayList_Far.add(dto5);

        farAdapter = new FarAdapter(this, R.layout.row_far_textonly, R.id.row_far_tv_radiusName, arrayList_Far);
        spinner1.setAdapter(farAdapter);
        farAdapter.notifyDataSetChanged();

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFarModel = new FarModel(arrayList_Far.get(position).getFarproductName(), arrayList_Far.get(position).getFarproductCode());

                Log.i("TEST", "===== 스피너에서 선택 (유종) : " + selectedFarModel.getFarproductName() + " / " + selectedFarModel.getFarproductCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrayList_product = new ArrayList<>();

        FarProductTypeModel model1 = new FarProductTypeModel("5km", "5000");
        FarProductTypeModel model2 = new FarProductTypeModel("3km", "3000");
        FarProductTypeModel model3 = new FarProductTypeModel("1km", "1000");

        arrayList_product.add(model1);
        arrayList_product.add(model2);
        arrayList_product.add(model3);

        farproductAdapter = new FarProductAdapter(this, R.layout.row_far_producttype, R.id.tv_Far_productName, arrayList_product);
        spinner2.setAdapter(farproductAdapter);
        farproductAdapter.notifyDataSetChanged();

        // 1. 위경도 입력 (경도, 위도)
        GeoPoint in_pt = new GeoPoint(126.7992, 37.4752);
        System.out.println("===== geo in : xGeo="  + in_pt.getX() + ", yGeo=" + in_pt.getY());

        // 2. 위경도 -> GIS 좌표 변경
        GeoPoint tm_pt = GeoTrans.convert(GeoTrans.GEO, GeoTrans.KATEC, in_pt);
        System.out.println("===== tm : xTM=" + tm_pt.getX() + ", yTM=" + tm_pt.getY());

        // 3. GIS -> 위경도 좌표 변경
        GeoPoint out_pt = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, tm_pt);
        System.out.println("===== geo out : xGeo=" + out_pt.getX() + ", yGeo=" + out_pt.getY());

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedProductTypeModel = new FarProductTypeModel(arrayList_product.get(position).getradiusName(), arrayList_product.get(position).getradiusCode());

                Log.i("TEST", "===== 스피너에서 선택 (거리) : " + selectedProductTypeModel.getradiusName() + " / " + selectedProductTypeModel.getradiusCode());

                String queryUrl="http://www.opinet.co.kr/api/aroundAll.do?code=F982210415&x="+ tm_pt.getX() + "&y=" + tm_pt.getY() + "&radius=" + selectedProductTypeModel.getradiusCode() + "&sort=1&prodcd=" + selectedFarModel.getFarproductCode() + "&out=json";

                Log.i("TEST", "===== QUERY : " + queryUrl);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 하단 리스트
        // 어댑터
        FarListAdapter = new FarListAdapter(this, R.layout.row_farlist, arrayList_Farlist);
        lv_farList.setAdapter(FarListAdapter);
        lv_farList.setOnItemClickListener(onItemClickListener);
        FarListAdapter.notifyDataSetChanged();

        Se = (Button)findViewById(R.id.FarSearch);

//        adt1 = ArrayAdapter.createFromResource(this, R.array.spinner_do, R.layout.support_simple_spinner_dropdown_item);
//        adt1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        Se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.FarSearch:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                getJsonData();
                            }
                        }).start();
                        break;
                }
            }
        });
    }

    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(FAR.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(FAR.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(FAR.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(FAR.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(FAR.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(FAR.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(FAR.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(FAR.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }

    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(FAR.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("TEST", " ===== onItemClickListener (리스트뷰 선택) : " + position + " / " + arrayList_Farlist.get(position).getUNI_ID());

            Intent i = new Intent(FAR.this, Activity.class);
            i.putExtra(SearchStation.PUTEXTRA_UNI_ID, arrayList_Farlist.get(position).getUNI_ID());
            startActivity(i);
        }
    };

    public void getJsonData()
    {
        /**
         * GPS좌표 가져오기
         */

        // 1. 위경도 입력 (경도, 위도)
        GeoPoint in_pt = new GeoPoint(GpsTracker.latitude, GpsTracker.longitude);
        System.out.println("===== geo in : xGeo="  + in_pt.getX() + ", yGeo=" + in_pt.getY());

        // 2. 위경도 -> GIS 좌표 변경
        GeoPoint tm_pt = GeoTrans.convert(GeoTrans.GEO, GeoTrans.KATEC, in_pt);
        System.out.println("===== tm : xTM=" + tm_pt.getX() + ", yTM=" + tm_pt.getY());

        // 3. GIS -> 위경도 좌표 변경
        GeoPoint out_pt = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, tm_pt);
        System.out.println("===== geo out : xGeo=" + out_pt.getX() + ", yGeo=" + out_pt.getY());


        /**
         * GPS좌표 가져오기
         */

        String queryUrl="http://www.opinet.co.kr/api/aroundAll.do?code=F982210415&x="+ tm_pt.getX() + "&y=" + tm_pt.getY() + "&radius=" + selectedProductTypeModel.getradiusCode() + "&sort=1&prodcd=" + selectedFarModel.getFarproductCode() + "&out=json";

        Log.d("TEST", " ===== getJsonData (네트워크 파싱 시작) : " + queryUrl);

        try {
            URL url = new URL(queryUrl);
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



            arrayList_Farlist = new ArrayList<>();

            ArrayList<Object> arrayList_obj = getData(jsonObject_oil, new FarListModel());

            for (int i=0; i<arrayList_obj.size(); i++)
            {
                arrayList_Farlist.add((FarListModel) arrayList_obj.get(i));
            }


            for (int i=0; i<arrayList_Farlist.size(); i++)
            {
                Log.e("TEST", "===== arrayList_FarList (하단 리스트 데이터) : " + arrayList_Farlist.get(i).getUNI_ID());
            }




            handler_response.sendEmptyMessage(0);
        }
        catch (Exception e)
        {
            Log.e("TEST", "===== getJsonData ERROR : ", e);
        }
    }

    // 정보 수신 성공 시의 콜백 핸들러
    class ParsingEndHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            Log.e("TEST", "===== ParsingEndHandler (네트워크 수신 종료)");

            for (int i=0; i<arrayList_Farlist.size(); i++)
            {
                Log.e("TEST", "===== ParsingEndHandler arrayList_FarList (네트워크 수신 종료) : " + arrayList_Farlist.get(i).getUNI_ID());
            }

            FarListAdapter = new FarListAdapter(FAR.this, R.layout.row_farlist, arrayList_Farlist);
            lv_farList.setAdapter(FarListAdapter);
            FarListAdapter.notifyDataSetChanged();
        }
    };

    Runnable run = new Runnable(){
        public void run(){

            Log.e("TEST", "===== Runnable (네트워크 수신 종료 Runnable)");

            arrayList_Farlist.clear();

            arrayList_Farlist.add(new FarListModel("1", 1, "", "",  0, 0, 0));

            FarListAdapter.setList(arrayList_Farlist);
        }
    };

    private ArrayList<Object> getData(JSONArray listArray, Object _type) {
        ArrayList<Object> list = new ArrayList<Object>();

        try {
            if(_type instanceof FarListModel) {

                for(int i=0, j=listArray.length(); i<j; i++) {
                    JSONObject listObject = (JSONObject) listArray.get(i);

                    list.add(new FarListModel(listObject));

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;

    }
}