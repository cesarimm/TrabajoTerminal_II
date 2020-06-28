package com.example.rediseno3d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.ChainHead;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class VistasMenuActivity extends AppCompatActivity {

    RadioGroup rgOpciones;
    Button btnContinuar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistas_menu);
        getSupportActionBar().hide();
        rgOpciones = (RadioGroup) findViewById(R.id.opciones);
        btnContinuar =(Button) findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int checkId = rgOpciones.getCheckedRadioButtonId();
                    if(checkId==-1){
                        Toast.makeText(getApplicationContext(), "Selecciona una opcion", Toast.LENGTH_SHORT).show();
                    }else{
                        buscarRadioButton(checkId);
                    }
            }
        });
    }

    private void buscarRadioButton(int checkId) {
        Intent intent;
        switch (checkId){
            case R.id.radioButton:
                 intent = new Intent(getApplicationContext(), CameraActivity.class);
                intent.putExtra("TIPO", 1);
                startActivity(intent);
                break;
            case R.id.radioButton2:
                 intent = new Intent(getApplicationContext(), CameraActivity.class);
                intent.putExtra("TIPO", 2);
                startActivity(intent);
                break;
            case R.id.radioButton3:
                intent = new Intent(getApplicationContext(), CameraActivity.class);
                intent.putExtra("TIPO", 3);
                startActivity(intent);
                break;
            case R.id.radioButton4:
                intent = new Intent(getApplicationContext(), CameraActivity.class);
                intent.putExtra("TIPO", 4);
                startActivity(intent);
                break;
        }

    }
}
