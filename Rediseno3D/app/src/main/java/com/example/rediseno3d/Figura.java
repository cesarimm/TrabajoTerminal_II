package com.example.rediseno3d;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public class Figura {

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;
    private ByteBuffer mIndexBuffer;

    private float vertices[];

    private float colors[];

    private byte indices[];


    public Figura(float vertices[], float colors[], byte indices[]){
        //Obtener datos del archivo Obj
        ///Mandar a llamar metodo
        this.vertices = vertices;
        this.colors = colors;
        this.indices = indices;


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



    public Figura(){

        //Obtener datos del archivo Obj
        vertices = new float[]{
                   - 1.0f, -1.0f, -1.0f,
                    1.0f, -1.0f, -1.0f,
                    1.0f,  1.0f, -1.0f,
                    -1.0f, 1.0f, -1.0f,
                    -1.0f, -1.0f,  1.0f,
                    1.0f, -1.0f,  1.0f,
                    1.0f,  1.0f,  1.0f,
                    -1.0f,  1.0f,  1.0f
        };

        colors= new float[(vertices.length/3)*4];

        indices = new byte[]{
            0, 4, 5, 0, 5, 1,
                    1, 5, 6, 1, 6, 2,
                    2, 6, 7, 2, 7, 3,
                    3, 7, 4, 3, 4, 0,
                    4, 7, 6, 4, 6, 5,
                    3, 0, 1, 3, 1, 2
        };


        ///Despues de llenar los datos correspondientes
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




    public static float[] getVertices(String archivo){
        float vertices[];
        String cadena="";
        ArrayList<String> verticesArray = new ArrayList<String>();

        try {
            FileReader f = new FileReader(Environment.getExternalStorageDirectory()+"/R3D/obj/"+archivo);
            BufferedReader b = new BufferedReader(f);

            try {
                while((cadena = b.readLine())!=null) {
                    if(cadena.length()>=1){
                        if(cadena.substring(0, 1).equals("v")){
                            verticesArray.add(cadena.replace("v ", ""));
                        }
                    }

                }
                b.close();
            } catch (IOException ex) {

            }

        } catch (FileNotFoundException ex) {

        }

        vertices=new float[verticesArray.size()*3];


        int cont=0;
        for (int i = 0; i < verticesArray.size(); i++) {
            //System.out.println(verticesArray.get(i));
            String[] palabras = verticesArray.get(i).split(" ");
            for(int j=0;j<palabras.length;j++){
                vertices[cont]=(Float.parseFloat(palabras[j])/200)-1;
                cont++;
            }
        }

        return vertices;
    }


    public static byte[] getIndices(String archivo){
        byte indices[];
        String cadena="";
        ArrayList<String> caras = new ArrayList<String>();

        try {
            FileReader f = new FileReader(Environment.getExternalStorageDirectory()+"/R3D/obj/"+archivo);
            BufferedReader b = new BufferedReader(f);

            try {
                while((cadena = b.readLine())!=null) {
                    if(cadena.length()>=1){
                        if(cadena.substring(0, 1).equals("f")){
                            caras.add(cadena.replace("f ", ""));
                        }
                    }

                }
                b.close();
            } catch (IOException ex) {

            }

        } catch (FileNotFoundException ex) {

        }

        indices= new byte[caras.size()*3];

        int cont=0;
        for (int i = 0; i < caras.size(); i++) {
            String[] palabras = caras.get(i).split(" ");
            for(int j=0;j<palabras.length;j++){
                indices[cont]= (byte) (Byte.parseByte(palabras[j])-(byte)1);
                cont++;
            }
        }

        return indices;
    }

}
