package com.example.clickwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    TextView login_page;
    Button sign_in;
    EditText user, call, meil, passcode;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        login_page = (TextView) findViewById(R.id.log);
        sign_in = (Button) findViewById(R.id.ingia);
        user = (EditText) findViewById(R.id.name);
        call = (EditText) findViewById(R.id.phone);
        meil = (EditText) findViewById(R.id.mail);
        passcode = (EditText) findViewById(R.id.code);

        mAuth = FirebaseAuth.getInstance();

        login_page.setOnClickListener(this::go_to_login);

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = meil.getText().toString().trim();
                String password = passcode.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    meil.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passcode.setError("Password is required");
                    return;
                }

                if (password.length() < 6) {
                    passcode.setError("Password must have 6 or more characters");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(signup.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(signup.this, "Could not create user" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    public void go_to_login(View view) {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }
}