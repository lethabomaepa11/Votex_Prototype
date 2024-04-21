package com.example.votex_prototype;

import static com.example.votex_prototype.Main.*;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class Home extends AppCompatActivity {

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
        TextView lblUsername = findViewById(R.id.lblUsername);
        lblUsername.setText(sessionUser.name);
        getSupportActionBar().hide();//hide the titlebar
    }
    public void close(View v)
    {
        finishActivity(0);
        Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show();
        System.exit(0);


    }
}