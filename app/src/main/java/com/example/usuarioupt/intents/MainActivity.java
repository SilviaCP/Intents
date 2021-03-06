package com.example.usuarioupt.intents;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int CAMERA_PIC_REQUEST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //_Funcionalidad del boton btn_call
        Button btn_llamar = (Button)findViewById(R.id.btn_call);

        btn_llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:999555888");

                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

                startActivity(callIntent);
            }
        });

        //_Funcionalidad del boton btn_location
        Button btn_location = (Button)findViewById(R.id.btn_location);

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location = Uri.parse("geo:0,0?q=Universidad+Privada+de+Tacna,+Granada,+Tacna");

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

                if(mapIntent.resolveActivity(getPackageManager()) !=null){
                    startActivity(mapIntent);
                }else{
                    Toast.makeText(MainActivity.this
                            ,"No existe Actividad para esta accion"
                            , Toast.LENGTH_SHORT).show();
                }

                startActivity(mapIntent);
            }
        });

        //_Funcionalidad del boton btn_web
        Button btn_web = (Button)findViewById(R.id.btn_web);

        btn_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("http://www.upt.edu.pe");

                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                // Verifica que se puede llamar
                //a alguna actividad
                PackageManager packageManager = getPackageManager();

                List<ResolveInfo> activities = packageManager.queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);

                boolean isIntentSafe = activities.size()>0;

                String titulo = getResources().getString(R.string.chooser_title);

                Intent wChooser = Intent.createChooser(webIntent,titulo);
                if(isIntentSafe){
                    startActivity(wChooser);
                }
            }
        });

        //Funcionalidad del boton bnt_camera
        Button btn_camera = (Button)findViewById(R.id.btn_camera);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CAMERA_PIC_REQUEST){
            if(resultCode == RESULT_OK){
                Bitmap imagen = (Bitmap)data.getExtras().get("data");

                ImageView foto = (ImageView)findViewById(R.id.img_prev);
                foto.setImageBitmap(imagen);
            }
        }
    }
}
