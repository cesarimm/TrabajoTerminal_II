package com.example.rediseno3d;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ObjListActivity extends AppCompatActivity {

    ListView listaObj;
    ArrayAdapter<OBJ> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obj_list);

        listaObj = findViewById(R.id.lvOBJ);
        adapter = new OBJAdapter(this);
        listaObj.setAdapter(adapter);

    }
}
