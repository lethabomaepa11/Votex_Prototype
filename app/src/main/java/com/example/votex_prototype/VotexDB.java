package com.example.votex_prototype;

import static com.example.votex_prototype.Main.candidateCount;
import static com.example.votex_prototype.Main.candidates;
import static com.example.votex_prototype.Main.users;
import static com.example.votex_prototype.Main.voteCount;
import static com.example.votex_prototype.Main.votes;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.database.sqlite.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.widget.Toast;

public final class VotexDB {
    private VotexDB() {}

    /* Inner class that defines the table contents */
    //Users table
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_EMAIL = "Email";
        public static final String COLUMN_NAME_PASSWORD = "Password";
        public static final String COLUMN_NAME_GENDER = "Gender";
    }
    //Candidates table
    public static class Candidates implements BaseColumns {
        public static final String TABLE_NAME = "Candidates";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_PORTFOLIO = "Portfolio";
        public static final String COLUMN_NAME_ID = "ID";
    }
    //Votes table
    public static class Votes implements BaseColumns {
        public static final String TABLE_NAME = "Votes";
        public static final String COLUMN_NAME_VOTER_ID = "VoterID";
        public static final String COLUMN_NAME_CANDIDATE_ID = "CandidateID";
        public static final String COLUMN_NAME_ID = "ID";
    }
