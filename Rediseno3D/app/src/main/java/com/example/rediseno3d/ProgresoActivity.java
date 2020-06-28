package com.example.rediseno3d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProgresoActivity extends AppCompatActivity {

    TextView tv;
    ArrayList<String> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progreso);

        getSupportActionBar().hide();
        ///
        Intent in = getIntent();
        Bundle b = in.getExtras();

        if(b!=null){
            lista = (ArrayList<String>) b.get("Lista");
        }

        String aux = "";

        for(int i=0;i<lista.size();i++){
            aux+=lista.get(i)+" \n";
        }


        tv =(TextView) findViewById(R.id.textViewArchivos);
        tv.setText(aux);
    }
}
