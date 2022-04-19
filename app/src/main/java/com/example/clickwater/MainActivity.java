package com.example.clickwater;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button Send, logout;
    EditText text;
    Firebase firebase;

    public static final String Firebase_Server_URL = "your firebase url here";
    String issueHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(MainActivity.this);
        firebase = new Firebase(Firebase_Server_URL);

        Send = (Button) findViewById(R.id.tuma);
        logout = (Button) findViewById(R.id.toka);
        text = (EditText) findViewById(R.id.data);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data issue_raised = new Data();

                GetDataFromEditText();
                issue_raised.setinfo(issueHolder);

                firebase.child("User").setValue(issue_raised);
                Toast.makeText(MainActivity.this,"Issue sent Successfully", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();
    }

    public void GetDataFromEditText(){

        issueHolder = text.getText().toString().trim();
    }
}
