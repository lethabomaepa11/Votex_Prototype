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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

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
        VotexDB.readFromDB(this,"users");

    }
    int count = 0;
   /* public void loadUsers() {
        try {
            InputStream inputStream = getAssets().open("users.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

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
    }*/
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
        boolean idExists = false;
        boolean wrongPassword = true;
        EditText txtUsername = findViewById(R.id.txtloginUsername);
        EditText txtPassword = findViewById(R.id.txtloginPassword);

        String id = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        //Toast.makeText(this, (id+" p "+password), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < users.size(); i++) {
            if ((users.get(i).id.equals(id) && !(users.get(i).id.equals("ad1010"))) || users.get(i).email.equals(id)) {
                idExists = true;
                if (users.get(i).password.equals(password)) { // Use equals() for string comparison
                    sessionUser = users.get(i);
                    Toast.makeText(this, "Logged In Successfully as "+users.get(i).name, Toast.LENGTH_SHORT).show();
                    finishActivity(0);
                    this.finish();
                    startActivity(new Intent(Login.this, Home.class));
                    wrongPassword = false;
                    break;
                }
                break;
            } else if (users.get(i).id.equals("ad1010") && users.get(i).password.equals(password)) {
                //startActivity(new Intent(Login.this, AdminDashboard.class));
                Toast.makeText(this, "Admin login Successful as "+users.get(i).name, Toast.LENGTH_SHORT).show();
                finishActivity(0);
                this.finish();
                startActivity(new Intent(Login.this, AdminDashboard.class));
                idExists = true;
                break; // Exit loop once admin is identified
            }

        }
        if(!idExists)
        {
            wrongPassword = false;//account does not exist
        }

        if(wrongPassword)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Wrong Password")
                    .setMessage("Wrong Password for \""+id+"\"")
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else if (!idExists) {
            new AlertDialog.Builder(this)
                    .setTitle("Account does not exist for \""+id+"\"")
                    .setMessage("Create a new account?")
                    .setNegativeButton(android.R.string.no, (dialog, which) -> dialog.dismiss())
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        dialog.dismiss();
                        startActivity(new Intent(Login.this, Register.class));

                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


    }

}