package com.androidks.vetura.activities;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidks.vetura.R;
import com.androidks.vetura.database.SQLiteHelper;

public class DetajeVetura extends AppCompatActivity {


    TextView tvTitulli, tvViti, tvKilometrazha, tvTel;
    ImageView ivFoto;
    SQLiteHelper sqLiteHelper;
    String tipi="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaje_vetura);

        Bundle bundle = getIntent().getExtras();
        int id = Integer.parseInt(bundle.getString("id"));
        tipi = getIntent().getExtras().getString("tipi");
        init();

        sqLiteHelper = new SQLiteHelper(this);
        Cursor cursor = sqLiteHelper.getData("Select * from VETURA where id="+id);

        while(cursor.moveToNext()) {
            tvTitulli.setText(cursor.getString(1));
            tvViti.setText(cursor.getString(2));
            tvKilometrazha.setText(cursor.getString(3));
            tvTel.setText(cursor.getString(5));
            try {
                ivFoto.setImageBitmap(BitmapFactory.decodeByteArray
                        (cursor.getBlob(8), 0, cursor.getBlob(8).length));
            }
            catch(Exception ex){
                Toast.makeText(this, "Gabim", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void init(){
        tvTitulli = (TextView)findViewById(R.id.tvDetajeTitulli);
        tvViti = (TextView)findViewById(R.id.tvDetajeViti);
        tvKilometrazha = (TextView)findViewById(R.id.tvDetajeKilometrazha);
        tvTel = (TextView)findViewById(R.id.tvDetajeTel);
        ivFoto = (ImageView) findViewById(R.id.ivDetajeFoto);
    }
}
