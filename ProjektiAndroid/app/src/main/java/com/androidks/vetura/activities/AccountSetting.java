package com.androidks.vetura.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidks.vetura.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountSetting extends AppCompatActivity {

    TextView tvEmail;
    ImageView btnMyListing, btnChangePassword, btnDeleteAccount;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        tvEmail = (TextView)findViewById(R.id.tvCurrentUserEmail);
        btnMyListing = (ImageView)findViewById(R.id.btnMyListing);
        btnChangePassword = (ImageView)findViewById(R.id.btnChangePassword);
        btnDeleteAccount = (ImageView)findViewById(R.id.btnDeleteAccount);

        mAuth = FirebaseAuth.getInstance();

        tvEmail.setText(mAuth.getCurrentUser().getEmail());


        btnMyListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSetting.this, ListUser.class));
            }
        });


        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    final FirebaseUser user = mAuth.getCurrentUser();
                    final Dialog dialog = new Dialog(AccountSetting.this);

                    dialog.setContentView(R.layout.activity_change_password);
                    dialog.setTitle("Ndrysho fjalekalimin");
                    final EditText edtPassword = (EditText) dialog.findViewById(R.id.edtPassword);
                    final EditText edtConfirmPassword = (EditText) dialog.findViewById(R.id.edtConfirm);


                    Button btnReset = (Button) dialog.findViewById(R.id.btnResetPassword);
                    btnReset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                             String password = edtPassword.getText().toString().trim();
                             String confirmPassword = edtConfirmPassword.getText().toString().trim();

                            if (password.equals("") || confirmPassword.equals("")) {
                                Toast.makeText(AccountSetting.this, "Duhet te plotesoni te gjitha fushat", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (!password.equals(confirmPassword)) {
                                Toast.makeText(AccountSetting.this, "Fjalekalimet duhet te perputhen", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                user.updatePassword(password)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(AccountSetting.this,
                                                            "Sukses",
                                                            Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                } else {
                                                    Toast.makeText(AccountSetting.this,
                                                            "Gabim", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }

                        }
                    });
                int width = (int)(AccountSetting.this.getResources().getDisplayMetrics().widthPixels * 0.95);
                int height = (int)(AccountSetting.this.getResources().getDisplayMetrics().widthPixels * 0.8);

                dialog.getWindow().setLayout(width,height);
                dialog.show();




            }
            });
        
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(AccountSetting.this);
                dialog.setTitle("Fshije llogarine!");
                dialog.setMessage("A jeni te sigurte qe deshironi ta fshini llogarine?");
                dialog.setPositiveButton("PO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseUser user = mAuth.getCurrentUser();
                        if(user != null){
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AccountSetting.this, "LLogaria juaj eshte fshire me sukses", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(AccountSetting.this,MainActivity.class));
                                    }else{
                                        Log.e(  "DESHTIM FSHIRJA",task.getException().toString());
                                        Toast.makeText(AccountSetting.this, "Ka ndodhur nje gabim!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }
                });
                dialog.setNegativeButton("JO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
    }
}
