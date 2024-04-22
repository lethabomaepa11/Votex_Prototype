package com.example.votex_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import static com.example.votex_prototype.Main.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.votex_prototype.VotexDB;

public class Index extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_index);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        getSupportActionBar().hide();//hide the titlebar
        VotexDB.VotexDbHelper dbHelper = new VotexDB.VotexDbHelper(this);
        VotexDB.ReadToDB(this,"users.txt");
        VotexDB.ReadToDB(this,"votes.txt");
        VotexDB.ReadToDB(this,"candidates.txt");
        VotexDB.readFromDB(this,"users");
        VotexDB.readFromDB(this,"votes");
        VotexDB.readFromDB(this,"candidates");
        
        while(users.size() == 0)
        {
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        }

    }
    public void btnClick(View v)
    {
        //go to another activity
        //Toast.makeText(this, users.size(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Index.this, Login.class));


    }
    public void register(View v)
    {
        //go to another activity
        startActivity(new Intent(Index.this, Register.class));

    }
}