package com.example.telaprincipal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void teladois(View view) {
        Intent in = new Intent(MainActivity.this, TelaDois.class);
        startActivity(in);
    }


    public void telatres(View view) {
        Intent in = new Intent(MainActivity.this, TelaTres.class);
        startActivity(in);
    }

    public void telaquatro(View view) {
        Intent in = new Intent(MainActivity.this, TelaQuatro.class);
        startActivity(in);
    }


    public void telacinco(View view) {
        Intent in = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(in);
    }


    public void telaseis (View view) {
        Intent in = new Intent(MainActivity.this, TelaSeis.class);
        startActivity(in);
    }
}