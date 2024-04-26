package com.example.votex_prototype;

import static com.example.votex_prototype.Main.*;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class AdminDashboard extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Objects.requireNonNull(getSupportActionBar()).hide();//hide the titlebar
        VotexDB.VotexDbHelper dbHelper = new VotexDB.VotexDbHelper(this);
        if(users.isEmpty())
        {
            Toast.makeText(this, "Cannot open", Toast.LENGTH_SHORT).show();
        }
        else {
            users.clear();
            votes.clear();
            candidates.clear();
            lblTotalUsers = findViewById(R.id.lblTotalUsers);
            lblVoted = findViewById(R.id.lblVoted);
            lblDidNotVote = findViewById(R.id.lblDidNotVote);
            VotexDB.readFromDB(this, "users");
            VotexDB.readFromDB(this, "votes");
            VotexDB.readFromDB(this, "candidates");
            for(int i = 0; i < votes.size(); i++)
            {
                if(Objects.equals(users.get(i).id, votes.get(i).getVoterId()))
                {
                    users.get(i).setHasVoted();
                }
            }
            //load the stats
            callStats();
        }
    }
    int hasVoted = 0;//admin not included
    int didNotVote = -1;
    TextView lblTotalUsers,lblVoted,lblDidNotVote;
    public void callStats()
    {

        for(int i = 0; i < users.size();i++)
        {
            if(users.get(i).getHasVoted())
            {
                hasVoted++;
            }
            else
            {
                didNotVote++;
            }
            lblTotalUsers.setText(String.valueOf(users.size()-1));
        }
        lblVoted.setText(String.valueOf(hasVoted));
        lblDidNotVote.setText(String.valueOf(didNotVote));
    }

}