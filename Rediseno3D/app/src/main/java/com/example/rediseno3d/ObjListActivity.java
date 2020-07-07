package com.example.rediseno3d;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ObjListActivity extends AppCompatActivity {

    ListView listaObj;
    ArrayAdapter<OBJ> adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_obj_list);

        listaObj = findViewById(R.id.lvOBJ);
        adapter = new OBJAdapter(this);
        listaObj.setAdapter(adapter);

        listaObj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view.findViewById(R.id.lvTitulo);

                ///Obtener los datos para la libreria de OPENGL a partir del OBJ
                float vertices[]  = Figura.getVertices(textView.getText().toString());
                byte indices[]  = Figura.getIndices(textView.getText().toString());

                float colors[]= new float[(vertices.length/3)*4];

                for(int x=0;x<(vertices.length/3)*4;x++){
                    colors[x] = 0.5f;
                }

               // Toast.makeText(getApplicationContext(), "Item: "+textView.getText().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Visualizador.class);
                intent.putExtra("Vertices", vertices);
                intent.putExtra("Indices", indices);
                intent.putExtra("Colores", colors);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
