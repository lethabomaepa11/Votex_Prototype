package com.example.votex_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import static com.example.votex_prototype.Main.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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


    }
    public void btnClick(View v)
    {
        //go to another activity
        startActivity(new Intent(Index.this, Login.class));

    }
    public void register(View v)
    {
        //go to another activity
        startActivity(new Intent(Index.this, Register.class));
    }
}