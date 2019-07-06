package com.androidks.vetura.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidks.vetura.R;
import com.androidks.vetura.database.SQLiteHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegisterForm extends AppCompatActivity {

    EditText etEmail, etPassword, etConfirmPassword;
    Button btnRegjistrohu;
    FirebaseAuth mAuth;

    SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etConfirmPassword = (EditText)findViewById(R.id.etConfirmPassword);
        btnRegjistrohu = (Button)findViewById(R.id.btnRegjistrohu);

        mAuth = FirebaseAuth.getInstance();

        btnRegjistrohu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                final String confirmPass = etConfirmPassword.getText().toString().trim();
                if(email.equals("") || password.equals("")){
                    Toast.makeText(RegisterForm.this, "Duhet ti plotesoni te gjitha fushat", Toast.LENGTH_SHORT).show();
                    if (email.equals(""))
                        etEmail.requestFocus();
                    else
                        etPassword.requestFocus();
                    return;
                }
                if(!password.equals(confirmPass)){
                    Toast.makeText(RegisterForm.this, "Fjalekalimi konfirmues duhet te perputhet!", Toast.LENGTH_SHORT).show();
                    etConfirmPassword.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterForm.this, "Regjistrimi u realizue me sukses!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterForm.this,MainActivity.class));
                                    finish();
                                }
                                else {
                                    try {
                                        throw task.getException( );
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        Toast.makeText(getApplicationContext(), "Password-i juaj eshte i dobet, ju lutem jepni nje password tjeter!",
                                                Toast.LENGTH_LONG).show();
                                        etPassword.requestFocus();
                                        Log.d("Kujtim" , "Weak Password");
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        Toast.makeText(getApplicationContext(), "Ekziston perdorues me keto te dhena!",
                                                Toast.LENGTH_LONG).show();
                                        etPassword.requestFocus();
                                        Log.d("Kujtim" , "User exists");
                                    } catch (Exception e) {
                                        Log.e("Kujtim" , e.getMessage( ));
                                    }
                                   // Log.d("ErrotFireBase" , task.getException( ).toString( ));
                                 //   Toast.makeText(RegisterForm.this , "gabim na falni" , Toast.LENGTH_SHORT).show( );
                                }
                            }
                        });
            }
        });

    }
}
