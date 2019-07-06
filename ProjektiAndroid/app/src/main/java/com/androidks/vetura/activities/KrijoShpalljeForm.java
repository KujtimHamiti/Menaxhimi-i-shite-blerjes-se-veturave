package com.androidks.vetura.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Spinner;

import com.androidks.vetura.R;
import com.androidks.vetura.adapter.CarListAdapter;
import com.androidks.vetura.beans.Vetura;
import com.androidks.vetura.database.SQLiteHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class KrijoShpalljeForm extends AppCompatActivity {



    Button btnKrijoShpallje, btnUpload;
    EditText etTitulli, etViti, etKilometrazha, etKomuna, etTel, etPershkrimi;
    ImageView ivUploadFoto;
    Spinner spinner;

    SQLiteHelper sqLiteHelper;
    FirebaseAuth mAuth;

    String llojiMakines = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krijo_shpallje_form);


        init();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.lloji_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                llojiMakines = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mAuth = FirebaseAuth.getInstance();

        sqLiteHelper = new SQLiteHelper(this);

        sqLiteHelper.queryData(sqLiteHelper.CREATE_TABLE_VETURA);

        btnKrijoShpallje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    Vetura vetura= new Vetura(
                            etTitulli.getText().toString(),
                            etViti.getText().toString(),
                            etKilometrazha.getText().toString(),
                            etKomuna.getText().toString(),
                            etTel.getText().toString(),
                            etPershkrimi.getText().toString(),
                            llojiMakines,
                            imageViewToByte(ivUploadFoto),
                            mAuth.getCurrentUser().getEmail());

                    sqLiteHelper.insertVetura(vetura);
                    Toast.makeText(getApplicationContext(), "Shpallja u krijua me sukses!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(KrijoShpalljeForm.this,MainActivity.class));
                    finish();

                }catch (Exception ex){
                    Toast.makeText(KrijoShpalljeForm.this,"Shpallja nuk u krijua", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(KrijoShpalljeForm.this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },999);
            }
        });



    }


    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 999){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,999);
            }
            else{
                Toast.makeText(this, "You dont have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 999 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ivUploadFoto.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void init(){

        etTitulli = (EditText)findViewById(R.id.etTitulliShpallje);
        etViti = (EditText)findViewById(R.id.etViti);
        etKilometrazha = (EditText)findViewById(R.id.etKilometrazha);
        etKomuna = (EditText)findViewById(R.id.etKomuna);
        etTel= (EditText)findViewById(R.id.etTel);
        etPershkrimi= (EditText)findViewById(R.id.etPershkrimi);
        btnKrijoShpallje = (Button)findViewById(R.id.btnKrijoShpallje);
        btnUpload = (Button)findViewById(R.id.btnUpload);
        ivUploadFoto = (ImageView)findViewById(R.id.ivUploadFoto);
        spinner = (Spinner)findViewById(R.id.spinner);

    }

}