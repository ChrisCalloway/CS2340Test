package com.project.sustain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {
    private Button btnAddUser, btnCancelUser;
    private EditText enteredUsername, enteredEmail;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Get reference to database instance
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // Get reference to "users" node of JSON database
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        enteredUsername = (EditText) findViewById(R.id.editUsername);
        enteredEmail = (EditText) findViewById(R.id.editUserEmail);
        btnAddUser = (Button) findViewById(R.id.buttonAddUser);
        btnCancelUser = (Button) findViewById(R.id.buttonCancelUser);


    }

    // Create a new user node under 'users'
    private void createUser (String name, String email) {
        // In real apps, this userID should be fetched
        // by implementing firebase auth
    }
}
