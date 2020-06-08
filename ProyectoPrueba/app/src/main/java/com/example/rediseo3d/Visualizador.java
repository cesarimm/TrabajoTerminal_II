package com.example.rediseo3d;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Visualizador extends AppCompatActivity {

    public class DemoRenderer implements GLSurfaceView.Renderer {

        private Cube cube = new Cube();
        private float rotation;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
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
            cube.draw(gl);
            gl.glLoadIdentity();
            rotation -= 0.15f;
        }

    }

    class Cube {

        private FloatBuffer mVertexBuffer;
        private FloatBuffer mColorBuffer;
        private ByteBuffer mIndexBuffer;
       // private IntBuffer mIndexBuffer;

        private float vertices[] = {

                /*-5.0f, -5.0f, -5.0f,
                5.0f, -5.0f, -5.0f,
                5.0f,  5.0f, -5.0f,
                -5.0f, 5.0f, -5.0f,
                -5.0f, -5.0f,  5.0f,
                5.0f, -5.0f,  5.0f,
                5.0f,  5.0f,  5.0f,
                -5.0f,  5.0f,  5.0f*/

               /* -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f,  1.0f, -1.0f,
                -1.0f, 1.0f, -1.0f,
                -1.0f, -1.0f,  1.0f,
                1.0f, -1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                -1.0f,  1.0f,  1.0f */

                /*.3f, .3f, .0f,
                .9f, .3f, .0f,
                .9f,  .9f, .0f,
                .3f, .9f, .0f,
                .6f, .6f,  .0f

                0.24f,0.11f, 0f,
                0.645f,0.115f, 0f,
                0.655f,1.505f, 0f,
                0.66f,1.505f, 0f,
                1.075f,1.52f, 0f,
                1.06f,1.935f, 0f,
                0.24f,1.935f, 0f,
                0.225f,1.92f, 0f,
                0.6f,1.32f,0f */

                1.535f,1.84f, 0f,
                1.59f,1.88f, 0f,
                1.64f,1.935f, 0f,
                1.755f,2.0f, 0f,
                1.8f,1.925f, 0f,
                1.775f,1.865f, 0f,
                1.73f,1.76f, 0f,
                1.82f,1.715f, 0f,
                1.905f,1.77f, 0f,
                1.995f,1.765f, 0f,
                2.005f,1.685f, 0f,
                1.915f,1.605f, 0f,
                1.92f,1.5f, 0f,
                1.94f,1.48f, 0f,
                2.06f,1.35f, 0f,
                2.08f,1.36f, 0f,
                2.15f,1.46f, 0f,
                2.14f,1.48f, 0f,
                2.12f,1.205f, 0f,
                2.145f,1.2f, 0f,
                2.15f,1.075f, 0f,
                2.12f,1.065f, 0f,
                2.06f,0.92f, 0f,
                2.07f,0.915f, 0f,
                2.14f,0.81f, 0f,
                2.135f,0.795f, 0f,
                1.94f,0.8f, 0f,
                1.935f,0.795f, 0f,
                1.85f,0.82f, 0f,
                1.865f,0.85f, 0f,
                1.905f,0.975f, 0f,
                1.915f,1.03f, 0f,
                1.645f,1.045f, 0f,
                1.625f,1.03f, 0f,
                1.615f,1.01f, 0f,
                1.615f,0.95f, 0f,
                1.595f,0.905f, 0f,
                1.575f,0.9f, 0f,
                1.555f,0.88f, 0f,
                1.545f,0.83f, 0f,
                1.415f,0.715f, 0f,
                1.38f,0.715f, 0f,
                1.36f,0.705f, 0f,
                1.345f,0.68f, 0f,
                1.29f,0.66f, 0f,
                1.23f,0.665f, 0f,
                1.175f,0.655f, 0f,
                1.11f,0.655f, 0f,
                1.02f,0.655f, 0f,
                0.98f,0.665f, 0f,
                0.935f,0.705f, 0f,
                0.925f,0.705f, 0f,
                0.895f,0.705f, 0f,
                0.885f,0.77f, 0f,
                0.805f,0.85f, 0f,
                0.77f,0.81f, 0f,
                0.74f,0.87f, 0f,
                0.705f,0.895f, 0f,
                0.74f,0.945f, 0f,
                0.72f,1.0f, 0f,
                0.695f,1.0f, 0f,
                0.675f,0.96f, 0f,
                0.65f,1.05f, 0f,
                0.67f,1.095f, 0f,
                0.7f,1.23f, 0f,
                0.655f,1.28f, 0f,
                0.685f,1.315f, 0f,
                0.695f,1.345f, 0f,
                0.68f,1.355f, 0f,
                0.71f,1.41f, 0f,
                0.755f,1.38f, 0f,
                0.76f,1.45f, 0f,
                0.8f,1.44f, 0f,
                0.72f,1.3f, 0f,
                0.935f,1.55f, 0f,
                0.96f,1.605f, 0f,
                0.985f,1.61f, 0f,
                0.995f,1.575f, 0f,
                1.285f,1.59f, 0f,
                1.345f,1.595f, 0f,
                1.345f,1.6f, 0f,
                1.52f,1.465f, 0f,
                1.54f,1.44f, 0f,
                1.59f,1.39f, 0f,
                1.615f,1.34f, 0f,
                1.605f,1.32f, 0f,
                1.615f,1.3f, 0f,
                1.645f,1.225f, 0f,
                1.925f,0.66f, 0f,
                1.935f,0.66f, 0f,
                1.935f,0.5f, 0f,
                1.94f,0.495f, 0f,
                1.765f,0.565f, 0f,
                1.78f,0.38f, 0f,
                1.78f,0.37f, 0f,
                1.66f,0.32f, 0f,
                1.64f,0.335f, 0f,
                1.52f,0.39f, 0f,
                1.49f,0.36f, 0f,
                1.36f,0.245f, 0f,
                1.365f,0.23f, 0f,
                1.485f,0.15f, 0f,
                1.51f,0.175f, 0f,
                1.22f,0.18f, 0f,
                1.21f,0.155f, 0f,
                1.1f,0.12f, 0f,
                1.075f,0.17f, 0f,
                1.0f,0.315f, 0f,
                0.94f,0.28f, 0f,
                0.895f,0.18f, 0f,
                0.815f,0.155f, 0f,
                0.795f,0.3f, 0f,
                0.77f,0.395f, 0f,
                0.67f,0.375f, 0f,
                0.59f,0.29f, 0f,
                0.5f,0.345f, 0f,
                0.515f,0.395f, 0f,
                0.58f,0.52f, 0f,
                0.505f,0.585f, 0f,
                0.4f,0.525f, 0f,
                0.37f,0.515f, 0f,
                0.3f,0.6f, 0f,
                0.315f,0.625f, 0f,
                0.395f,0.775f, 0f,
                0.39f,0.78f, 0f,
                0.34f,0.91f, 0f,
                0.33f,0.94f, 0f,
                0.35f,0.995f, 0f,
                0.255f,0.94f, 0f,
                0.235f,0.93f, 0f,
                0.165f,0.8f, 0f,
                0.19f,0.785f, 0f,
                0.165f,1.09f, 0f,
                0.135f,1.1f, 0f,
                0.295f,1.145f, 0f,
                0.29f,1.23f, 0f,
                0.315f,1.25f, 0f,
                0.345f,1.24f, 0f,
                0.355f,1.265f, 0f,
                0.225f,1.38f, 0f,
                0.19f,1.4f, 0f,
                0.2f,1.515f, 0f,
                0.285f,1.515f, 0f,
                0.4f,1.515f, 0f,
                0.405f,1.535f, 0f,
                0.3f,1.71f, 0f,
                0.295f,1.745f, 0f,
                0.42f,1.77f, 0f,
                0.45f,1.76f, 0f,
                0.565f,1.84f, 0f,
                0.54f,1.875f, 0f,
                0.56f,2.005f, 0f,
                0.6f,2.01f, 0f,
                0.71f,1.89f, 0f,
                0.79f,1.905f, 0f,
                0.815f,1.96f, 0f,
                0.94f,2.06f, 0f,
                0.98f,1.975f, 0f,
                1.09f,2.12f, 0f,
                1.115f,2.175f, 0f,
                1.22f,2.14f, 0f,
                1.235f,2.025f, 0f,
                1.335f,1.975f, 0f,
                1.295f,1.925f, 0f,
                1.41f,1.895f, 0f,
                1.465f,1.875f, 0f,
                1.505f,1.97f, 0f,
                1.515f,1.995f, 0f,
                1.49f,2.135f, 0f,
                1.43f,2.145f, 0f,
                1.38f,2.06f, 0f,
                0.855f,2.15f, 0f,
                0.795f,2.125f, 0f,
                1.14f,1.145f,0f
        };

        private float colors[] = new float[(vertices.length/3)*4];



        /*private byte indices[] = {

                /*0, 1, 4, 1, 2, 4,
                2, 3, 4, 3, 0, 4*/

                /*0, 4, 5, 0, 5, 1,
                1, 5, 6, 1, 6, 2,
                2, 6, 7, 2, 7, 3,
                3, 7, 4, 3, 4, 0,
                4, 7, 6, 4, 6, 5,
                3, 0, 1, 3, 1, 2

                (byte)0,(byte)1,(byte)8,
                (byte)1,(byte)2,(byte)8,
                (byte)2,(byte)3,(byte)8,
                (byte)3,(byte)4,(byte)8,
                (byte)4,(byte)5,(byte)8,
                (byte)5,(byte)6,(byte)8,
                (byte)6,(byte)7,(byte)8,
                (byte)0,(byte)7,(byte)8

                (byte)0,(byte)1,(byte)173,
                (byte)1,(byte)2,(byte)173,
                (byte)2,(byte)3,(byte)173,
                (byte)3,(byte)4,(byte)173,
                (byte)4,(byte)5,(byte)173,
                (byte)5,(byte)6,(byte)173,
                (byte)6,(byte)7,(byte)173,
                (byte)7,(byte)8,(byte)173,
                (byte)8,(byte)9,(byte)173,
                (byte)9,(byte)10,(byte)173,
                (byte)10,(byte)11,(byte)173,
                (byte)11,(byte)12,(byte)173,
                (byte)12,(byte)13,(byte)173,
                (byte)13,(byte)14,(byte)173,
                (byte)14,(byte)15,(byte)173,
                (byte)15,(byte)16,(byte)173,
                (byte)16,(byte)17,(byte)173,
                (byte)17,(byte)18,(byte)173,
                (byte)18,(byte)19,(byte)173,
                (byte)19,(byte)20,(byte)173,
                (byte)20,(byte)21,(byte)173,
                (byte)21,(byte)22,(byte)173,
                (byte)22,(byte)23,(byte)173,
                (byte)23,(byte)24,(byte)173,
                (byte)24,(byte)25,(byte)173,
                (byte)25,(byte)26,(byte)173,
                (byte)26,(byte)27,(byte)173,
                (byte)27,(byte)28,(byte)173,
                (byte)28,(byte)29,(byte)173,
                (byte)29,(byte)30,(byte)173,
                (byte)30,(byte)31,(byte)173,
                (byte)31,(byte)32,(byte)173,
                (byte)32,(byte)33,(byte)173,
                (byte)33,(byte)34,(byte)173,
                (byte)34,(byte)35,(byte)173,
                (byte)35,(byte)36,(byte)173,
                (byte)36,(byte)37,(byte)173,
                (byte)37,(byte)38,(byte)173,
                (byte)38,(byte)39,(byte)173,
                (byte)39,(byte)40,(byte)173,
                (byte)40,(byte)41,(byte)173,
                (byte)41,(byte)42,(byte)173,
                (byte)42,(byte)43,(byte)173,
                (byte)43,(byte)44,(byte)173,
                (byte)44,(byte)45,(byte)173,
                (byte)45,(byte)46,(byte)173,
                (byte)46,(byte)47,(byte)173,
                (byte)47,(byte)48,(byte)173,
                (byte)48,(byte)49,(byte)173,
                (byte)49,(byte)50,(byte)173,
                (byte)50,(byte)51,(byte)173,
                (byte)51,(byte)52,(byte)173,
                (byte)52,(byte)53,(byte)173,
                (byte)53,(byte)54,(byte)173,
                (byte)54,(byte)55,(byte)173,
                (byte)55,(byte)56,(byte)173,
                (byte)56,(byte)57,(byte)173,
                (byte)57,(byte)58,(byte)173,
                (byte)58,(byte)59,(byte)173,
                (byte)59,(byte)60,(byte)173,
                (byte)60,(byte)61,(byte)173,
                (byte)61,(byte)62,(byte)173,
                (byte)62,(byte)63,(byte)173,
                (byte)63,(byte)64,(byte)173,
                (byte)64,(byte)65,(byte)173,
                (byte)65,(byte)66,(byte)173,
                (byte)66,(byte)67,(byte)173,
                (byte)67,(byte)68,(byte)173,
                (byte)68,(byte)69,(byte)173,
                (byte)69,(byte)70,(byte)173,
                (byte)70,(byte)71,(byte)173,
                (byte)71,(byte)72,(byte)173,
                (byte)72,(byte)73,(byte)173,
                (byte)73,(byte)74,(byte)173,
                (byte)74,(byte)75,(byte)173,
                (byte)75,(byte)76,(byte)173,
                (byte)76,(byte)77,(byte)173,
                (byte)77,(byte)78,(byte)173,
                (byte)78,(byte)79,(byte)173,
                (byte)79,(byte)80,(byte)173,
                (byte)80,(byte)81,(byte)173,
                (byte)81,(byte)82,(byte)173,
                (byte)82,(byte)83,(byte)173,
                (byte)83,(byte)84,(byte)173,
                (byte)84,(byte)85,(byte)173,
                (byte)85,(byte)86,(byte)173,
                (byte)86,(byte)87,(byte)173,
                (byte)87,(byte)88,(byte)173,
                (byte)88,(byte)89,(byte)173,
                (byte)89,(byte)90,(byte)173,
                (byte)90,(byte)91,(byte)173,
                (byte)91,(byte)92,(byte)173,
                (byte)92,(byte)93,(byte)173,
                (byte)93,(byte)94,(byte)173,
                (byte)94,(byte)95,(byte)173,
                (byte)95,(byte)96,(byte)173,
                (byte)96,(byte)97,(byte)173,
                (byte)97,(byte)98,(byte)173,
                (byte)98,(byte)99,(byte)173,
                (byte)99,(byte)100,(byte)173,
                (byte)100,(byte)101,(byte)173,
                (byte)101,(byte)102,(byte)173,
                (byte)102,(byte)103,(byte)173,
                (byte)103,(byte)104,(byte)173,
                (byte)104,(byte)105,(byte)173,
                (byte)105,(byte)106,(byte)173,
                (byte)106,(byte)107,(byte)173,
                (byte)107,(byte)108,(byte)173,
                (byte)108,(byte)109,(byte)173,
                (byte)109,(byte)110,(byte)173,
                (byte)110,(byte)111,(byte)173,
                (byte)111,(byte)112,(byte)173,
                (byte)112,(byte)113,(byte)173,
                (byte)113,(byte)114,(byte)173,
                (byte)114,(byte)115,(byte)173,
                (byte)115,(byte)116,(byte)173,
                (byte)116,(byte)117,(byte)173,
                (byte)117,(byte)118,(byte)173,
                (byte)118,(byte)119,(byte)173,
                (byte)119,(byte)120,(byte)173,
                (byte)120,(byte)121,(byte)173,
                (byte)121,(byte)122,(byte)173,
                (byte)122,(byte)123,(byte)173,
                (byte)123,(byte)124,(byte)173,
                (byte)124,(byte)125,(byte)173,
                (byte)125,(byte)126,(byte)173,
                (byte)126,(byte)127,(byte)173,
                (byte)127,(byte)128,(byte)173,
                (byte)128,(byte)129,(byte)173,
                (byte)129,(byte)130,(byte)173,
                (byte)130,(byte)131,(byte)173,
                (byte)131,(byte)132,(byte)173,
                (byte)132,(byte)133,(byte)173,
                (byte)133,(byte)134,(byte)173,
                (byte)134,(byte)135,(byte)173,
                (byte)135,(byte)136,(byte)173,
                (byte)136,(byte)137,(byte)173,
                (byte)137,(byte)138,(byte)173,
                (byte)138,(byte)139,(byte)173,
                (byte)139,(byte)140,(byte)173,
                (byte)140,(byte)141,(byte)173,
                (byte)141,(byte)142,(byte)173,
                (byte)142,(byte)143,(byte)173,
                (byte)143,(byte)144,(byte)173,
                (byte)144,(byte)145,(byte)173,
                (byte)145,(byte)146,(byte)173,
                (byte)146,(byte)147,(byte)173,
                (byte)147,(byte)148,(byte)173,
                (byte)148,(byte)149,(byte)173,
                (byte)149,(byte)150,(byte)173,
                (byte)150,(byte)151,(byte)173,
                (byte)151,(byte)152,(byte)173,
                (byte)152,(byte)153,(byte)173,
                (byte)153,(byte)154,(byte)173,
                (byte)154,(byte)155,(byte)173,
                (byte)155,(byte)156,(byte)173,
                (byte)156,(byte)157,(byte)173,
                (byte)157,(byte)158,(byte)173,
                (byte)158,(byte)159,(byte)173,
                (byte)159,(byte)160,(byte)173,
                (byte)160,(byte)161,(byte)173,
                (byte)161,(byte)162,(byte)173,
                (byte)162,(byte)163,(byte)173,
                (byte)163,(byte)164,(byte)173,
                (byte)164,(byte)165,(byte)173,
                (byte)165,(byte)166,(byte)173,
                (byte)166,(byte)167,(byte)173,
                (byte)167,(byte)168,(byte)173,
                (byte)168,(byte)169,(byte)173,
                (byte)169,(byte)170,(byte)173,
                (byte)170,(byte)171,(byte)173,
                (byte)171,(byte)172,(byte)173,
                0,(byte)172,(byte)173


        };*/

        private byte indices[]  = {

        };

        public Cube() {

            for(int i=0;i<(vertices.length/3)*4;i++){
                colors[i] = 0.5f;
            }


            ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
            byteBuf.order(ByteOrder.nativeOrder());
            mVertexBuffer = byteBuf.asFloatBuffer();
            mVertexBuffer.put(vertices);
            mVertexBuffer.position(0);

             byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
             byteBuf.order(ByteOrder.nativeOrder());
             mColorBuffer = byteBuf.asFloatBuffer();
             mColorBuffer.put(colors);
             mColorBuffer.position(0);

            mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
            mIndexBuffer.put(indices);
            mIndexBuffer.position(0);
        }

        public void draw(GL10 gl) {

            gl.glFrontFace(GL10.GL_CW);

            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);

            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

            gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE,
                    mIndexBuffer);



            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

        }
    }


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new DemoRenderer());
        setContentView(view);
    }
}
