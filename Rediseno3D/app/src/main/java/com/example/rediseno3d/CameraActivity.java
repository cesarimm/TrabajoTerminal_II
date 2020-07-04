package com.example.rediseno3d;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import static android.content.ContentValues.TAG;


public class CameraActivity extends AppCompatActivity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public static final int UNA_IMAGEN = 1;
    public static final int DOS_IMAGEN = 2;
    public static final int TRES_IMAGEN = 3;

    ///lista con los nombre del arrayList
    private static ArrayList<String> listaImagenes = new ArrayList<>();

    private Camera mCamera;
    private CameraPreview mPreview;
    private int tipo, contador=0;
    Button listo, captureButton;



    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            //Toast.makeText(getApplicationContext(), "Ya fue creado: "+pictureFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Capturada", Toast.LENGTH_LONG).show();
            if (pictureFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();
       ///
        Intent in = getIntent();
        Bundle b = in.getExtras();

        if(b!=null){
            tipo = (int) b.get("TIPO");
            Toast.makeText(getApplicationContext(), "Vis "+tipo, Toast.LENGTH_LONG).show();
        }


        ///Iniciar lo que esta captando la camara
        this.iniciarCamara();
              listo =  (Button) findViewById(R.id.btnListo);
               captureButton = (Button) findViewById(R.id.button_capture);

              listo.setVisibility(View.INVISIBLE);
              captureButton.setOnClickListener(
                      new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                             //Capturar la fotografía
                               capturarImagen();
                              //Reiniciar la camara
                              iniciarCamara();
                              contador++;
                              //listo.setVisibility(View.VISIBLE);
                              if(tipo==1){
                                  ///Objetos solidos aquí se va a guardar de una hasta tres imagenes
                                  if(contador==1)
                                      listo.setVisibility(View.VISIBLE);

                                  if(contador==3){
                                      captureButton.setVisibility(View.INVISIBLE);
                                  }

                              }else if(tipo==2){
                                   //Cilindros unicamente una imagen
                                  if(contador==1){
                                      listo.setVisibility(View.VISIBLE);
                                      captureButton.setVisibility(View.INVISIBLE);
                                  }

                              }else if(tipo==3){
                                  //Donas unicamente 2 imagenes
                                  if(contador==2){
                                      listo.setVisibility(View.VISIBLE);
                                      captureButton.setVisibility(View.INVISIBLE);
                                  }

                              }else if(tipo==4){
                                  ////Donas unicamente 3 imagenes
                                  if(contador==3){
                                      listo.setVisibility(View.VISIBLE);
                                      captureButton.setVisibility(View.INVISIBLE);
                                  }
                              }
                          }
                      }
              );


              listo.setOnClickListener(
                      new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              Toast.makeText(getApplicationContext(), "Hola: "+contador, Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(getApplicationContext(), ProgresoActivity.class);
                              intent.putExtra("Lista", CameraActivity.listaImagenes);
                              intent.putExtra("Tipo", tipo);
                              //CameraActivity.listaImagenes.clear();
                              startActivity(intent);
                          }
                      }
              );
    }

   private void iniciarCamara(){
       mCamera = getCameraInstance();
       mCamera.setDisplayOrientation(90);
       mPreview = new CameraPreview(this, mCamera);
       FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
       preview.addView(mPreview);

   }

   private void liberarCamara(){
        if(mCamera!=null){
            mCamera.release();
            mCamera=null;
        }

   }



    public void capturarImagen(){
        mCamera.takePicture(null, null, mPicture);
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }


    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        // File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
        //       Environment.DIRECTORY_PICTURES), "MyCameraApp");

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()+"/R3D/", "imagenes");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Creacion del nombre de la imagen
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }
        listaImagenes.add(mediaFile.getName());
        return mediaFile;
    }


    @Override protected void onStart() {
        super.onStart();

    }

    @Override protected void onResume() {
        super.onResume();

    }

    @Override protected void onPause() {
        super.onPause();
        liberarCamara();

    }

    @Override protected void onRestart() {
        super.onRestart();
        iniciarCamara();

    }

    @Override protected void onDestroy() {
        super.onDestroy();
         liberarCamara();
    }
    
}