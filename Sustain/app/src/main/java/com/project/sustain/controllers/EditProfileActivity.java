package com.project.sustain.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.project.sustain.R;
import com.project.sustain.model.UserType;

public class EditProfileActivity extends AppCompatActivity {
    private EditText mDisplayName;
    private Spinner mSpinnerUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //get refs to widgets on screen
        mDisplayName = (EditText) findViewById(R.id.editText_display_name);
        mSpinnerUserType = (Spinner) findViewById(R.id.spinner_usertype);

        //fill drop-down boxes
        mSpinnerUserType.setAdapter(new ArrayAdapter<UserType>(this, R.layout.support_simple_spinner_dropdown_item,
                UserType.values()));

    }
}
