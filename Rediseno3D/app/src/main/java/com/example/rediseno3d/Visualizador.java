package com.example.rediseno3d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Visualizador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        float[] vertices={}, colores={};
        byte[] indices={};

        GLSurfaceView view = new GLSurfaceView(this);

        Intent in = getIntent();
        Bundle b = in.getExtras();

        if(b!=null){
            vertices = (float[]) b.get("Vertices");
            colores = (float[]) b.get("Colores");
            indices = (byte[]) b.get("Indices");
            Toast.makeText(getApplicationContext(), "Vis ", Toast.LENGTH_LONG).show();
        }

        view.setRenderer(new Renderizador(vertices, colores, indices));
        setContentView(view);


    }
}
