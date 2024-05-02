package com.example.d308app.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308app.R;
import com.example.d308app.database.Repository;
import com.example.d308app.entities.Excursion;
import com.example.d308app.entities.Vacation;

import java.util.List;

public class ExcursionDetails extends AppCompatActivity {
    String name;
    String date;
    int ID;
    int vacationID;
    EditText editName;
    EditText editDate;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

            editName = findViewById(R.id.titletext);
            editDate = findViewById(R.id.date);
            ID = getIntent().getIntExtra("id", -1);
            vacationID = getIntent().getIntExtra("VacationID", -1);
            name = getIntent().getStringExtra("name");
            date = getIntent().getStringExtra("date");
            editName.setText(name);
            editDate.setText(date);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Excursion excursion;
        if (item.getItemId() == R.id.excursionsave) {
            if (ID == -1) {
//                if (repository.getmAllExcursions().size() == 0) ID = 1;
//                else
//                    ID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getID() + 1;
                excursion = new Excursion(0, editName.getText().toString(), editDate.getText().toString(), vacationID);
                repository.insert(excursion);
                this.finish();
            } else {
                excursion = new Excursion(ID, editName.getText().toString(), editDate.getText().toString(), vacationID);
                repository.update(excursion);
                this.finish();
            }
        } else if (item.getItemId() == R.id.excursiondelete) {
            excursion = new Excursion(ID, editName.getText().toString(), editDate.getText().toString(), vacationID);
            repository.delete(excursion);
            this.finish();
        }
        return true;
    }
}