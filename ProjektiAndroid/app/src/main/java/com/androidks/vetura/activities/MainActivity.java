package com.androidks.vetura.activities;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidks.vetura.R;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    EditText etSearch;
    ImageView ivSearch;
    TextView tvCurrentUser;
    FirebaseAuth mAuth;
    Button btnRegjistrohu;
    Button btnLogout;
    Button btnKycu,buttoni;
    //na shfaq nje image ne momentin qe jena te kyqun se ne fillim eshte invisibil
    ImageView setting;
    ImageView id1,id2,id3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = (EditText) findViewById(R.id.etSearch);
        ivSearch = (ImageView) findViewById(R.id.ivClickSearch);
        tvCurrentUser = (TextView) findViewById(R.id.tvCurrentUser);
        btnRegjistrohu = (Button) findViewById(R.id.btnRegjistrohu);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnKycu = (Button) findViewById(R.id.btnKycu);
        setting = (ImageView) findViewById(R.id.btnSetting);
        buttoni=findViewById(R.id.buttonii);

        id1=findViewById(R.id.id1);
        id2=findViewById(R.id.id2);
        id3=findViewById(R.id.id3);

        buttoni.animate().translationYBy(250f).alpha(1).rotationBy(360).setDuration(2000);
        id1.animate().alpha(1).translationXBy(0f).translationYBy(120f).setDuration(4000);
        id1.animate().rotationBy(3600).setDuration(4000);
        id2.animate().alpha(1).translationXBy(125f).translationX(-225f).translationYBy(250f).setDuration(4000);
        id2.animate().rotationBy(3600).setDuration(4000);
        id3.animate().alpha(1).translationXBy(25f).translationX(225f).translationYBy(75f).setDuration(4000);
        id3.animate().rotationBy(3600).setDuration(4000);

        mAuth = FirebaseAuth.getInstance( );


        //nese  perputhet email dhe password ne kyqy ateher

        if(mAuth.getCurrentUser() != null){
            tvCurrentUser.setText(mAuth.getCurrentUser().getEmail());
            btnLogout.setVisibility(View.VISIBLE);
            btnKycu.setVisibility(View.INVISIBLE);
            setting.setVisibility(View.VISIBLE);
        }



        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, ListViewCars.class);
                i.putExtra("searchWord",etSearch.getText().toString());
                i.putExtra("tipi","search");
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                tvCurrentUser.setText("No User");
                finish();

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountSetting.class);
                startActivity(intent);
            }
        });
    }

    public void Kycu(View v){

        startActivity(new Intent(MainActivity.this, LoginForm.class));

    }

    public void Regjistrohu(View v){ startActivity(new Intent(MainActivity.this,RegisterForm.class)); }

    public void KrijoShpallje(View v){
        if(mAuth.getCurrentUser() == null){
            Toast.makeText(this, "Per te krijuar shpallje duhet te kyceni!", Toast.LENGTH_SHORT).show();
        }else {
            Intent i = new Intent(MainActivity.this, KrijoShpalljeForm.class);
            startActivity(i);

        }

    }

    public void KerkoVetura(View v){
        Intent i = new Intent(MainActivity.this, ListViewCars.class);
        i.putExtra("tipi","vetura");
        startActivity(i);
    }
    public void KerkoMotor(View v){
        Intent i = new Intent(MainActivity.this, ListViewCars.class);
        i.putExtra("tipi","motor");
        startActivity(i);
    }
    public void KerkoPjese(View v){
        Intent i = new Intent(MainActivity.this, ListViewCars.class);
        i.putExtra("tipi","pjese");
        startActivity(i);
    }


}
