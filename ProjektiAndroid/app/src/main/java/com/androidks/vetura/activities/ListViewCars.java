package com.androidks.vetura.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidks.vetura.adapter.CarListAdapter;
import com.androidks.vetura.R;
import com.androidks.vetura.beans.Vetura;
import com.androidks.vetura.database.SQLiteHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ListViewCars extends AppCompatActivity {

    ListView listView;

    //nje arrayList te tipit Vetura ku i mer te dhenat
    ArrayList<Vetura> veturaArrayList;

    //shkon e  krijon nje liste te car me adapteer  si fillim e  kena lan zero
    CarListAdapter carListAdapter = null;

    SQLiteHelper sqLiteHelper;
    String search="";
    String tipi = "";
    Cursor cursor;


    FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_cars);

        listView = (ListView)findViewById(R.id.listViewCar);
        mAuth = FirebaseAuth.getInstance();


        search = getIntent().getExtras().getString("searchWord");
        tipi = getIntent().getExtras().getString("tipi");


        sqLiteHelper = new SQLiteHelper(this);
        veturaArrayList = new ArrayList<>();
        carListAdapter = new CarListAdapter(this, veturaArrayList);
        listView.setAdapter(carListAdapter);

        try {

            if(tipi.equals("search"))
                cursor = sqLiteHelper.getData("Select * from VETURA where titulli like '" + search +"%'");
            else
                cursor = sqLiteHelper.getData("Select * from VETURA where lloji like '" + tipi + "'");


            if(cursor.getCount()== 0){
                Toast.makeText(this, "Nuk ka te dhena!", Toast.LENGTH_SHORT).show();
            }
            else {
                veturaArrayList.clear();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String titulli = cursor.getString(1);
                    String pershkrimi = cursor.getString(6);
                    byte[] foto = cursor.getBlob(8);

                    veturaArrayList.add(new Vetura(titulli, pershkrimi, foto, id));

                    Log.d("Kujtim3", "Size: " + veturaArrayList.size()+ " ");
                }

                carListAdapter.notifyDataSetChanged();
            }

        }
        catch (Exception ex){
            Toast.makeText(this, "Gabim ju lutem kerkoni diqka per te gjetur", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor c;
                if(tipi.equals("search"))
                    c = sqLiteHelper.getData("Select * from VETURA where titulli like '" + search +"%'");
                else
                    c = sqLiteHelper.getData("Select * from VETURA where lloji like '" + tipi + "'");


                ArrayList<Integer> arrID = new ArrayList<Integer>();
                while(c.moveToNext()){
                    arrID.add(c.getInt(0));
                }

                Log.d("Kujtim2", "Palidhje: " + String.valueOf(arrID.get(position)));
                Intent i = new Intent(ListViewCars.this, DetajeVetura.class);
                i.putExtra("id",String.valueOf(arrID.get(position)));
                i.putExtra("tipi",tipi);
                startActivity(i);
            }
        });
    }

}
