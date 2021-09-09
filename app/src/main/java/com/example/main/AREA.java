package com.example.main;

import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import com.example.main.AreaAdapter.AreaListAdapter;
import com.example.main.AreaAdapter.ProductAdapter;
import com.example.main.AreaAdapter.SidoAdapter;
import com.example.main.AreaDto.AreaListModel;
import com.example.main.AreaDto.AreaSidoModel;
import com.example.main.AreaDto.ProductTypeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AREA extends AppCompatActivity {

    ArrayAdapter<CharSequence> adt1, adt2;
    Button Se;


    XmlPullParser xpp;

    String key="F982210415";
    String data;

    // 시 도
    Spinner spinner1;
    SidoAdapter sidoAdapter;
    ArrayList<AreaSidoModel> arrayList_Sido;

    // 제품
    Spinner spinner2;
    ProductAdapter productAdapter;
    ArrayList<ProductTypeModel> arrayList_product;

    // 선택된 값
    AreaSidoModel selectedSidoModel;    // 선택된 시,도
    ProductTypeModel selectedProductTypeModel; // 선택된 제품값 (휘발유, 경유)

    // 하단 리스트
    ListView lv_areaList;
    AreaListAdapter areaListAdapter;
    ArrayList<AreaListModel> arrayList_areaList = new ArrayList<>();

    // 네트워크 종료 핸들러
    ParsingEndHandler handler_response = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r_e);

        Log.d("TEST", "===== AREA");


        /**
         * findViewById
         */
        spinner1 = (Spinner)findViewById(R.id.BChoice);
        spinner2 = (Spinner)findViewById(R.id.SChoice);
        lv_areaList = (ListView) findViewById(R.id.lv_areaList);

        /**
         * 초기화
         */
        handler_response = new ParsingEndHandler();
        // 시, 도 리스트 만들기
        arrayList_Sido = new ArrayList<>();
        arrayList_areaList = new ArrayList<>();


        AreaSidoModel dto1 = new AreaSidoModel("서울시", "01");
        AreaSidoModel dto2 = new AreaSidoModel("경기도", "02");
        AreaSidoModel dto3 = new AreaSidoModel("강원도", "03");
        AreaSidoModel dto4 = new AreaSidoModel("충북", "04");
        AreaSidoModel dto5 = new AreaSidoModel("충남", "05");
        AreaSidoModel dto6 = new AreaSidoModel("전북", "06");
        AreaSidoModel dto7 = new AreaSidoModel("전남", "07");
        AreaSidoModel dto8 = new AreaSidoModel("경북", "08");
        AreaSidoModel dto9 = new AreaSidoModel("경남", "09");
        AreaSidoModel dto10 = new AreaSidoModel("부산", "10");
        AreaSidoModel dto11 = new AreaSidoModel("제주", "11");
        AreaSidoModel dto12 = new AreaSidoModel("대구시", "14");
        AreaSidoModel dto13 = new AreaSidoModel("인천시", "15");
        AreaSidoModel dto14 = new AreaSidoModel("대전시", "17");
        AreaSidoModel dto15 = new AreaSidoModel("울산시", "18");
        AreaSidoModel dto16 = new AreaSidoModel("세종시", "19");

        arrayList_Sido.add(dto1);
        arrayList_Sido.add(dto2);
        arrayList_Sido.add(dto3);
        arrayList_Sido.add(dto4);
        arrayList_Sido.add(dto5);
        arrayList_Sido.add(dto6);
        arrayList_Sido.add(dto7);
        arrayList_Sido.add(dto8);
        arrayList_Sido.add(dto9);
        arrayList_Sido.add(dto10);
        arrayList_Sido.add(dto11);
        arrayList_Sido.add(dto12);
        arrayList_Sido.add(dto13);
        arrayList_Sido.add(dto14);
        arrayList_Sido.add(dto15);
        arrayList_Sido.add(dto16);

        for (int i=0; i<arrayList_Sido.size(); i++)
        {
            Log.i("TEST", "===== 시도 Name : " + arrayList_Sido.get(i).getSidoName() + " / " + arrayList_Sido.get(i).getSidoCode());

        }

        // 어댑터
        sidoAdapter = new SidoAdapter(this, R.layout.row_textonly, R.id.row_tv_areaName, arrayList_Sido);
        spinner1.setAdapter(sidoAdapter);
        sidoAdapter.notifyDataSetChanged();

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedSidoModel = new AreaSidoModel(arrayList_Sido.get(position).getSidoName(), arrayList_Sido.get(position).getSidoCode());

                Log.i("TEST", "===== 스피너에서 선택 (시,도) : " + selectedSidoModel.getSidoName() + " / " + selectedSidoModel.getSidoCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 어댑터 끝

        // 제품
        arrayList_product = new ArrayList<>();

        ProductTypeModel model1 = new ProductTypeModel("휘발유", "B027");
        ProductTypeModel model2 = new ProductTypeModel("경유", "D047");
        ProductTypeModel model3 = new ProductTypeModel("고급휘발유", "B034");
        ProductTypeModel model4 = new ProductTypeModel("실내등유", "B027");
        ProductTypeModel model5 = new ProductTypeModel("자동차부탄", "K015");

        arrayList_product.add(model1);
        arrayList_product.add(model2);
        arrayList_product.add(model3);
        arrayList_product.add(model4);
        arrayList_product.add(model5);

        // 어댑터
        productAdapter = new ProductAdapter(this, R.layout.row_producttype, R.id.tv_productName, arrayList_product);
        spinner2.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedProductTypeModel = new ProductTypeModel(arrayList_product.get(position).getproductName(), arrayList_product.get(position).getproductCode());

                Log.i("TEST", "===== 스피너에서 선택 (제품) : " + selectedProductTypeModel.getproductName() + " / " + selectedProductTypeModel.getproductCode());

                String queryUrl="http://www.opinet.co.kr/api/lowTop10.do?out=xml&code=F982210415&prodcd=" + selectedProductTypeModel.getproductCode() + "&area=" + selectedSidoModel.getSidoCode() + "&cnt=2";

                Log.i("TEST", "===== QUERY : " + queryUrl);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // 하단 리스트
        // 어댑터
        areaListAdapter = new AreaListAdapter(this, R.layout.row_arealist, arrayList_areaList);
        lv_areaList.setAdapter(areaListAdapter);
        lv_areaList.setOnItemClickListener(onItemClickListener);
        areaListAdapter.notifyDataSetChanged();


        Se = (Button)findViewById(R.id.Search);

        adt1 = ArrayAdapter.createFromResource(this, R.array.spinner_do, R.layout.support_simple_spinner_dropdown_item);
        adt1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spinner1.setAdapter(adt1);




//        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                String area = (String) spinner1.getItemAtPosition(position);
//
//                switch (area){
//                    case "서울시":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_seoul, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "부산시":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_busan, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "대구시":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_deagu, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "인천시":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_incheon, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "광주시":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_gwangju, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "대전시":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_daejeon, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "울산시":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_ulsan, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "세종시":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_sejong, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "경기도":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_gyeonggi, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "강원도":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_gangwon, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "충청북도":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_chung_buk, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "충청남도":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_chung_nam, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "전라북도":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_jeon_buk, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "전라남도":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_jeon_nam, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "경상북도":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_gyeong_buk, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "경상남도":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_gyeong_nam, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                    case "제주도":
//                        adt2 = ArrayAdapter.createFromResource(AREA.this, R.array.spinner_do_jeju, R.layout.support_simple_spinner_dropdown_item);
//                        adt2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//                        spinner2.setAdapter(adt2);
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });



        Se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(AREA.this,AreaList.class);
                startActivity(i);*/
                switch (v.getId()){
                    case R.id.Search:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                getJsonData();
                            }
                        }).start();
                        break;
                }
            }



