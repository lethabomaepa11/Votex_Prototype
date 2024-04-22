package com.example.votex_prototype;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
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


            if(!name.isEmpty() && !email.isEmpty() && !gender.isEmpty() && !studentNumber.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
                if (email.contains("@mynwu.ac.za"))
                {
                    if (studentNumber.length() < 8)
                    {
                        Snackbar.make(v, "Enter your student number: Has 8 digits", Snackbar.LENGTH_LONG).show();
                    }
                    else {

                        if(password.equals(confirmPassword))
                        {
                            //VotexDB.VotexDbHelper dbHelper = new VotexDB.VotexDbHelper(this);
                            User user = new User(studentNumber,name,email,password,gender);
                            VotexDB.Insert(this,user,null,null);
                            //go to another activity
                            this.finish();
                            Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, Login.class));
                        }
                        else
                        {
                            Snackbar.make(v, "Passwords do not match!!", Snackbar.LENGTH_LONG).show();
                        }

                    }

                }
                else {

                    Snackbar.make(v, "Enter your student email: includes \"@mynwu.ac.za\"", Snackbar.LENGTH_LONG).show();
                }
            }
            else
            {
                Snackbar.make(v, "Error!\nMake sure all textboxes are not empty!!!", Snackbar.LENGTH_LONG).show();
            }



        }catch (Exception e)
        {
            Toast.makeText(this, "Input every textbox "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}