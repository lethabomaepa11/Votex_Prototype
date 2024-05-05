package com.example.votex_prototype;

import static com.example.votex_prototype.Main.candidates;
import static com.example.votex_prototype.Main.votes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class UpdateCandidates extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();//hide the titlebar
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_candidates);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        candidates.clear();
        VotexDB.readFromDB(this, "candidates");//returns an array of Candidate objects
        txtSascoChairperson = findViewById(R.id.txtSascoChairperson);
        txtSascoDeputyChair = findViewById(R.id.txtSascoDeputyChair);
        txtSascoSecretary = findViewById(R.id.txtSascoSecretary);
        txtSascoDeputySecretary = findViewById(R.id.txtSascoDeputySecretary);
        txtSascoAO = findViewById(R.id.txtSascoAO);
        txtSascoLO = findViewById(R.id.txtSascoLO);

        txtEffChairperson = findViewById(R.id.txtEffChairperson);
        txtEffDeputyChair = findViewById(R.id.txtEffDeputyChair);
        txtEffSecretary = findViewById(R.id.txtEffSecretary);
        txtEffDeputySecretary = findViewById(R.id.txtEffDeputySecretary);
        txtEffAO = findViewById(R.id.txtEffAO);
        txtEffLO = findViewById(R.id.txtEffLO);

        txtRevoChairperson = findViewById(R.id.txtRevoChairperson);
        txtRevoDeputyChair = findViewById(R.id.txtRevoDeputyChair);
        txtRevoSecretary = findViewById(R.id.txtRevoSecretary);
        txtRevoDeputySecretary = findViewById(R.id.txtRevoDeputySecretary);
        txtRevoAO = findViewById(R.id.txtRevoAO);
        txtRevoLO = findViewById(R.id.txtRevoLO);
        assignToPortfolios();

    }

    //the 18 variables
    EditText txtSascoChairperson,txtSascoDeputyChair,txtSascoSecretary, txtSascoDeputySecretary,txtSascoAO,txtSascoLO;
    EditText txtEffChairperson,txtEffDeputyChair,txtEffSecretary, txtEffDeputySecretary,txtEffAO,txtEffLO;
    EditText txtRevoChairperson,txtRevoDeputyChair,txtRevoSecretary, txtRevoDeputySecretary,txtRevoAO,txtRevoLO;
    ArrayList<Candidate> candidateUpdates = new ArrayList<>();

    public void assignToPortfolios()
    {
        String[] portfolios = {"Chairperson","Deputy Chairperson","Secretary","Deputy Secretary","Academic Officer","Legal Officer"};
        for(int p = 0; p < 6; p++)//portfolios
        {
            String portfolio = portfolios[p];
            /*ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
            ListView listView = (ListView) findViewById(R.id.listview);
            listView.setAdapter(itemsAdapter);*/
            for(int i = 0; i < candidates.size(); i++)
            {

                if(candidates.get(i).getPortfolio().equalsIgnoreCase(portfolio))
                {
                    switch (candidates.get(i).getParty().toLowerCase())
                    {
                        case "effsc":
                            switch (candidates.get(i).getPortfolio().toLowerCase())
                            {
                                case "chairperson":
                                    txtEffChairperson.setText(candidates.get(i).getName());
                                    break;
                                case "deputy chairperson":
                                    txtEffDeputyChair.setText(candidates.get(i).getName());
                                    break;
                                case "secretary":
                                    txtEffSecretary.setText(candidates.get(i).getName());
                                    break;
                                case "deputy secretary":
                                    txtEffDeputySecretary.setText(candidates.get(i).getName());
                                    break;
                                case "academic officer":
                                    txtEffAO.setText(candidates.get(i).getName());
                                    break;
                                case "legal officer":
                                    txtEffLO.setText(candidates.get(i).getName());
                                    break;
                            }
                            break;
                        case "sasco":
                            switch (candidates.get(i).getPortfolio().toLowerCase())
                            {
                                case "chairperson":
                                    txtSascoChairperson.setText(candidates.get(i).getName());
                                    break;
                                case "deputy chairperson":
                                    txtSascoDeputyChair.setText(candidates.get(i).getName());
                                    break;
                                case "secretary":
                                    txtSascoSecretary.setText(candidates.get(i).getName());
                                    break;
                                case "deputy secretary":
                                    txtSascoDeputySecretary.setText(candidates.get(i).getName());
                                    break;
                                case "academic officer":
                                    txtSascoAO.setText(candidates.get(i).getName());
                                    break;
                                case "legal officer":
                                    txtSascoLO.setText(candidates.get(i).getName());
                                    break;
                            }
                            break;
                        case "revolution":
                            switch (candidates.get(i).getPortfolio().toLowerCase())
                            {
                                case "chairperson":
                                    txtRevoChairperson.setText(candidates.get(i).getName());
                                    break;
                                case "deputy chairperson":
                                    txtRevoDeputyChair.setText(candidates.get(i).getName());
                                    break;
                                case "secretary":
                                    txtRevoSecretary.setText(candidates.get(i).getName());
                                    break;
                                case "deputy secretary":
                                    txtRevoDeputySecretary.setText(candidates.get(i).getName());
                                    break;
                                case "academic officer":
                                    txtRevoAO.setText(candidates.get(i).getName());
                                    break;
                                case "legal officer":
                                    txtRevoLO.setText(candidates.get(i).getName());
                                    break;
                            }
                            break;
                    }

                    candidateUpdates.add(candidates.get(i));
                }

            }
        }
    }

    public void updateThem()
    {
        for(int i = 0; i < candidates.size(); i++)
        {

            switch (candidates.get(i).getParty().toLowerCase())
            {
                case "sasco":
                    switch (candidates.get(i).getPortfolio().toLowerCase())
                    {
                        case "chairperson":
                            candidates.get(i).setName(txtSascoChairperson.getText().toString());
                            break;
                        case "deputy chairperson":
                            candidates.get(i).setName(txtSascoDeputyChair.getText().toString());
                            break;
                        case "secretary":
                            candidates.get(i).setName(txtSascoSecretary.getText().toString());
                            break;
                        case "deputy secretary":
                            candidates.get(i).setName(txtSascoDeputySecretary.getText().toString());
                            break;
                        case "academic officer":
                            candidates.get(i).setName(txtSascoAO.getText().toString());
                            break;
                        case "legal officer":
                            candidates.get(i).setName(txtSascoLO.getText().toString());
                            break;
                    }
                    break;
                case "effsc":
                    switch (candidates.get(i).getPortfolio().toLowerCase())
                    {
                        case "chairperson":
                            candidates.get(i).setName(txtEffChairperson.getText().toString());
                            break;
                        case "deputy chairperson":
                            candidates.get(i).setName(txtEffDeputyChair.getText().toString());
                            break;
                        case "secretary":
                            candidates.get(i).setName(txtEffSecretary.getText().toString());
                            break;
                        case "deputy secretary":
                            candidates.get(i).setName(txtEffDeputySecretary.getText().toString());
                            break;
                        case "academic officer":
                            candidates.get(i).setName(txtEffAO.getText().toString());
                            break;
                        case "legal officer":
                            candidates.get(i).setName(txtEffLO.getText().toString());
                            break;
                    }
                    break;
                case "revolution":
                    switch (candidates.get(i).getPortfolio().toLowerCase())
                    {
                        case "chairperson":
                            candidates.get(i).setName(txtRevoChairperson.getText().toString());
                            break;
                        case "deputy chairperson":
                            candidates.get(i).setName(txtRevoDeputyChair.getText().toString());
                            break;
                        case "secretary":
                            candidates.get(i).setName(txtRevoSecretary.getText().toString());
                            break;
                        case "deputy secretary":
                            candidates.get(i).setName(txtRevoDeputySecretary.getText().toString());
                            break;
                        case "academic officer":
                            candidates.get(i).setName(txtRevoAO.getText().toString());
                            break;
                        case "legal officer":
                            candidates.get(i).setName(txtRevoLO.getText().toString());
                            break;
                    }
                    break;
            }

        }
        Toast.makeText(this, "Updating...", Toast.LENGTH_SHORT).show();
        for(int i = 0; i < candidates.size();i++)
        {
            VotexDB.Update(this,null,candidates.get(i),null);
        }
        Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(UpdateCandidates.this,AdminDashboard.class));
        finish();


    }

    public  void btnUpdateClick(View v)
    {
        updateThem();
    }

}