//create tables
    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + Users.TABLE_NAME + " (" +
                    Users.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    Users.COLUMN_NAME_NAME + " TEXT," +
                    Users.COLUMN_NAME_EMAIL + " TEXT,"+
                    Users.COLUMN_NAME_PASSWORD + " TEXT,"+
                    Users.COLUMN_NAME_GENDER + " TEXT)";
    private static final String SQL_CREATE_CANDIDATES =
            "CREATE TABLE " + Candidates.TABLE_NAME + " (" +
                    Candidates.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    Candidates.COLUMN_NAME_NAME + " TEXT," +
                    Candidates.COLUMN_NAME_PORTFOLIO + " TEXT)";
    private static final String SQL_CREATE_VOTES =
            "CREATE TABLE " + Votes.TABLE_NAME + " (" +
                    Votes.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                    Votes.COLUMN_NAME_CANDIDATE_ID+ " TEXT," +
                    Votes.COLUMN_NAME_VOTER_ID + " TEXT)";
    //delete if tables
    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + Users.TABLE_NAME;
    private static final String SQL_DELETE_CANDIDATES =
            "DROP TABLE IF EXISTS " + Candidates.TABLE_NAME;
    private static final String SQL_DELETE_VOTES =
            "DROP TABLE IF EXISTS " + Votes.TABLE_NAME;

    static public class VotexDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "VotexDatabase.db";

        public VotexDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_USERS);
            db.execSQL(SQL_CREATE_CANDIDATES);
            db.execSQL(SQL_CREATE_VOTES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_USERS);
            db.execSQL(SQL_DELETE_CANDIDATES);
            db.execSQL(SQL_DELETE_VOTES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }


    }

    //insert
    static void Insert(Context context, User user,Candidate candidate,Vote vote)
    {
        //Pass an object on one of the 3 and pass null on the remaining 2
        VotexDbHelper dbHelper = new VotexDbHelper(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(user != null)
        {
            values.put(Users.COLUMN_NAME_ID, user.id);
            values.put(Users.COLUMN_NAME_NAME, user.name);
            values.put(Users.COLUMN_NAME_EMAIL, user.email);
            values.put(Users.COLUMN_NAME_PASSWORD, user.password);
            values.put(Users.COLUMN_NAME_GENDER, user.gender);
            db.insert(Users.TABLE_NAME, null, values);

        }
        else if(candidate != null)
        {
            values.put(Candidates.COLUMN_NAME_ID, candidate.getId());
            values.put(Candidates.COLUMN_NAME_NAME, candidate.getName());
            values.put(Candidates.COLUMN_NAME_PORTFOLIO, candidate.getPortfolio());
            db.insert(Candidates.TABLE_NAME, null, values);
        }
        else if(vote != null)
        {
            values.put(Votes.COLUMN_NAME_ID, vote.getVoteId());
            values.put(Votes.COLUMN_NAME_VOTER_ID, vote.getVoterId());
            values.put(Votes.COLUMN_NAME_CANDIDATE_ID, vote.getCandidateId());
            db.insert(Votes.TABLE_NAME, null, values);
        }
        //column names are the keys



        //Insert the new row, returning the primary key value of the new row

    }
    static void ReadToDB(Context context,String filename)
    {


        try {
            InputStream inputStream = context.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            //add each line into its corresponding table
            while ((line = reader.readLine()) != null) {
                String[] details = line.split("#");
                if (details.length > 0) {
                    switch (filename)
                    {
                        case "users.txt":
                            User user = new User(details[0],details[1],details[2],details[3],details[4]);
                            Insert(context,user,null,null);
                            break;
                        case "candidates.txt":
                            Candidate candidate = new Candidate(details[0],details[1],details[2]);
                            Insert(context,null,candidate,null);
                            break;
                        case "votes.txt":
                            Vote vote = new Vote(details[0],details[1]);
                            Insert(context,null,null,vote);
                            break;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            //Toast.makeText(this, "Failed to load user data\n"+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    static void readFromDB(Context context,String type)
    {
        VotexDbHelper dbHelper = new VotexDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        //read from DB to an array of the object
        switch (type)
        {

            case "users":
                cursor = db.query(
                        Users.TABLE_NAME,   // The table to query
                        null,             // The array of columns to return (pass null to get all)
                        null,              // The columns for the WHERE clause(null to get all)
                        null,          // The values for the WHERE clause(null to get all)
                        null,              // don't group the rows
                        null,                   // don't filter by row groups
                        null              // The sort order
                );

                //insert the results into the array of the user object
                while(cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_NAME));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_EMAIL));
                    String password = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_PASSWORD));
                    String gender = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_GENDER));
                    users.add(new User(id,name,email,password,gender));

                }
                cursor.close();
                break;
            case "candidates":
                cursor = db.query(
                        Candidates.TABLE_NAME,   // The table to query
                        null,             // The array of columns to return (pass null to get all)
                        null,              // The columns for the WHERE clause(null to get all)
                        null,          // The values for the WHERE clause(null to get all)
                        null,              // don't group the rows
                        null,                   // don't filter by row groups
                        null              // The sort order
                );
                while(cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(Candidates.COLUMN_NAME_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(Candidates.COLUMN_NAME_NAME));
                    String portfolio = cursor.getString(cursor.getColumnIndexOrThrow(Candidates.COLUMN_NAME_PORTFOLIO));
                    candidates.add(new Candidate(name,portfolio,id));
                    candidateCount++;
                }
                cursor.close();
                break;
            case "votes":
                cursor = db.query(
                        Votes.TABLE_NAME,   // The table to query
                        null,             // The array of columns to return (pass null to get all)
                        null,              // The columns for the WHERE clause(null to get all)
                        null,          // The values for the WHERE clause(null to get all)
                        null,              // don't group the rows
                        null,                   // don't filter by row groups
                        null              // The sort order
                );
                while(cursor.moveToNext()) {
                    String voterID = cursor.getString(cursor.getColumnIndexOrThrow(Votes.COLUMN_NAME_VOTER_ID));
                    String  candidateID= cursor.getString(cursor.getColumnIndexOrThrow(Votes.COLUMN_NAME_CANDIDATE_ID));
                    votes.add(new Vote(voterID,candidateID));
                    voteCount++;
                }
                cursor.close();
                break;
        }



        // Define a projection that specifies which columns from the database
        // you will actually use after this query.


        // Filter results WHERE "title" = 'My Title'


        // How you want the results sorted in the resulting Cursor


    }

}

