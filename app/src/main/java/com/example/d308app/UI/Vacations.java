package com.example.d308app.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308app.R;
import com.example.d308app.database.Repository;
import com.example.d308app.entities.Excursion;
import com.example.d308app.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Vacations extends AppCompatActivity {
    private Repository repository;
    private VacationAdapter vacationAdapter;
    private SearchView searchView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacations);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vacations.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recyclerview);
        searchView = findViewById(R.id.search_vacation);
        repository = new Repository(getApplication());

        // Initialize the adapter once
        vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load data into adapter
        loadVacations();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // No action on submit for now
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterVacations(newText);
                return true; // Text change handled
            }
        });

        //System.out.println(getIntent().getStringExtra("test"));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void loadVacations() {
        // Asynchronous fetch of data to ensure UI does not block
        repository.getmAllVacationsAsync(vacations -> {
            // Ensure this runs on the UI thread, especially if coming from a background thread
            runOnUiThread(() -> {
                vacationAdapter.setVacations(vacations);
            });
        });
    }
    private void filterVacations(String text) {
        repository.getFilteredVacations(text).observe(this, vacations -> {
            vacationAdapter.setVacations(vacations);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVacations();
    }
}