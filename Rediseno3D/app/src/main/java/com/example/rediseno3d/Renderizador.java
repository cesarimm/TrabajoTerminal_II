package com.example.rediseno3d;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Renderizador implements GLSurfaceView.Renderer {

    private Figura figura; //new Figura();
    private float rotation;

    public Renderizador(float vertices[], float colors[], byte indices[]){

         /*float vertices[] = {0.24f,0.11f, 0f,
                 0.645f,0.115f, 0f,
                 0.655f,1.505f, 0f,
                 0.66f,1.505f, 0f,
                 1.075f,1.52f, 0f,
                 1.06f,1.935f, 0f,
                 0.24f,1.935f, 0f,
                 0.225f,1.92f, 0f,
                 0.6f,1.32f,0f};

        float colors[]= new float[(vertices.length/3)*4];

          for(int i=0;i<(vertices.length/3)*4;i++){
           colors[i] = 0.5f;
         }

         byte indices[] ={
                 0,1,8,
                 1,2,8,
                 2,3,8,
                 3,4,8,
                 4,5,8,
                 5,6,8,
                 6,7,8,
                 0,7,8
         };*/

        figura = new Figura(vertices, colors, indices);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig Config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);
        // Enables depth testing.
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do.
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -10.0f);
        gl.glRotatef(rotation, 1.0f, 1.0f, 1.0f);
        figura.draw(gl);
        gl.glLoadIdentity();
        rotation -= .15f;
    }
}
