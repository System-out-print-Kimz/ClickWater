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

public class login extends AppCompatActivity {

    TextView signup;
    Button Login;
    EditText meil, passcode;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        Login = (Button) findViewById(R.id.login);
        signup = (TextView) findViewById(R.id.sign);
        meil = (EditText) findViewById(R.id.mail);
        passcode = (EditText) findViewById(R.id.code);

        signup.setOnClickListener(this::go_to_sign);

        Login.setOnClickListener(new View.OnClickListener() {
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

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(login.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(login.this, "Could not log in" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
    public void go_to_sign(View view) {
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }
}