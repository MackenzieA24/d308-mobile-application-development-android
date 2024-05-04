package com.example.d308app.UI;



import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {
    String name;
    String hotel;
    String startDate;
    String endDate;
    int ID;
    EditText editName;
    EditText editHotel;

    EditText editStartDate;
    EditText editEndDate;
    Repository repository;
   DatePickerDialog.OnDateSetListener myStartDate = new DatePickerDialog.OnDateSetListener() {
       @Override
       public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
           myCalendar.set(Calendar.YEAR, year);
           myCalendar.set(Calendar.MONTH, month);
           myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
           updateStartDateLabel();
           SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
           String formattedDate = sdf.format(myCalendar.getTime());

           if (editStartDate != null && !validateDateFormat(formattedDate)) {
               Toast.makeText(VacationDetails.this, "Invalid start date format", Toast.LENGTH_SHORT).show();
           } else {
               editStartDate.setText(formattedDate);
           }
       }
   };
    DatePickerDialog.OnDateSetListener myEndDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEndDateLabel();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
            String formattedDate = sdf.format(myCalendar.getTime());

            if (editEndDate != null && !validateDateFormat(formattedDate)) {
                Toast.makeText(VacationDetails.this, "Invalid end date format", Toast.LENGTH_SHORT).show();
            } else {
                editEndDate.setText(formattedDate);
            }
        }
    };

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        repository = new Repository(getApplication());

        startDate = getIntent().getStringExtra(startDate);
        endDate = getIntent().getStringExtra(endDate);

        editStartDate = findViewById(R.id.startdate);
        editEndDate = findViewById(R.id.enddate);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String currentDate = sdf.format(new Date());


        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(editStartDate);
            }
        });
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(editEndDate);
            }
        });

        editName = findViewById(R.id.titletext);
        editHotel = findViewById(R.id.hotel);
        ID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        hotel = getIntent().getStringExtra("hotel");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        editName.setText(name);
        editHotel.setText(hotel);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("VacationID", ID);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationID() == ID) filteredExcursions.add(e);
            if (e.getVacationID() != ID) filteredExcursions.remove(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }
    @Override
    protected void onResume() {
        super.onResume();
        List<Excursion> filteredExcursions = repository.getAssociatedExcursions(ID);
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        ExcursionAdapter excursionAdapter = (ExcursionAdapter) recyclerView.getAdapter();
        excursionAdapter.setExcursions(filteredExcursions);
        excursionAdapter.notifyDataSetChanged();
    }
    private void updateStartDateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateEndDateLabel() {
        String myFormat = "MM/dd/yy"; // Your date format
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndDate.setText(sdf.format(myCalendar.getTime()));
    }
    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
                        String formattedDate = sdf.format(selectedDate.getTime());
                        if (editText == editStartDate && !validateDateFormat(formattedDate)) {
                            Toast.makeText(VacationDetails.this, "Invalid start date format", Toast.LENGTH_SHORT).show();
                        } else {
                            editText.setText(formattedDate);
                        }
                        if (editText == editEndDate && !validateDateFormat(formattedDate)) {
                            Toast.makeText(VacationDetails.this, "Invalid start date format", Toast.LENGTH_SHORT).show();
                        } else {
                            editText.setText(formattedDate);
                        }
                    }
                },
                year, month, dayOfMonth
        );
        datePickerDialog.show();
    }
    private boolean validateDateFormat(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        sdf.setLenient(false);
        try {
            sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isEndDateAfterStartDate(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            return end.after(start);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Vacation vacation;
        String startDateInput = editStartDate.getText().toString();
        String endDateInput = editEndDate.getText().toString();
        if (item.getItemId() == R.id.vacationsave) {
            if (!validateDateFormat(startDateInput)) {
                Log.d("DateValidation", "Invalid start date format: " + startDateInput);
                Toast.makeText(this, "Invalid start date format", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Log.d("DateValidation", "Valid start date format: " + startDateInput);
            }
            if (!validateDateFormat(endDateInput)) {
                Log.d("DateValidation", "Invalid end date format: " + endDateInput);
                Toast.makeText(this, "Invalid end date format", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Log.d("DateValidation", "Valid end date format: " + endDateInput);
            }
            if (!isEndDateAfterStartDate(startDateInput, endDateInput)) {
                Toast.makeText(this, "Start date must be before end date!", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (ID == -1) {
                if (repository.getmAllVacations().size() == 0) ID = 1;
                else
                    ID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getID() + 1;
                vacation = new Vacation(ID, editName.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                repository.insert(vacation);
                this.finish();
            } else {
                vacation = new Vacation(ID, editName.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                repository.update(vacation);
                this.finish();
            }
        } else if (item.getItemId() == R.id.vacationdelete) {
            List<Excursion> excursions = repository.getAssociatedExcursions(ID);
            if (excursions.isEmpty()) {
                vacation = new Vacation(ID, editName.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                repository.delete(vacation);
                this.finish();
            } else {
                Toast.makeText(this, "Cannot delete. Excursions are associated with this vacation.", Toast.LENGTH_SHORT).show();
            }
        }
        if (item.getItemId() == R.id.share) {
            Intent sentIntent= new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            String shareText = "Vacation Name: " + editName.getText().toString() + "\n" +
                    "Accomodation: " + editHotel.getText().toString() + "\n" +
                    "Start Date: " + editStartDate.getText().toString() + "\n" +
                    "End Date: " + editEndDate.getText().toString();
            sentIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            sentIntent.setType("text/plain");
            Intent shareIntent=Intent.createChooser(sentIntent,null);
            startActivity(shareIntent);
            return true;
        }
        if (item.getItemId() == R.id.notifystart) {
            String dateFromScreen = editStartDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
            intent.putExtra("key", editName.getText().toString() + " is starting!");
            PendingIntent sender=PendingIntent.getBroadcast(VacationDetails.this,++MainActivity.numAlert, intent,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger,sender);
            return true;
        }
        if (item.getItemId() == R.id.notifyend) {
            String dateFromScreen = editEndDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
            intent.putExtra("key", editName.getText().toString() + " is ending!");
            PendingIntent sender=PendingIntent.getBroadcast(VacationDetails.this,++MainActivity.numAlert, intent,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger,sender);
            return true;
        }
        return true;
        }
    }

