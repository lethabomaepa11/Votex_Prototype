package com.example.votex_prototype;

import static com.example.votex_prototype.Main.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().hide();//hide the titlebar

    }
    public void loadUsers() {
        try {
            InputStream inputStream = getAssets().open("users.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            count = 0;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split("#");
                if (details.length == 5) {
                   users[count] = new User(details[0],details[1],details[2],details[3],details[4]);
                   String data = details[0]+"#"+details[1]+"#"+details[2]+"#"+details[3]+"#"+details[4]+"\n";
                   writeToFileInInternalStorage(data,this);
                   count++;
                }
            }
            reader.close();
        } catch (IOException e) {
            //Toast.makeText(this, "Failed to load user data\n"+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void writeToFileInInternalStorage(String data, Context context) {
        try {
            // Open a private file associated with this Context's application package for writing.
            FileWriter writer = new FileWriter(new File(context.getFilesDir(), "users.txt"),true);
            writer.write(data);
            writer.close(); // Always close the writer to avoid memory leaks.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loggedIn(View v)
    {

        loadUsers();
        boolean logged_in = false;
        EditText txtUsername = findViewById(R.id.txtloginUsername);
        EditText txtPassword = findViewById(R.id.txtloginPassword);

        String id = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        //Toast.makeText(this, (id+" p "+password), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < count; i++) {
            if (users[i].id.equals(id) && !(users[i].id.equals("ad1010"))) {
                if (users[i].password.equals(password)) { // Use equals() for string comparison
                    sessionUser = users[i];
                    Toast.makeText(this, "Logged In Successfully as "+users[i].name, Toast.LENGTH_SHORT).show();
                    finishActivity(0);
                    this.finish();
                    startActivity(new Intent(Login.this, Home.class));
                    logged_in = true;
                    break;
                }
            } else if (users[i].id.equals("ad1010") && users[i].password.equals(password)) {
                //startActivity(new Intent(Login.this, AdminDashboard.class));
                logged_in = true; // Set logged_in to true for admin login
                break; // Exit loop once admin is identified
            }
        }

        if (!logged_in) {



            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }

    }

}