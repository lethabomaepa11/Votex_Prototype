package com.example.votex_prototype;

import static com.example.votex_prototype.Main.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
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


        if(users.isEmpty())
        {
            Toast.makeText(this, "Cannot open...", Toast.LENGTH_SHORT).show();
        }
        else {
            lblTotalUsers = findViewById(R.id.lblTotalUsers);
            lblVoted = findViewById(R.id.lblVoted);
            lblDidNotVote = findViewById(R.id.lblDidNotVote);
            users.clear();
            votes.clear();
            candidates.clear();
            VotexDB.readFromDB(this, "users");
            VotexDB.readFromDB(this, "votes");
            VotexDB.readFromDB(this, "candidates");

            for(int k = 0; k < users.size(); k++)
            {

                for(int i = 0; i < votes.size(); i++)
                {
                    if(Objects.equals(users.get(k).id, votes.get(i).getVoterId()))
                    {
                        users.get(k).setHasVoted();
                    }
                    if(users.get(k).getHasVoted())
                    {
                        break;
                    }
                }
            }

            //load the stats
            callStats();
            results();


        }
    }
    public ArrayList<String> generateResults() {
        ArrayList<String> resultsList = new ArrayList<>();
        String[] portfolios = {"Chairperson", "Deputy Chairperson", "Secretary", "Deputy Secretary", "Academic Officer", "Legal Officer"};

        for (String portfolio : portfolios) {
            StringBuilder result = new StringBuilder();
            result.append(portfolio.toUpperCase()).append("...\n");
            for (int i = 0; i < candidates.size(); i++) {
                int voteCount = 0;
                if (candidates.get(i).getPortfolio().equalsIgnoreCase(portfolio)) {
                    for (Vote vote : votes) {
                        if (vote.getCandidateId().equals(candidates.get(i).getId())) {
                            voteCount++;
                        }
                    }
                    result.append((i + 1)).append("\t").append(candidates.get(i).getName()).append("\t\t").append(voteCount).append("\n");
                }
            }
            resultsList.add(result.toString());
        }
        return resultsList;
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

    public void results()
    {
        String[] portfolios = {"Chairperson","Deputy Chairperson","Secretary","Deputy Secretary","Academic Officer","Legal Officer"};
        //display all 6 portfolios, 18 candidates and their vote count, sort descending

        for(int p = 0; p < 6; p++)//portfolios
        {
            String portfolio = portfolios[p];
            TextView list = findViewById(R.id.txtList);
            /*ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
            ListView listView = (ListView) findViewById(R.id.listview);
            listView.setAdapter(itemsAdapter);*/
            list.setText(list.getText()+"\n\n"+portfolio.toUpperCase()+"...\n");
            for(int i = 0; i < candidates.size(); i++)
            {

                int voteCount = 0;
                if(candidates.get(i).getPortfolio().equalsIgnoreCase(portfolio))
                {

                    //iterate in votes
                    for(int v = 0; v < votes.size(); v++)
                    {
                        if(votes.get(v).getCandidateId().equals(candidates.get(i).getId()))
                        {
                            voteCount++;
                        }
                    }
                    //this will display the candidates grouped by portfolio
                    list.setText(list.getText()+""+(i+1)+"\t"+candidates.get(i).getName()+"\t\t"+voteCount+"\n");
                }

            }
        }
    }

    public void updateCandidates(View v)
    {
        startActivity(new Intent(AdminDashboard.this, UpdateCandidates.class));
        finish();
    }

}