package com.androidks.vetura.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidks.vetura.activities.KrijoShpalljeForm;
import com.androidks.vetura.adapter.CarListAdapter;
import com.androidks.vetura.R;
import com.androidks.vetura.beans.Vetura;
import com.androidks.vetura.database.SQLiteHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListUser extends AppCompatActivity{


    ListView listView;
    CarListAdapter adapteri;
    ArrayList<Vetura> veturaArrayList;
    SQLiteHelper sqLiteHelper;
    FirebaseAuth mAuth;
    String lloji = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        listView = (ListView)findViewById(R.id.listViewUser);
        mAuth = FirebaseAuth.getInstance();




        sqLiteHelper = new SQLiteHelper(this);
        veturaArrayList = new ArrayList<>();
        adapteri = new CarListAdapter(this, veturaArrayList);
        listView.setAdapter(adapteri);

        try {

            Cursor cursor = sqLiteHelper.getData("Select * from VETURA where " +
                    "email like '" + mAuth.getCurrentUser().getEmail() +"'");

            if(cursor.getCount()== 0){
                Toast.makeText(this, "Nuk ka te dhena!", Toast.LENGTH_SHORT).show();
            }
            else {
                veturaArrayList.clear();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String titulli = cursor.getString(1);
                    String pershkrimi = cursor.getString(7);
                    byte[] foto = cursor.getBlob(9);

                    veturaArrayList.add(new Vetura(titulli, pershkrimi, foto, id));
                }

                adapteri.notifyDataSetChanged();
            }

        }
        catch (Exception ex){
            Toast.makeText(this,"Gabim", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                CharSequence[] items = {"Perditeso", "Fshije"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListUser.this);
                dialog.setTitle("Zgjedh nje veprim");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = sqLiteHelper.getData("SELECT id FROM VETURA WHERE email='" + mAuth.getCurrentUser().getEmail() + "'");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(ListUser.this, arrID.get(position));

                        } else {
                            // delete
                            Cursor c = sqLiteHelper.getData("SELECT id FROM VETURA Where email = '"+ mAuth.getCurrentUser().getEmail() +"'");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    ImageView ivUploadFoto;

    private void showDialogUpdate(final Activity activity, final int position){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.activity_krijo_shpallje_form);
        dialog.setTitle("Perditeso");

        final EditText etTitulli = (EditText)dialog.findViewById(R.id.etTitulliShpallje);
        final EditText etViti = (EditText)dialog.findViewById(R.id.etViti);
        final EditText etKilometrazha = (EditText)dialog.findViewById(R.id.etKilometrazha);
        final EditText etKomuna = (EditText)dialog.findViewById(R.id.etKomuna);
        final EditText etTel= (EditText)dialog.findViewById(R.id.etTel);
        final EditText etPershkrimi= (EditText)dialog.findViewById(R.id.etPershkrimi);
        final Spinner spinner = (Spinner)dialog.findViewById(R.id.spinner);
        final Button btnNdrysho = (Button)dialog.findViewById(R.id.btnKrijoShpallje);
        final Button btnUpload = (Button)dialog.findViewById(R.id.btnUpload);
        ivUploadFoto = (ImageView)dialog.findViewById(R.id.ivUploadFoto);



        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int)(activity.getResources().getDisplayMetrics().widthPixels * 0.8);

        dialog.getWindow().setLayout(width,height);
        dialog.show();





        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.lloji_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lloji = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ListUser.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });


        btnNdrysho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Vetura vetura = new Vetura(
                            etTitulli.getText().toString(),
                            etViti.getText().toString(),
                            etKilometrazha.getText().toString(),
                            etKomuna.getText().toString(),
                            etTel.getText().toString(),
                            etPershkrimi.getText().toString(),
                            lloji,
                            KrijoShpalljeForm.imageViewToByte(ivUploadFoto),
                            mAuth.getCurrentUser().getEmail()
                    );
                    sqLiteHelper.updateData(vetura,position);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "U perditesua me sukses!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                updateVeturaList();
            }
        });


    }

    private void showDialogDelete(final int idVetura){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ListUser.this);

        dialogDelete.setTitle("Kujdes!!");
        dialogDelete.setMessage("Jeni te sigurte qe deshironi ta fshihni?");
        dialogDelete.setPositiveButton("PO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    sqLiteHelper.deleteData(idVetura);
                    Toast.makeText(getApplicationContext(), "Fshirja u be ne rregull",Toast.LENGTH_SHORT).show();

                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateVeturaList();

            }
        });

        dialogDelete.setNegativeButton("JO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateVeturaList(){
        // get all data from sqlite
        Cursor cursor = sqLiteHelper.getData("Select * from VETURA where " +
                "email like '" + mAuth.getCurrentUser().getEmail() +"'");
        veturaArrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String titulli = cursor.getString(1);
            String pershkrimi = cursor.getString(5);
            byte[] foto = cursor.getBlob(7);

            veturaArrayList.add(new Vetura(titulli, pershkrimi, foto, id));
        }

        adapteri.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ivUploadFoto.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
