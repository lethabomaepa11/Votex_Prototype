package com.example.votex_prototype;

import static com.example.votex_prototype.Main.*;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VotingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Objects.requireNonNull(getSupportActionBar()).hide();//hide the titlebar
        setContentView(R.layout.activity_voting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSkipNext = findViewById(R.id.btnSkipNext);
        btnSubmit = findViewById(R.id.btnSubmitVote);
        layer1 = findViewById(R.id.candidateOne);
        layer2 = findViewById(R.id.candidateTwo);
        layer3 = findViewById(R.id.candidateThree);
        Candidate1 = findViewById(R.id.Candidate1);
        Candidate2 = findViewById(R.id.Candidate2);
        Candidate3 = findViewById(R.id.Candidate3);
        screen = 0; //first screen, first portfolio
        txtName1 = findViewById(R.id.txtCandidateName1);
        txtName2 = findViewById(R.id.txtCandidateName2);
        txtName3 = findViewById(R.id.txtCandidateName3);
        txtParty1 = findViewById(R.id.txtParty1);
        txtParty2 = findViewById(R.id.txtParty2);
        txtParty3 = findViewById(R.id.txtParty3);
        txtPortfolio = findViewById(R.id.txtPortfolio);
        portfolios = new ArrayList<>();
        portfolioCandidates = new ArrayList<>();

        if(!candidates.isEmpty())
        {
            populatePortfolios();
            showPortfolio();
        }
        else
            Toast.makeText(this, "There are no candidates, Exiting...", Toast.LENGTH_LONG).show();
    }



    Button btnSkipNext, btnSubmit;
    LinearLayout layer1,layer2,layer3;
    ImageButton Candidate1,Candidate2,Candidate3;
    ArrayList<String> portfolios;
    Vote selectedNow;
    int screen;
    TextView txtPortfolio,txtName1,txtName2,txtName3,txtParty1,txtParty2,txtParty3;
    ArrayList <Candidate> portfolioCandidates;
    public void populatePortfolios()
    {
        //take the array of candidates, then extract all the different portfolios available
        String portfolio = candidates.get(screen).getPortfolio();
        portfolios.add(portfolio);//add the first available portfolio

        for(int i = 0; i < candidates.size(); i++)
        {
            if(!Objects.equals(portfolio, candidates.get(i).getPortfolio()))
            {
                portfolio = candidates.get(i).getPortfolio();
                portfolios.add(portfolio);
            }
        }

    }

    List<Candidate> matchedCandidates = new ArrayList<>();
    public void showPortfolio() {
        if (portfolios.isEmpty() || candidates.isEmpty()) {
            Toast.makeText(this, "No portfolios or candidates available.", Toast.LENGTH_SHORT).show();
            return;
        }

            matchedCandidates.clear();
        for (Candidate candidate : candidates) {
            if (candidate.getPortfolio().equals(portfolios.get(screen))) {
                matchedCandidates.add(candidate);
            }
        }

        if (matchedCandidates.size() < 3) {
            Toast.makeText(this, "Not enough candidates in this portfolio.", Toast.LENGTH_SHORT).show();
            return;
        }

        txtPortfolio.setText(portfolios.get(screen));
        txtName1.setText((matchedCandidates.get(0).getName().charAt(0)+". "+matchedCandidates.get(0).getName().split(" ")[1]));
        txtName2.setText(matchedCandidates.get(1).getName().charAt(0)+". "+matchedCandidates.get(1).getName().split(" ")[1]);
        txtName3.setText(matchedCandidates.get(2).getName().charAt(0)+". "+matchedCandidates.get(2).getName().split(" ")[1]);
        txtParty1.setText(matchedCandidates.get(0).getParty());
        txtParty2.setText(matchedCandidates.get(1).getParty());
        txtParty3.setText(matchedCandidates.get(2).getParty());

        btnSkipNext.setText("Skip");
        screen++;
    }
    public void showPortfolio(View v)
    {

        layer1.setBackground(getDrawable(R.drawable.rounded8dp));
        layer2.setBackground(getDrawable(R.drawable.rounded8dp));
        layer3.setBackground(getDrawable(R.drawable.rounded8dp));
        clicked = 0;

        if(selectedNow != null)
        {
            sessionVotes.add(selectedNow);
        }

        if(screen < 6) {
            //uses the array of candidates, 3 candidates per portfolio
            if (portfolios.isEmpty() || candidates.isEmpty()) {
                Toast.makeText(this, "No portfolios or candidates available.", Toast.LENGTH_SHORT).show();
                screen = 7;//will exit
                return;
            }

            matchedCandidates.clear();
            for (Candidate candidate : candidates) {
                if (candidate.getPortfolio().equals(portfolios.get(screen))) {
                    matchedCandidates.add(candidate);
                }
            }

            if (matchedCandidates.size() < 3) {
                Toast.makeText(this, "Not enough candidates in this portfolio.", Toast.LENGTH_SHORT).show();
                return;
            }

            txtPortfolio.setText(portfolios.get(screen));
            txtName1.setText((matchedCandidates.get(0).getName().charAt(0)+". "+matchedCandidates.get(0).getName().split(" ")[1]));
            txtName2.setText(matchedCandidates.get(1).getName().charAt(0)+". "+matchedCandidates.get(1).getName().split(" ")[1]);
            txtName3.setText(matchedCandidates.get(2).getName().charAt(0)+". "+matchedCandidates.get(2).getName().split(" ")[1]);
            //the code above displays only the Initial and Surname e.g >> John Nkabi, it turns to J. Nkabi
            txtParty1.setText(matchedCandidates.get(0).getParty());
            txtParty2.setText(matchedCandidates.get(1).getParty());
            txtParty3.setText(matchedCandidates.get(2).getParty());
        }

        if(screen == 5)
        {
            btnSubmit.setVisibility(View.VISIBLE);
            btnSkipNext.setVisibility(View.GONE);
        }else
        {
            btnSkipNext.setText("Skip");
        }


        screen++;
        if(screen == 7)
        {
            //add to DB
            for(int i = 0; i < sessionVotes.size();i++)
            {
                VotexDB.Insert(this,null,null,sessionVotes.get(i));
            }

            if(sessionVotes.size() > 0)
            {
                new AlertDialog.Builder(this)
                        .setTitle("Congratulations")
                        .setMessage("You have completed your voting Process\nWe hope your preferred candidates win!")
                        .setPositiveButton(android.R.string.ok, (dialog, which) ->
                        {
                            users.clear();
                            votes.clear();
                            candidates.clear();
                            VotexDB.readFromDB(this, "users");
                            VotexDB.readFromDB(this, "votes");
                            VotexDB.readFromDB(this, "candidates");
                            startActivity(new Intent(VotingActivity.this, Home.class));
                            finish();
                            dialog.dismiss();
                        })
                        .setIcon(android.R.drawable.ic_menu_info_details)
                        .show();
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("No Vote")
                        .setMessage("We noticed that you have skipped all portfolios and we respect your wish if you prefer not to vote\n" +
                                "But if you wish to start over again" +
                                ", press \"Ok\" or press \"Cancel\" to exit")
                        .setPositiveButton(android.R.string.ok, (dialog, which) ->
                        {
                            Toast.makeText(this, "Starting over...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(VotingActivity.this, VotingActivity.class));
                            finish();
                            dialog.dismiss();
                        })
                        .setNegativeButton(android.R.string.no, (dialog, which) ->
                        {
                            startActivity(new Intent(VotingActivity.this, Home.class));
                            finish();
                            dialog.dismiss();
                        })
                        .setIcon(android.R.drawable.ic_menu_info_details)
                        .show();
            }

        }
    }
    @SuppressLint("SetTextI18n")
    public void holdVote(int candidate)
    {
        //one of the images has been clicked
        if(clicked == candidate)
        {
            layer1.setBackground(getDrawable(R.drawable.rounded8dp));
            layer2.setBackground(getDrawable(R.drawable.rounded8dp));
            layer3.setBackground(getDrawable(R.drawable.rounded8dp));
            btnSkipNext.setText("Skip");
            clicked = 0;
        }
        else {
            btnSkipNext.setText("Next");
            selectedNow = new Vote(sessionUser.id, matchedCandidates.get(candidate-1).getId());
            Toast.makeText(this, "Voting for "+selectedNow.getCandidateId(), Toast.LENGTH_SHORT).show();
            //a bug for above, the getCandidate id returns id for only the first portfolio's candidates' id
        }

    }

    //opting for the least decent way
    int clicked;
    public void one(View v)
    {
        layer2.setBackground(getDrawable(R.drawable.rounded8dp));
        layer3.setBackground(getDrawable(R.drawable.rounded8dp));
        layer1.setBackground(getDrawable(R.drawable.borders));
        holdVote(1);
        clicked = 1;
    }
    public void two(View v)
    {
        layer1.setBackground(getDrawable(R.drawable.rounded8dp));
        layer3.setBackground(getDrawable(R.drawable.rounded8dp));
        layer2.setBackground(getDrawable(R.drawable.borders));
        holdVote(2);
        clicked = 2;
    }
    public void three(View v)
    {
        layer1.setBackground(getDrawable(R.drawable.rounded8dp));
        layer2.setBackground(getDrawable(R.drawable.rounded8dp));
        layer3.setBackground(getDrawable(R.drawable.borders));
        holdVote(3);
        clicked = 3;
    }
}
