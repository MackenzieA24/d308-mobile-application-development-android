package com.example.d308app.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308app.R;
import com.example.d308app.database.Repository;

public class ExcursionDetails extends AppCompatActivity {
    String name;
    String date;
    int ID;
    EditText editName;
    EditText editDate;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

            editName = findViewById(R.id.titletext);
            editDate = findViewById(R.id.date);
            ID = getIntent().getIntExtra("id", -1);
            name = getIntent().getStringExtra("name");
            date = getIntent().getStringExtra("date");
            editName.setText(name);
            editDate.setText(date);


    }
}