package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button Fr;
    Button Ar;
    Button Fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        Fr = findViewById(R.id.Far);
        Ar = findViewById(R.id.Area);
        Fa = findViewById(R.id.Fav);

        Fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,FAR.class);
                startActivity(i);
            }
        });

        Ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this,AREA.class);
                startActivity(i2);
            }
        });

        Fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(MainActivity.this,FAV.class);
                startActivity(i3);
            }
        });
    }
}