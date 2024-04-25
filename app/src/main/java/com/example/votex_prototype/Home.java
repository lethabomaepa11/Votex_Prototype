package com.example.votex_prototype;

import static com.example.votex_prototype.Main.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


public class Home extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lblUsername = findViewById(R.id.lblUsername);
        lblUsername.setText(sessionUser.name);
        Objects.requireNonNull(getSupportActionBar()).hide();//hide the titlebar
        for(int i = 0; i < votes.size(); i++)
        {
            if(Objects.equals(sessionUser.id, votes.get(i).getVoterId()))
            {
                sessionUser.setHasVoted();
            }
        }
        if(sessionUser.getHasVoted())
        {
            Button btnVote = findViewById(R.id.btnVote);
            btnVote.setVisibility(View.GONE);
        }
        else
        {
            Button btnVote = findViewById(R.id.btnResults);
            btnVote.setVisibility(View.GONE);
        }

    }
    TextView lblUsername;
    public void logout(View v)
    {
        //clear the session user
        sessionUser = null;
        finishActivity(0);
        Toast.makeText(this, "Logging Out...", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(Home.this, Index.class));

    }
    public void closeApp(View v)
    {
        finishAffinity();
    }
    boolean displayed = false;


    @SuppressLint("SetTextI18n")
    public void showDetails(View v)
    {
        if(!displayed)
        {
            lblUsername.setText("DETAILS\n\nName: "+sessionUser.name+
                    "\nUsername: "+sessionUser.id+
                    "\nEmail: "+sessionUser.email+
                    "\nGender: "+sessionUser.gender.toUpperCase()+
                    "\nHas voted: "+ sessionUser.getHasVoted());
            displayed = true;
            lblUsername.setTextSize(18);
        }
        else
        {
            lblUsername.setTextSize(25);
            lblUsername.setText(sessionUser.name);
            displayed = false;
        }
    }
    public void help(View v) {

        new AlertDialog.Builder(this)
                .setTitle("Help Information")
                .setMessage(!(sessionUser.getHasVoted())?"Click on \"Cast your Vote\" to proceed to voting screen" +
                        "\n\nYou will be able to choose only one candidate per Portfolio" +
                        "\n\nAvailable portfolios are: " +
                        "\n\t*Chaiperson" +
                        "\n\t*Deputy Chairperson" +
                        "\n\t*Secretary" +
                        "\n\t*Treasurer" +
                        "\n\t*Legal Officer" +
                        "\n\t*Academic Officer" +
                        "\n\nEach portfolio will have 3 candidates, each candidate representing SASCO, EFFSC or REVOLUTION":
                        "Click View Results")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }
}