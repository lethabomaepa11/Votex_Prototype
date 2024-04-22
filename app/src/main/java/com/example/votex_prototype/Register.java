package com.example.votex_prototype;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.sqlite.*;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();//hide the titlebar
    }
    public void loggedIn(View v)
    {
        //get the data
        try {

            EditText txtName = findViewById(R.id.txtName);
            EditText txtEmail = findViewById(R.id.txtEmail);
            EditText txtGender = findViewById(R.id.txtGender);
            EditText txtStudentNumber = findViewById(R.id.txtStudentNumber);
            EditText txtNPassword = findViewById(R.id.txtNPassword);//new password
            EditText txtCPassword = findViewById(R.id.txtCpassword);//confirm password

            String name = txtName.getText().toString();
            String email = txtEmail.getText().toString();
            String gender = txtGender.getText().toString();
            String studentNumber = txtStudentNumber.getText().toString();
            String password = txtNPassword.getText().toString();
            String confirmPassword = txtCPassword.getText().toString();
            boolean error = true;
            String title = null,message = null;


            if(!name.isEmpty() && !email.isEmpty() && !gender.isEmpty() && !studentNumber.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
                if (email.endsWith("@mynwu.ac.za"))
                {
                    if (studentNumber.length() != 8)
                    {
                        title = "Username!!";
                        message = "Enter your student number as your username:\nMust have 8 digits";
                    }
                    else {

                        if(password.equals(confirmPassword))
                        {
                           if(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("other"))
                           {
                               gender = gender.toLowerCase();
                               //VotexDB.VotexDbHelper dbHelper = new VotexDB.VotexDbHelper(this);
                               User user = new User(studentNumber,name,email,password,gender);
                               VotexDB.Insert(this,user,null,null);
                               //go to another activity
                               this.finish();
                               error = false;
                               Toast.makeText(this, "Successfully Created an Account \tUsername: "+studentNumber, Toast.LENGTH_LONG).show();
                               startActivity(new Intent(Register.this, Login.class));
                           }
                           else
                           {
                               title = "Gender!!!";
                               message = "Gender must be Male, Female or Other!!";
                           }
                        }
                        else
                        {
                            title = "Password do not match!!";
                            message = "Passwords entered do not match, try again!";
                        }

                    }

                }
                else {
                    title = "Email";
                    message = "Enter your student email: must end with \"@mynwu.ac.za\"";
                }
            }
            else
            {
                message = "Make sure all textboxes are not empty";
                Snackbar.make(v, "Error!\nMake sure all textboxes are not empty!!!", Snackbar.LENGTH_LONG).show();
            }
            if(error)
            {
                new AlertDialog.Builder(this)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }


        }catch (Exception e)
        {
            Toast.makeText(this, "Input every textbox "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}