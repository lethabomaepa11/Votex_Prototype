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
import java.util.ArrayList;
import java.util.Scanner;
import android.database.sqlite.*;


//we use this class as the main, it contains all the methods needed
public class Main extends AppCompatActivity
{
    static ArrayList<User> users = new ArrayList<>();
    static  int userCount;
    static ArrayList<Vote> votes = new ArrayList<>();
    static  int voteCount;
    static ArrayList<Candidate> candidates = new ArrayList<>();
    static  int candidateCount;
    static  User sessionUser;

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