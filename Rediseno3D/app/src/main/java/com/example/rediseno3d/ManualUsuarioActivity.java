package com.example.rediseno3d;

import Manual.AdapterInstruccion;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import Manual.Instruccion;

public class ManualUsuarioActivity extends AppCompatActivity {

    ListView listaInstrucciones;
    ArrayAdapter<Instruccion> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_usuario);

        listaInstrucciones = findViewById(R.id.lvManualUsuario);
        adapter = new AdapterInstruccion(this);
        listaInstrucciones.setAdapter(adapter);
    }
}
