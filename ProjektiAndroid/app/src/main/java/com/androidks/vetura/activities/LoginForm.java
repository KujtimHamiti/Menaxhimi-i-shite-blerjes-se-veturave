package com.androidks.vetura.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidks.vetura.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginForm extends AppCompatActivity {



    EditText email,password;
    Button btnLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        email =(EditText)findViewById(R.id.etLoginEmail);
        password =(EditText)findViewById(R.id.etLoginPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);


        mAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(LoginForm.this,"Jeni duke u kycur",
                        "Ju lutem prisni ...",true);


                mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful()){
                                    finish();
                                    Intent intent = new Intent(LoginForm.this, MainActivity.class);
                                    intent.putExtra("Email",mAuth.getCurrentUser().getEmail());
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Log.e("ERROR",task.getException().toString());
                                    Toast.makeText(LoginForm.this, "Me vjen keq por ju keni shtypur te dhenat e gabuara", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });
    }


}
