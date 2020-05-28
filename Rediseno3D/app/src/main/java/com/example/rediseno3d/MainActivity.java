package com.example.rediseno3d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(), "Funcionamiento  correcto", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "No funciona ", Toast.LENGTH_SHORT).show();
        }
    }

    public void abrirVentanaCamara(View view){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void abrirVentanaComoUtilizar(View view){
        Intent intent = new Intent(this, CargarImg.class);
        startActivity(intent);
    }

}
