package com.example.votex_prototype;


import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
//Session
abstract public class Main extends AppCompatActivity
{
    static ArrayList<User> users = new ArrayList<>();//holds the all users of the app
    static ArrayList<Vote> votes = new ArrayList<>();//holds all the votes in the app
    static ArrayList<Candidate> candidates = new ArrayList<>();//holds all the candidates in the app
    static ArrayList<Vote> sessionVotes = new ArrayList<>();//holds the current logged in user's votes until they confirm and submit their vote
    static  User sessionUser = null;//holds the current logged_in user data

}