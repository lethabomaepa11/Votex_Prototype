package com.example.votex_prototype;

import static com.example.votex_prototype.Main.candidates;
import static com.example.votex_prototype.Main.votes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();//hide the titlebar
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        results();

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
            list.setText(list.getText()+"\n\n*****"+portfolio.toUpperCase()+"*****\n");
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

    public void backToHome(View v)
    {
        startActivity(new Intent(Results.this, Home.class));
        finish();
    }
}