//            String getXmlData()
//            {
//                StringBuffer buffer=new StringBuffer();
//                /*String str= (text.Stext.getText().toString();*/
//                /*String location = URLEncoder.encode(str);*/
//                String query="%EC%A0%84%EB%A0%A5%EB%A1%9C";
//
//                String queryUrl="http://www.opinet.co.kr/api/areaCode.do?out=xml&code=F982210415&area01";
//
//                try{
//                    URL url= new URL(queryUrl);
//                    InputStream is= url.openStream();
//
//                    XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
//                    XmlPullParser xpp= factory.newPullParser();
//                    xpp.setInput( new InputStreamReader(is, "UTF-8") );
//
//                    String tag;
//
//                    xpp.next();
//                    int eventType= xpp.getEventType();
//                    while( eventType != XmlPullParser.END_DOCUMENT ){
//                        switch( eventType ){
//                            case XmlPullParser.START_DOCUMENT:
//                                buffer.append("파싱 시작...\n\n");
//                                break;
//
//                            case XmlPullParser.START_TAG:
//                                tag= xpp.getName();
//
//                                if(tag.equals("OIL")) ;
//                                else if(tag.equals("AREA_CD")){
//                                    buffer.append("지역코드 : ");
//                                    xpp.next();
//                                    buffer.append(xpp.getText());
//                                    buffer.append("\n");
//                                }
//                                else if(tag.equals("AREA_NM")){
//                                    buffer.append("지역명 : ");
//                                    xpp.next();
//                                    buffer.append(xpp.getText());
//                                    buffer.append("\n");
//                                }
//                                /*else if(tag.equals("cpId")){
//                                    buffer.append("충전소ID :");
//                                    xpp.next();
//                                    buffer.append(xpp.getText());
//                                    buffer.append("\n");
//                                }
//                                else if(tag.equals("cpNm")){
//                                    buffer.append("충전기 명칭 :");
//                                    xpp.next();
//                                    buffer.append(xpp.getText());
//                                    buffer.append("\n");
//                                }
//                                else if(tag.equals("cpStat")){
//                                    buffer.append("충전기 상태 코드 :");
//                                    xpp.next();
//                                    buffer.append(xpp.getText());
//                                    buffer.append("\n");
//                                }
//                                else if(tag.equals("cpTp")){
//                                    buffer.append("충전 방식 :");
//                                    xpp.next();
//                                    buffer.append(xpp.getText());
//                                    buffer.append("  ,  ");
//                                }
//                                else if(tag.equals("csId")){
//                                    buffer.append("충전소 ID :");
//                                    xpp.next();
//                                    buffer.append(xpp.getText());
//                                    buffer.append("\n");
//                                }
//                                else if(tag.equals("cpNm")){
//                                    buffer.append("충전소 명칭 :");
//                                    xpp.next();
//                                    buffer.append(xpp.getText());
//                                    buffer.append("\n");
//                                }
//                                else if(tag.equals("lat")){
//                                    buffer.append("위도 :");
//                                    xpp.next();
//                                    buffer.append(xpp.getText());
//                                    buffer.append("\n");
//                                }
//                                else if(tag.equals("longi")){
//                                    buffer.append("경도 :");
//                                    xpp.next();
//                                    buffer.append(xpp.getText());
//                                    buffer.append("\n");
//                                }
//                                else if(tag.equals("statUpdateDatetime")){
//                                    buffer.append("충전기상태갱신시각 :");
//                                    xpp.next();
//                                    buffer.append(xpp.getText());
//                                    buffer.append("\n");
//                                }*/
//                                break;
//
//                            case XmlPullParser.TEXT:
//                                break;
//
//                            case XmlPullParser.END_TAG:
//                                tag= xpp.getName();
//
//                                if(tag.equals("OIL")) buffer.append("\n");
//                                break;
//                        }
//
//                        eventType= xpp.next();
//                    }
//
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                buffer.append("파싱 끝\n");
//                return buffer.toString();
//
//            }
        });
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("TEST", " ===== onItemClickListener (리스트뷰 선택) : " + position + " / " + arrayList_areaList.get(position).getUNI_ID());

            Intent i = new Intent(AREA.this, SearchStation.class);
            i.putExtra(SearchStation.PUTEXTRA_UNI_ID, arrayList_areaList.get(position).getUNI_ID());
            startActivity(i);
        }
    };

    public void getJsonData()
    {
        String queryUrl="http://www.opinet.co.kr/api/lowTop10.do?out=json&code=F982210415&prodcd=" + selectedProductTypeModel.getproductCode() + "&area=" + selectedSidoModel.getSidoCode() + "&cnt=";

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



            arrayList_areaList = new ArrayList<>();

            ArrayList<Object> arrayList_obj = getData(jsonObject_oil, new AreaListModel());

            for (int i=0; i<arrayList_obj.size(); i++)
            {
                arrayList_areaList.add((AreaListModel) arrayList_obj.get(i));
            }


            for (int i=0; i<arrayList_areaList.size(); i++)
            {
                Log.e("TEST", "===== arrayList_areaList (하단 리스트 데이터) : " + arrayList_areaList.get(i).getUNI_ID());
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

            for (int i=0; i<arrayList_areaList.size(); i++)
            {
                Log.e("TEST", "===== ParsingEndHandler arrayList_areaList (네트워크 수신 종료) : " + arrayList_areaList.get(i).getUNI_ID());
            }

            areaListAdapter = new AreaListAdapter(AREA.this, R.layout.row_arealist, arrayList_areaList);
            lv_areaList.setAdapter(areaListAdapter);
            areaListAdapter.notifyDataSetChanged();
        }
    };

    Runnable run = new Runnable(){
        public void run(){

            Log.e("TEST", "===== Runnable (네트워크 수신 종료 Runnable)");

            arrayList_areaList.clear();

            arrayList_areaList.add(new AreaListModel("1", 1, "", "", "", "", 0, 0, 0, 0));

            areaListAdapter.setList(arrayList_areaList);
        }
    };




    private ArrayList<Object> getData(JSONArray listArray, Object _type) {
        ArrayList<Object> list = new ArrayList<Object>();

        try {
            if(_type instanceof AreaListModel) {

                for(int i=0, j=listArray.length(); i<j; i++) {
                    JSONObject listObject = (JSONObject) listArray.get(i);

                    list.add(new AreaListModel(listObject));

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;

    }
}