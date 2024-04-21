package com.example.votex_prototype;
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

public class Main extends AppCompatActivity
{
    static User users[] = new User[100];
    static  User sessionUser;
    static int count = 0;
    char delimiter = '#';






    public void writeToFileInInternalStorage(String filename, String data, Context context) {
        try {
            // Open a private file associated with this Context's application package for writing.
            FileWriter writer = new FileWriter(new File(context.getFilesDir(), filename));
            writer.write(data);
            writer.close(); // Always close the writer to avoid memory leaks.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInitialUsers() {
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
                    writeToFileInInternalStorage("users.txt",data,this);
                    count++;
                }
            }
            reader.close();
        } catch (IOException e) {
            //Toast.makeText(this, "Failed to load user data\n"+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    static public void main(String[] args)
    {
        /*Login Method
         * 
         *
        */
       /*Registration
        * 
       Scanner input = new Scanner(System.in);
       System.out.print("Name: ");
       String name = input.nextLine();
       System.out.print("Email: ");
       String email = input.nextLine();
       if(email.contains("@mynwu.ac.za"))
        {
           System.out.print("Student ID: ");
           String stdId = input.nextLine();
           if(stdId.length() == 8)
           {
                   System.out.print("Gender: ");
                   String gender = input.nextLine();
                   System.out.print("Password: ");
                   String password = input.nextLine();
                   
                   if(stdId.contains("ad"))
                   {
                       System.out.println("Use your student number");
                   }
                   else
                   {
                       
                           //append to file
                           String line = "\n"+stdId+"#"+name+"#"+email+"#"+password+"#"+gender;
                           appendToFile(line);
                    }
           }
           else
           {
               System.out.println("Enter your student id, (8 characters)");
           }
          
        }
       else
       {
           System.out.println("Enter your student mail ending with \"@mynwu.ac.za\"");
       }
       */
    }
}